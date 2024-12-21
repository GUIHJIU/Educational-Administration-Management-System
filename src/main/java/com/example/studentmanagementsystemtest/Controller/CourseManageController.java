package com.example.studentmanagementsystemtest.Controller;

import com.example.studentmanagementsystemtest.Entity.Course;
import com.example.studentmanagementsystemtest.Service.CourseManageService;
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
}
