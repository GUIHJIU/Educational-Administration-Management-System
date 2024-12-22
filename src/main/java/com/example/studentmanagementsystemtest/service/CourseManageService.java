package com.example.studentmanagementsystemtest.service;

import com.example.studentmanagementsystemtest.entity.Course;
import com.example.studentmanagementsystemtest.mapper.CourseManageMapper;
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
    public List< Course > SearchCourse(String queryType,String queryParam){
        return mapper.searchCourse(queryType,queryParam);
    }
    public void InsertCourse (Course course){mapper.insertCourse(course);}
    public int UpdateCourse(Course course){return mapper.updateCourse(course);}
    public int DeleteCourse(int courseId){return mapper.deleteCourse(courseId);}
}
