package com.example.studentmanagementsystemtest.mapper;

import org.apache.ibatis.annotations.*;
import com.example.studentmanagementsystemtest.entity.Student;
import java.util.List;

@Mapper
public interface Studentmapper {

 // 插入学生信息的SQL语句
 @Insert("INSERT INTO studentinformation ( stu_num, stu_name, stu_sex, stu_phone, stu_time,stu_class,stu_major,stu_college,stu_state,stu_home) " +
         "VALUES (#{stuNum}, #{stuName}, #{stuSex}, #{stuPhone}, #{stuTime}, #{stuClass}, #{stuMajor}, #{stuCollege},#{stuState},#{stuHome})")
 public void insert(Student student);

 // 查询所有学生信息的SQL语句，并配置结果映射
 @Select("SELECT * FROM studentinformation")
 @Results({
         @Result(property = "stuNum", column = "stu_num"),
         @Result(property = "stuName", column = "stu_name"),
         @Result(property = "stuSex", column = "stu_sex"),
         @Result(property = "stuPhone", column = "stu_phone"),
         @Result(property = "stuTime", column = "stu_time"),
         @Result(property = "stuClass", column = "stu_class"),
         @Result(property = "stuMajor", column = "stu_major"),
         @Result(property = "stuCollege", column = "stu_college"),
         @Result(property = "stuState", column = "stu_state"),
         @Result(property = "stuHome", column = "stu_home")
 })
 List<Student> findAll();

 // 根据传入的学生对象更新学生信息
 @Update("UPDATE studentinformation SET stu_num = #{stuNum},stu_name = #{stuName}, stu_sex = #{stuSex}, " +
         "stu_class = #{stuClass}, stu_major = #{stuMajor}, stu_college = #{stuCollege}, stu_phone = #{stuPhone}, " +
         "stu_time = #{stuTime}, stu_state=#{stuState}, stu_home=#{stuHome} WHERE stu_num = #{stuNum}")
 public int updateStudent(Student student);

 // 根据学号删除学生信息
 @Delete("DELETE FROM studentinformation WHERE stu_num = #{stuNum}")
 public void deleteStudent(int stuNum);

 // 根据学号获取学生信息
 @Select("SELECT * FROM studentinformation WHERE stu_num = #{stuNum}")
 public Student getStudentByStuNum(int stuNum);

 
}