package com.example.studentmanagementsystemtest.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.studentmanagementsystemtest.mapper.Teachermapper;
import com.example.studentmanagementsystemtest.Entity.Teacher;
import java.util.List;

@Service
public class Teacherservice implements Teacherserviceimpl
{
    @Autowired
    Teachermapper teachermapper;
    //增加
    @Override
    public void add(Teacher teacher)
    {
        teachermapper.insert(teacher);
    }

    //显示

    @Override
    public List<Teacher> getAllTeacher()
    {
        return teachermapper.findAll();
    }
    @Override
    public int updateTeacher(Teacher teacher)
    {
        return teachermapper.updateTeacher(teacher);
    }

    @Override
    public void deleteTeacher(String class_id)
    {
        teachermapper.deleteTeacher(class_id);
    }

    @Override
    public Teacher getTeacher(String class_id)
    {
        return teachermapper.getTeacher(class_id);
    }
}
