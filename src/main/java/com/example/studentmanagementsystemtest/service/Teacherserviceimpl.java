package com.example.studentmanagementsystemtest.service;

import com.example.studentmanagementsystemtest.Entity.Teacher;

import java.util.List;

public interface Teacherserviceimpl
{
    //新增
    public void add(Teacher teacher);
    //列表
    public List<Teacher> getAllTeacher();
    //更新学生信息
    public int updateTeacher(Teacher teacher);

    // 删除学生信息
    public void deleteTeacher(String class_id);

    //根据用户学号查询学生信息
    public Teacher getTeacher(String class_id);


}
