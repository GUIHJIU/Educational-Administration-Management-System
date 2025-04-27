package com.example.studentmanagementsystemtest.mapper;
import org.apache.ibatis.annotations.*;
import com.example.studentmanagementsystemtest.entity.Teacher;
import java.util.List;

@Mapper
public interface Teachermapper
{

    @Insert("INSERT INTO examinformation ( classid, subject, time, location) " +
            "VALUES (#{class_id}, #{subjects}, #{time_exam}, #{locations})")
    public void insert(Teacher teacher);

    @Select("SELECT * FROM examinformation")
    @Results({
            @Result(property = "class_id", column = "classid"),
            @Result(property = "subjects", column = "subject"),
            @Result(property = "time_exam", column = "time"),
            @Result(property = "locations", column = "location"),
    })
    List<Teacher> findAll();

    @Update("UPDATE examinformation SET classid = #{class_id},subject = #{subjects}, time = #{time_exam},location=#{locations} WHERE classid = #{class_id} ")
    public int updateTeacher(Teacher Teacher);


    @Delete("DELETE FROM examinformation WHERE classid = #{class_id}")
    public void deleteTeacher( String class_id);

    @Select("SELECT * FROM examinformation WHERE classid = #{class_id}")
    public Teacher getTeacher(String class_id);

}
