package com.zpy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zpy.pojo.CountNumber;
import com.zpy.pojo.StudentCourse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {
    List<StudentCourse> listStudentCourse(Integer id);

    List<CountNumber> queryAll();

    List<StudentCourse> listMyCourse(Integer userId, String cname);
}
