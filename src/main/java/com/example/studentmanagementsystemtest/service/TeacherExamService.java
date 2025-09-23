package com.example.studentmanagementsystemtest.service;

import com.example.studentmanagementsystemtest.entity.Exam;
import com.example.studentmanagementsystemtest.mapper.TeacherExamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherExamService implements TeacherExamServiceimpl {
    @Autowired
    TeacherExamMapper teacherExamMapper;

    //增加
    @Override
    public void add(Exam exam) {
        teacherExamMapper.insert(exam);
    }

    //显示

    @Override
    public List< Exam > getAllTeacher() {
        return teacherExamMapper.findAll();
    }

    @Override
    public int updateTeacher(Exam exam) {
        return teacherExamMapper.updateTeacher(exam);
    }

    @Override
    public void deleteTeacher(String class_id) {
        teacherExamMapper.deleteTeacher(class_id);
    }

    @Override
    public Exam getTeacher(String class_id) {
        return teacherExamMapper.getTeacher(class_id);
    }
}
