package com.example.studentmanagementsystemtest.service;

import com.example.studentmanagementsystemtest.Entity.Teacher;

import java.util.List;

public interface StudentExamServiceimpl {

    // 根据学生 ID 查询该学生相关的考试信息
    List<Teacher> getClassInfoByStudentId(int studentid);

}

