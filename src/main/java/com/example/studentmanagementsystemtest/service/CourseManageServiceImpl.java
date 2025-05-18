package com.example.studentmanagementsystemtest.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.studentmanagementsystemtest.Exception.BusinessException;
import com.example.studentmanagementsystemtest.entity.Course;
import com.example.studentmanagementsystemtest.mapper.CourseManageMapper;
import com.example.studentmanagementsystemtest.service.CourseManageService;
import com.example.studentmanagementsystemtest.util.ErrorCode;
import com.example.studentmanagementsystemtest.util.Result;
import com.example.studentmanagementsystemtest.util.TimeConflictDetector;
import com.example.studentmanagementsystemtest.util.TimeRange;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.spi.DirStateFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CourseManageServiceImpl
        extends ServiceImpl< CourseManageMapper, Course >  // 继承ServiceImpl
        implements CourseManageService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private SelectionRecordService selectionService;

    @Override
    public List< Course > ShowAllCourse() {
        return this.list(); // 自动过滤已删除数据（需配置逻辑删除）
    }

    // 2. 动态查询（增强安全性）
    @Override
    public List< Course > SearchCourse(String queryType, String queryParam) {
        return baseMapper.searchCourse(queryType, queryParam);
    }

    // 3. 插入课程（添加参数校验）
    @Override
    @Transactional
    public void InsertCourse(Course course) {
        // 数据校验示例
        if (course.getCapacity() <= 0) {
            throw new IllegalArgumentException("课程容量必须大于0");
        }
        this.save(course);
    }

    // 4. 更新课程（带乐观锁检查）
    @Override
    @Transactional
    public int UpdateCourse(Course course) {
        return this.updateById(course) ? 1 : 0;
    }

    // 5. 删除课程（升级为逻辑删除）
    @Override
    @Transactional
    public int DeleteCourse(Long courseId) {
        return baseMapper.deleteCourse(courseId);
    }

    /**
     * 动态查询实现（带防SQL注入机制）
     */
    @Override
    public List< Course > dynamicSearch(String queryType, String queryParam) {
        // 参数安全检查
        if (!isValidQueryType(queryType)) {
            throw new IllegalArgumentException("非法查询类型");
        }
        return baseMapper.searchCourse(queryType, queryParam);
    }

    /**
     * 带分布式锁的库存扣减
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result< Boolean > deductStock(Long courseId) {
        RLock lock = redissonClient.getLock("course:lock:" + courseId);
        try {
            if (lock.tryLock(3, 15, TimeUnit.SECONDS)) {
                Course course = getByIdWithCheck(courseId);

                // 使用CAS机制更新
                int result = baseMapper.deductStockWithVersion(
                        courseId, course.getVersion());

                if (result > 0) {
                    log.info("库存扣减成功，课程ID：{}", courseId);
                    return Result.success(true);
                }
                return Result.error(ErrorCode.STOCK_NOT_ENOUGH);
            }
            return Result.error(ErrorCode.SERVICE_BUSY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BusinessException(ErrorCode.SERVICE_BUSY);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取可选课程列表（带缓存示例）
     */
    @Override
    @Cacheable(value = "availableCourses", key = "#studentId")
    public List< Course > getAvailableCourses(Long studentId) {
        // 获取已选课程ID列表
        List< Long > selectedIds = selectionService.getSelectedCourseIds(studentId);

        return this.lambdaQuery()
                .gt(Course::getRemaining, 0)
                .notIn(!selectedIds.isEmpty(), Course::getCourseId, selectedIds)
                .gt(Course::getStartTime, LocalDateTime.now())
                .list();
    }

    /**
     * 时间冲突检测（含数据库级校验）
     */
    @Override
    public boolean checkTimeConflict(Long studentId, Long courseId) {
        // 双重校验：应用层+数据库层
        boolean appLevelConflict = checkConflictAtAppLevel(studentId, courseId);
        if (appLevelConflict) return true;

        return checkConflictAtDatabaseLevel(studentId, courseId);
    }

    //-- 私有方法 --//
    private Course getByIdWithCheck(Long courseId) {
        Course course = getById(courseId);
        if (course == null) {
            throw new BusinessException(ErrorCode.COURSE_NOT_EXIST);
        }
        return course;
    }

    private boolean isValidQueryType(String type) {
        return Set.of("courseName", "teacherId", "courseType").contains(type);
    }

    private boolean checkConflictAtAppLevel(Long studentId, Long courseId) {
        // 应用层使用线段树算法检测
        List< Course > existing = selectionService.getStudentCourses(studentId);
        Course newCourse = getById(courseId);
        return TimeConflictDetector.checkConflict(
                existing.stream()
                        .map(c -> new TimeRange(c.getStartTime(), c.getEndTime()))
                        .collect(Collectors.toList()),
                new TimeRange(newCourse.getStartTime(), newCourse.getEndTime())
        );
    }

    private boolean checkConflictAtDatabaseLevel(Long studentId, Long courseId) {
        // 使用数据库函数校验
        return baseMapper.checkTimeConflictInDB(studentId,
                getById(courseId).getStartTime(),
                getById(courseId).getEndTime());
    }
}