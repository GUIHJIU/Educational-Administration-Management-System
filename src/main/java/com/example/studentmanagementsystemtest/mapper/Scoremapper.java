package com.example.studentmanagementsystemtest.mapper;

import org.apache.ibatis.annotations.*;
import com.example.studentmanagementsystemtest.entity.Score;

import java.util.List;

@Mapper
public interface Scoremapper {

 @Insert("INSERT INTO student_scores (studentid, courseid, score) VALUES (#{studentid}, #{courseid}, #{score})")
 public void insert(Score student);

 @Select("SELECT * FROM student_scores")
 @Results({
         @Result(property = "studentid", column = "studentid"),
         @Result(property = "courseid", column = "courseid"),
         @Result(property = "score", column = "score")
 })
 List<Score> findAll();

 // 根据传入的学生对象更新学生成绩
 @Update("UPDATE student_scores SET score = #{score} WHERE studentid = #{studentid} AND courseid = #{courseid}")
 public int updateStudent(Score student);

 // 根据学生ID和课程ID删除学生成绩
 @Delete("DELETE FROM student_scores WHERE studentid = #{studentid} AND courseid = #{courseid}")
 public int deleteStudentScore(int studentid, int courseid);

 // 根据学生ID和课程ID获取学生成绩
 @Select("SELECT score FROM student_scores WHERE studentid = #{studentid} AND courseid = #{courseid}")
 public int getStudentScoreByStudentIdAndCourseId(int studentid, int courseid);
}
