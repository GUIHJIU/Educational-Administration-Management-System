package com.example.studentmanagementsystemtest.service;

import com.example.studentmanagementsystemtest.entity.Student;

import java.util.List;

public interface Studentserviceimpl
{
  //新增
  public void add(Student student);
  //列表
  public List<Student> getAllStudent();
  //更新学生信息
  public int updateStudent(Student student);

  // 删除学生信息
  public void deleteStudent(int stuNum);

  //根据用户学号查询学生信息
  public Student getStudentByStuNum(int stuNum);


}
