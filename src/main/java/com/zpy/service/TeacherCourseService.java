package com.zpy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zpy.pojo.TeacherCourse;

import java.util.List;

public interface TeacherCourseService extends IService<TeacherCourse> {
    List<TeacherCourse> listTeacherCourse(Integer id);

    List<TeacherCourse> listMyCourse(Integer userId, String cname);
}
