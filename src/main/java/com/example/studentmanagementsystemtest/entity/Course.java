package com.example.studentmanagementsystemtest.entity;

import lombok.Data;

@Data
public class Course {
    public int courseId;
    public String courseName;
    public int credit;
    public int classHour;
    public String courseType;
    public String Teacher;
}
