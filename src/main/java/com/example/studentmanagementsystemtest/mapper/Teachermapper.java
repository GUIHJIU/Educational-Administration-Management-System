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

    // 根据传入的学生对象更新学生信息

    // 更新学生信息的SQL语句，根据实际表结构和字段进行调整
    @Update("UPDATE examinformation SET classid = #{class_id},subject = #{subjects}, time = #{time_exam},location=#{locations} WHERE classid = #{class_id} ")
    public int updateTeacher(Teacher Teacher);


    // 根据学号删除学生信息
    @Delete("DELETE FROM examinformation WHERE classid = #{class_id}")
    public void deleteTeacher( String class_id);

    // 根据学号获取学生信息
    @Select("SELECT * FROM examinformation WHERE classid = #{class_id}")
    public Teacher getTeacher(String class_id);

}
