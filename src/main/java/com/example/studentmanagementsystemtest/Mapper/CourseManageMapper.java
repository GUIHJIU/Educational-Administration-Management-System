package com.example.studentmanagementsystemtest.Mapper;

import com.example.studentmanagementsystemtest.Entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseManageMapper {
    @Select("select * from course")
    List< Course > selectAllCourse();
}
