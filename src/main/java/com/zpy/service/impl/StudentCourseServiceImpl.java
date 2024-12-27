package com.zpy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zpy.mapper.StudentCourseMapper;
import com.zpy.pojo.CountNumber;
import com.zpy.pojo.StudentCourse;
import com.zpy.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse>implements StudentCourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;
    @Override
    public List<StudentCourse> listStudentCourse(Integer id) {
        return studentCourseMapper.listStudentCourse( id);
    }

    @Override
    public List<CountNumber> queryAll() {
        return studentCourseMapper.queryAll();
    }

    @Override
    public List<StudentCourse> listMyCourse(Integer userId, String cname) {
        return studentCourseMapper.listMyCourse(userId,cname);
    }
}
