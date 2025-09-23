package com.example.studentmanagementsystemtest.service;

import com.example.studentmanagementsystemtest.entity.Exam;
import com.example.studentmanagementsystemtest.mapper.StudentExamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentExamService implements StudentExamServiceimpl {

    @Autowired
    StudentExamMapper studentExamMapper;

    // 根据学生 ID 查询该学生的所有考试信息
    @Override
    public List< Exam > getClassInfoByStudentId(int studentid) {
        return studentExamMapper.getClassInfoByStudentId(studentid);
    }
}


