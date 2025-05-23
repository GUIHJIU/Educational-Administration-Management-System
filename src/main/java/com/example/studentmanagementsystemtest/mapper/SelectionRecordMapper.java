package com.example.studentmanagementsystemtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.studentmanagementsystemtest.entity.SelectionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SelectionRecordMapper
        extends BaseMapper< SelectionRecord > {

    @Select("SELECT courseId FROM select_record " +
            "WHERE studentId = #{studentId} AND deleted = 0")
    List< Long > getCourseIdsByStudentId(@Param("studentId") Long studentId);


}
