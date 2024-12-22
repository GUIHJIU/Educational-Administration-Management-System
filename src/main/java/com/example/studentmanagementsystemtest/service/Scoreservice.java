package com.example.studentmanagementsystemtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.studentmanagementsystemtest.mapper.Scoremapper;
import com.example.studentmanagementsystemtest.entity.Score;
import java.util.List;

@Service
public class Scoreservice implements Scoreserviceimpl {
    @Autowired
    Scoremapper scoremapper;

    //增加
    @Override
    public void add(Score student) {
        scoremapper.insert(student);
    }

    //显示

    @Override
    public List<Score> getAllStudent() {
        return scoremapper.findAll();
    }

    @Override
    public int updateStudent(Score student) {
        return scoremapper.updateStudent(student);
    }

    @Override
    public void deleteStudentScore(int studentid, int courseid) {
        scoremapper.deleteStudentScore(studentid, courseid);
    }

    @Override
    public int getStudentScoreByStudentIdAndCourseId(int studentid, int courseid) {
        return scoremapper.getStudentScoreByStudentIdAndCourseId(studentid, courseid);
    }
}
