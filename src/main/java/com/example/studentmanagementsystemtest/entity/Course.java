package com.example.studentmanagementsystemtest.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Course {
    @TableId(value = "CourseId", type = IdType.AUTO)
    private Long courseId;
    @TableField(value = "CourseName")
    private String courseName;
    private Integer credit;
    private Integer capacity;//课程容量
    private Integer remaining;//剩余名额
    @TableField(value = "ClassHour")
    private Integer classHour;//学时
    private String time_slot;//上课时间段
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @TableField(value = "CourseType")
    private String courseType;
    @TableField(value = "TeacherId")
    private String teacherId;
    @Version
    private Integer version;//乐观锁版本号
    @TableField(typeHandler = JacksonTypeHandler.class, value = "preCourse")
    private List< Long > preCourse;
    @TableLogic
    @TableField(value = "deleted")
    private Integer deleted;
}
