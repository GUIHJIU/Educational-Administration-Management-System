package com.example.studentmanagementsystemtest.mapper;

import com.example.studentmanagementsystemtest.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseManageMapper {
    @Select("select * from course")
    List< Course > selectAllCourse();
    @Select("select * from course where ${queryType} = #{queryParam}")
    List<Course> searchCourse(String queryType,String queryParam);
    @Insert("INSERT INTO course (courseName, credit, classHour,courseType,Teacher) VALUES ( #{courseName}, #{credit}, #{classHour}, #{courseType}, #{Teacher})")
    void insertCourse(Course course);
    @Update({
            "<script>",
            "UPDATE course",
            "<set>",
            "<if test='course.courseName != null'>courseName = #{course.courseName},</if>",
            "<if test='course.credit != null'>credit = #{course.credit},</if>",
            "<if test='course.classHour != null'>classHour = #{course.classHour},</if>",
            "<if test='course.courseType != null'>courseType = #{course.courseType},</if>",
            "<if test='course.Teacher != null'>Teacher = #{course.Teacher},</if>",
            "</set>",
            "WHERE courseId = #{course.courseId}",
            "</script>"
    })
    int updateCourse(@Param("course") Course course);
    @Delete("delete from course where courseId=#{courseId}")
    int deleteCourse(int courseId);

}
