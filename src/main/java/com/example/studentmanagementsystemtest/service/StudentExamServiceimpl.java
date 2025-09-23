package com.example.studentmanagementsystemtest.service;

import com.example.studentmanagementsystemtest.entity.Exam;

import java.util.List;

public interface StudentExamServiceimpl {

    // 根据学生 ID 查询该学生相关的考试信息
    List< Exam > getClassInfoByStudentId(int studentid);

}

