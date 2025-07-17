package com.example.studentmanagementsystemtest.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.studentmanagementsystemtest.entity.Course;
import com.example.studentmanagementsystemtest.service.CourseManageServiceImpl;
import com.example.studentmanagementsystemtest.util.ErrorCode;
import com.example.studentmanagementsystemtest.util.PageResult;
import com.example.studentmanagementsystemtest.util.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@Tag(name = "课程管理接口")
public class CourseManageController {
    @Autowired
    private CourseManageServiceImpl courseService;

    // 统一响应格式 + 分页支持
    @GetMapping
    @Operation(summary = "获取所有课程")
    public ResponseResult< PageResult< Course > > getAllCourses(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page< Course > coursePage = courseService.page(new Page<>(page, size));
        return ResponseResult.success(new PageResult<>(coursePage.getTotal(), coursePage.getRecords()));
    }

    // 动态查询
    @GetMapping("/search")
    @Operation(summary = "动态查询课程")
    public ResponseResult< List< Course > > searchCourses(
            @RequestParam("queryType") String queryType,
            @RequestParam("queryParam") String queryParam) {
        return ResponseResult.success(courseService.SearchCourse(queryType, queryParam));
    }

    // 参数校验 + 统一响应
    @PostMapping
    @Operation(summary = "创建课程")
    public ResponseResult< String > createCourse(@Valid @RequestBody Course course) {
        courseService.InsertCourse(course);
        return ResponseResult.success("课程创建成功");
    }

    // 乐观锁支持
    @PutMapping
    @Operation(summary = "更新课程")
    public ResponseResult< String > updateCourse(@Valid @RequestBody Course course) {
        int result = courseService.UpdateCourse(course);
        return result > 0 ? ResponseResult.success("更新成功") : ResponseResult.error(ErrorCode.UPDATE_COURSE_ERROR);
    }

    // 逻辑删除
    @DeleteMapping("/{courseId}")
    @Operation(summary = "删除课程")
    public ResponseResult< String > deleteCourse(@PathVariable Long courseId) {
        int result = courseService.DeleteCourse(courseId);
        return result > 0 ? ResponseResult.success("删除成功") : ResponseResult.error(ErrorCode.COURSE_NOT_EXIST);
    }

    // 新增选课接口
    @PostMapping("/{courseId}/select")
    @Operation(summary = "学生选课")
    public ResponseResult< Boolean > selectCourse(@PathVariable Long courseId,
                                                  @RequestParam Long studentId) {
        return courseService.deductStock(courseId, studentId);
    }
}
