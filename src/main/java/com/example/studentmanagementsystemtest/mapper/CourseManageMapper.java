package com.example.studentmanagementsystemtest.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.example.studentmanagementsystemtest.entity.Course;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CourseManageMapper extends BaseMapper< Course > {

    // 保留原查询所有课程（使用MyBatis-Plus方式实现）
    default List< Course > selectAllCourse() {
        return selectList(null); // 等价于SELECT * FROM course
    }

    // 增强版动态查询（保留原searchCourse功能）
    default List< Course > searchCourse(String queryType, String queryParam) {
        LambdaQueryWrapper< Course > wrapper = Wrappers.lambdaQuery();

        // 字段映射安全处理（防止SQL注入）
        switch (queryType.toLowerCase()) {
            case "coursename":
                wrapper.eq(Course::getCourseName, queryParam);
                break;
            case "teacher":
                wrapper.eq(Course::getTeacherId, Long.parseLong(queryParam));
                break;
            case "coursetype":
                wrapper.eq(Course::getCourseType, queryParam);
                break;
            default:
                throw new IllegalArgumentException("无效的查询类型");
        }
        return selectList(wrapper);
    }

    @Update("UPDATE course SET remaining = remaining - 1, version = version + 1 " +
            "WHERE courseId = #{courseId} AND remaining > 0 AND version = #{version}")
    int deductStockWithVersion(@Param("courseId") Long courseId,
                               @Param("version") Integer version);

    // CourseManageMapper.java 添加方法
    @Select("SELECT EXISTS(SELECT 1 FROM selection_record sr " +
            "JOIN course c ON sr.courseId = c.courseId " +
            "WHERE sr.student_id = #{studentId} " +
            "AND (c.start_time < #{newEnd} AND c.end_time > #{newStart}))")
    boolean checkTimeConflictInDB(@Param("studentId") Long studentId,
                                  @Param("newStart") LocalDateTime newStart,
                                  @Param("newEnd") LocalDateTime newEnd);

    // 保留原插入方法（增强为使用MyBatis-Plus的插入策略）
    default void insertCourse(Course course) {
        insert(course); // 自动回填主键）
    }

    // 增强版更新方法（保留动态SQL特性）
    default int updateCourse(Course course) {
        return updateById(course); // 自动根据ID更新，配合@Version实现乐观锁
    }

    // 保留原删除方法（升级为逻辑删除支持）
    @Delete("UPDATE course SET deleted = 1 WHERE courseId = #{courseId}")
    int deleteCourse(@Param("courseId") Long courseId);

}
