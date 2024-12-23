package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.Course;
import com.example.studentmanagementsystemtest.service.CourseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseManageController {
    @Autowired
    CourseManageService service;
    @GetMapping("/course")
    public List< Course > getshowall()
    {
        return postshowall();
    }
    @GetMapping("/searchcourse")
    public List<Course> SearchCourse(@RequestParam("queryType")String queryType,@RequestParam("queryParam") String queryParam){
        return service.SearchCourse(queryType,queryParam);
    }
    @PostMapping("/course")
    public List<Course> postshowall()
    {
        return service.ShowAllCourse();
    }
    @PostMapping("/addcourses")
    public String InsertCourse(@RequestBody Course course){
        service.InsertCourse(course);
        return "Course data received successfully";
    }
    @PutMapping("/updatecourse")
    public int UpdateCourse(@RequestBody Course course){
        return service.UpdateCourse(course);
    }
    @DeleteMapping("/deletecourse")
    public int DeleteCourse(@RequestParam("courseId")int courseId){
        return service.DeleteCourse(courseId);
    }
}
