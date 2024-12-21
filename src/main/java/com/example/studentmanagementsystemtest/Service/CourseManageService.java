package com.example.studentmanagementsystemtest.Service;

import com.example.studentmanagementsystemtest.Entity.Course;
import com.example.studentmanagementsystemtest.Mapper.CourseManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseManageService {
    @Autowired
    CourseManageMapper mapper;
    public List< Course > ShowAllCourse(){
        return mapper.selectAllCourse();
    }
    public void InsertCourse (Course course){mapper.insertCourse(course);}
}
