package com.zpy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zpy.pojo.CountNumber;
import com.zpy.pojo.StudentCourse;

import java.util.List;

public interface StudentCourseService extends IService<StudentCourse> {
    List<StudentCourse> listStudentCourse(Integer id);

    List<CountNumber> queryAll();

    List<StudentCourse> listMyCourse(Integer userId, String cname);
}
