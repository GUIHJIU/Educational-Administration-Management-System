package com.example.studentmanagementsystemtest.service;

import com.example.studentmanagementsystemtest.entity.Score;

import java.util.List;

public interface Scoreserviceimpl {
  //新增
  void add(Score student);

  //列表
  List<Score> getAllStudent();

  //更新学生成绩
  int updateStudent(Score student);

  // 删除学生成绩
  void deleteStudentScore(int studentid, int courseid);

  //根据学生ID和课程ID查询学生成绩
  int getStudentScoreByStudentIdAndCourseId(int studentid, int courseid);
}
