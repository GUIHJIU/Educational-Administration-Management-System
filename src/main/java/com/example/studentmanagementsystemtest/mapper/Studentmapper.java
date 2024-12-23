package com.example.studentmanagementsystemtest.mapper;

import org.apache.ibatis.annotations.*;
import com.example.studentmanagementsystemtest.entity.Student;
import java.util.List;

@Mapper
public interface Studentmapper {

    // 插入学生信息的SQL语句
    @Insert("INSERT INTO studentinformation ( stu_num, stu_name, stu_sex, stu_phone, stu_time,stu_class,stu_major,stu_college,stu_state,stu_home) " +
            "VALUES (#{stu_num}, #{stu_name}, #{stu_sex}, #{stu_phone}, #{stu_time}, #{stu_class}, #{stu_major}, #{stu_college},#{stu_state},#{stu_home})")
    public void insert(Student student);

    // 查询所有学生信息的SQL语句，并配置结果映射
    @Select("SELECT * FROM studentinformation")

    List<Student> findAll();

    // 根据传入的学生对象更新学生信息
    @Update("UPDATE studentinformation SET stu_num = #{stu_num},stu_name = #{stu_name}, stu_sex = #{stu_sex}, " +
            "stu_class = #{stu_class}, stu_major = #{stu_major}, stu_college = #{stu_college}, stu_phone = #{stu_phone}, " +
            "stu_time = #{stu_time}, stu_state=#{stu_state}, stu_home=#{stu_home} WHERE stu_num = #{stu_num}")
    public int updateStudent(Student student);

    // 根据学号删除学生信息
    @Delete("DELETE FROM studentinformation WHERE stu_num = #{stu_num}")
    public void deleteStudent(int stu_num);

    // 根据学号获取学生信息
    @Select("SELECT * FROM studentinformation WHERE stu_num = #{stu_num}")

    public Student getStudentBystu_num(int stu_num);

}