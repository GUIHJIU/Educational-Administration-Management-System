package com.example.studentmanagementsystemtest.service;

import com.example.studentmanagementsystemtest.entity.Exam;

import java.util.List;

public interface TeacherExamServiceimpl {
    //新增
    public void add(Exam exam);

    //列表
    public List< Exam > getAllTeacher();

    //更新学生信息
    public int updateTeacher(Exam exam);

    // 删除学生信息
    public void deleteTeacher(String class_id);

    //根据用户学号查询学生信息
    public Exam getTeacher(String class_id);


}
