package com.example.studentmanagementsystemtest.mapper;


import com.example.studentmanagementsystemtest.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface StudentExamMapper {
    @Select("SELECT classid, subject, time,location " +
            "FROM examinformation " +
            "WHERE classid IN (SELECT classid FROM student_class WHERE number = #{studentid})")
    @Results({
            @Result(property = "class_id", column = "classid"),
            @Result(property = "subjects", column = "subject"),
            @Result(property = "time_exam", column = "time"),
            @Result(property = "locations", column = "location"),
    })
    List<Teacher> getClassInfoByStudentId(@Param("studentid") int studentId);

}
