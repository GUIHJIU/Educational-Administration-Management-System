package com.example.studentmanagementsystemtest.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.studentmanagementsystemtest.entity.Course;
import com.example.studentmanagementsystemtest.entity.SelectionRecord;
import com.example.studentmanagementsystemtest.mapper.CourseManageMapper;
import com.example.studentmanagementsystemtest.mapper.SelectionRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

// SelectionRecordServiceImpl.java
@Service
@Slf4j
public class SelectionRecordServiceImpl
        extends ServiceImpl< SelectionRecordMapper, SelectionRecord >
        implements SelectionRecordService {

    @Autowired
    private CourseManageMapper courseMapper;
    @Autowired
    private SelectionRecordMapper selectionRecordMapper;

    @Override
    @Transactional(readOnly = true)
    public List< Long > getSelectedCourseIds(Long studentId) {
        return baseMapper.getCourseIdsByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List< Course > getStudentCourses(Long studentId) {
        List< Long > courseIds = getSelectedCourseIds(studentId);
        if (CollectionUtils.isEmpty(courseIds)) {
            return Collections.emptyList();
        }
        return courseMapper.selectBatchIds(courseIds);
    }

    @Override
    public boolean addSelectedCourseRecord(SelectionRecord SR) {
        int result = selectionRecordMapper.insert(SR);
        return result != 0;
    }
}
