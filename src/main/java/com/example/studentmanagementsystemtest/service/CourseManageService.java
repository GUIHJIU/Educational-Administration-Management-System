package com.example.studentmanagementsystemtest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.studentmanagementsystemtest.entity.Course;
import com.example.studentmanagementsystemtest.mapper.CourseManageMapper;
import com.example.studentmanagementsystemtest.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseManageService extends IService< Course > {  // 继承MyBatis-Plus服务接口

    // 自定义业务方法
    List< Course > dynamicSearch(String queryType, String queryParam);

    Result< Boolean > deductStock(Long courseId);

    List< Course > getAvailableCourses(Long studentId);

    boolean checkTimeConflict(Long studentId, Long courseId);

    // 原接口方法声明
    List< Course > ShowAllCourse();

    List< Course > SearchCourse(String queryType, String queryParam);

    void InsertCourse(Course course);

    int UpdateCourse(Course course);

    int DeleteCourse(Long courseId);

    // 新增方法...
}