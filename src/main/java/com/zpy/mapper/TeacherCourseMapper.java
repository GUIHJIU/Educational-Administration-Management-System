package com.zpy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zpy.pojo.TeacherCourse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherCourseMapper extends BaseMapper<TeacherCourse> {
    List<TeacherCourse> listTeacherCourse(Integer id);

    List<TeacherCourse> listMyCourse(Integer userId, String cname);
}
