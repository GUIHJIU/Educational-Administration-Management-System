package com.example.studentmanagementsystemtest.service;

import com.example.studentmanagementsystemtest.entity.Course;
import com.example.studentmanagementsystemtest.entity.SelectionRecord;

import java.util.List;

// SelectionRecordService.java
public interface SelectionRecordService {
    /**
     * 获取学生已选课程ID列表
     */
    List< Long > getSelectedCourseIds(Long studentId);

    /**
     * 获取学生已选课程详细信息
     */
    List< Course > getStudentCourses(Long studentId);

    boolean addSelectedCourseRecord(SelectionRecord SR);
}