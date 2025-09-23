package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.Teacher;
import com.example.studentmanagementsystemtest.service.Teacherservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    Teacherservice teacherservice;

    // 添加教师信息的接口方法
    @PostMapping("/add")
    public ResponseEntity<Integer> add(@RequestBody Teacher teacher) {
        try {
            // 调用服务层的添加教师方法
            boolean isSuccess = teacherservice.save(teacher);
            // 如果添加成功，返回状态码为200
            return ResponseEntity.ok(isSuccess ? 200 : 400);
        } catch (Exception e) {
            logger.error("添加教师信息出现异常", e);
            // 捕获其他通用异常，返回500状态码及一个自定义的通用异常错误标识值（这里返回5000，可根据实际情况调整）
            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 获取教师信息列表的接口方法
    @GetMapping("/List")
    public ResponseEntity<List<Teacher>> getTeacherList() {
        try {
            // 调用服务层获取所有教师信息的方法
            List< Teacher > teacherList = teacherservice.list();
            logger.info("成功获取到 {} 条教师信息", teacherList.size());
            return new ResponseEntity<>(teacherList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("获取教师信息列表出现异常", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 更新教师信息接口
    @PutMapping("/update")
    public ResponseEntity< Integer > updateTeacher(@RequestBody Teacher teacher) {
        try {
            boolean isSuccess = teacherservice.updateById(teacher);
            if (isSuccess) {
                return new ResponseEntity<>(200, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(400, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("更新教师信息出现异常", e);
            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据ID获取教师信息的接口方法
    @GetMapping("/{id}")
    public ResponseEntity< Teacher > getTeacherById(@PathVariable int id) {
        try {
            Teacher teacher = teacherservice.getById(id);
            if (teacher != null) {
                return new ResponseEntity<>(teacher, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("根据ID查询教师信息出现异常", e);
            // 捕获其他通用异常，同样返回500状态码
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 删除教师信息的接口方法，接收ID作为路径参数
    @DeleteMapping("/delete/{id}")
    public ResponseEntity< Integer > deleteTeacherById(@PathVariable int id) {
        try {
            // 调用服务层的删除教师方法
            boolean isSuccess = teacherservice.removeById(id);
            return ResponseEntity.ok(isSuccess ? 200 : 400);
        } catch (Exception e) {
            logger.error("删除教师信息出现异常", e);
            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
