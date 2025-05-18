package com.example.studentmanagementsystemtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.studentmanagementsystemtest.entity.SelectionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// SelectionRecordMapper.java
@Mapper
public interface SelectionRecordMapper
        extends BaseMapper< SelectionRecord > {

    @Select("SELECT course_id FROM selection_record " +
            "WHERE student_id = #{studentId} AND deleted = 0")
    List< Long > getCourseIdsByStudentId(@Param("studentId") Long studentId);
}
