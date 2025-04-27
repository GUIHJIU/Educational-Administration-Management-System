package com.example.studentmanagementsystemtest.mapper;

import com.example.studentmanagementsystemtest.entity.StuStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Stustudentmapper {

        @Select("SELECT " +
                "`姓名`, `学号`, `性别`, `名族`, `学制`, " +
                "`学院`, `专业`, `班级`, `证件类型`, `证件号码`, " +
                "`出生日期`, `入学日期`, `政治面貌`, `电话号码`, " +
                "`当前所在级`, `预计毕业日` " +
                "FROM `stu-information` " +
                "WHERE `学号` = #{name}")
        StuStudent findByStudentId(String name);
}