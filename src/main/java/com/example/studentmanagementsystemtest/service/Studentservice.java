package com.example.studentmanagementsystemtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.studentmanagementsystemtest.mapper.Studentmapper;
import com.example.studentmanagementsystemtest.entity.Student;
import java.util.List;

@Service
public class Studentservice implements Studentserviceimpl
{
    @Autowired
    Studentmapper studentmapper;

    //增加
    @Override
    public void add(Student student)
    {
        studentmapper.insert(student);
    }

    //显示

    @Override
    public List<Student> getAllStudent()
    {
        return studentmapper.findAll();
    }
    @Override
    public int updateStudent(Student student)
    {
        return studentmapper.updateStudent(student);
    }

    @Override
    public void deleteStudent(int stu_num)
    {
        studentmapper.deleteStudent(stu_num);
    }

    @Override
    public Student getStudentBystu_num(int stu_num)
    {
        return studentmapper.getStudentBystu_num(stu_num);
    }
}
