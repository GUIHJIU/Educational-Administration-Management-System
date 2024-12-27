package com.zpy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zpy.mapper.TeacherCourseMapper;
import com.zpy.pojo.TeacherCourse;
import com.zpy.service.TeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherCourseServiceImpl extends ServiceImpl<TeacherCourseMapper, TeacherCourse>implements TeacherCourseService {
    @Autowired
    private TeacherCourseMapper teacherCourseMapper;
    @Override
    public List<TeacherCourse> listTeacherCourse(Integer id) {

        return teacherCourseMapper.listTeacherCourse(id);
    }

    @Override
    public List<TeacherCourse> listMyCourse(Integer userId, String cname) {
        return teacherCourseMapper.listMyCourse(userId,cname);
    }
}
