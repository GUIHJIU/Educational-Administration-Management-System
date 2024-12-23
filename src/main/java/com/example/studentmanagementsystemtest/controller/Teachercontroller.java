package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.Teacher;
import com.example.studentmanagementsystemtest.service.Teacherservice;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/exams")
public class Teachercontroller {
    private static final Logger logger = LoggerFactory.getLogger(Teachercontroller.class);
    // 注入学生服务层接口实现类，用于调用相关业务逻辑方法
    @Resource
    Teacherservice teacherservice;

    // 添加学生信息的接口方法
    @PostMapping("/add")
    public ResponseEntity<Integer> add(@RequestBody Teacher teacher) {
        try {
            // 调用服务层的添加学生方法
            teacherservice.add(teacher);
            // 如果添加成功，返回状态码为200
            return ResponseEntity.ok(200);
        } catch (DataAccessException e)
        {
            logger.error("添加考试信息出现数据库访问异常", e);
            // 如果出现数据库访问相关异常，返回状态码为500（Internal Server Error）以及一个自定义的数据库异常错误标识值（这里返回5001，可根据实际情况调整）
            return new ResponseEntity<>(5001, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e)
        {
            logger.error("添加考试信息出现其他异常", e);
            // 捕获其他通用异常，同样返回500状态码及一个自定义的通用异常错误标识值（这里返回5000，可根据实际情况调整）
            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 获取学生信息列表的接口方法
    @GetMapping("/List")
    public ResponseEntity<List<Teacher>> getTeacherList() {
        try {
            // 调用服务层获取所有学生信息的方法
            List<Teacher> teacherList = teacherservice.getAllTeacher();
            logger.info("成功获取到 {} 条考试信息", teacherList.size());
            return new ResponseEntity<>(teacherList, HttpStatus.OK);
        } catch (DataAccessException e)
        {
            logger.error("获取考试信息列表出现数据库访问异常", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e)
        {
            logger.error("获取考信息列表出现其他异常", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 更新学生信息接口
    @PutMapping("/update")
    public ResponseEntity<Integer> updateTeacher(@RequestBody Teacher teacher)
    {

        try
        {
            int result = teacherservice.updateTeacher(teacher);
            if (result > 0)
            {
                return new ResponseEntity<>(200, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(400, HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException e) {
            logger.error("更新考试信息出现数据库访问异常", e);
            return new ResponseEntity<>(5001, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("更新考试信息出现其他异常", e);
            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据学号获取学生信息的接口方法
    @GetMapping("/{class_id}")
    public ResponseEntity<Integer> getTeacher(@PathVariable String class_id) {
        try {
            Teacher teacher = teacherservice.getTeacher(class_id);
            if (teacher!= null) {
                // 如果查询到学生信息，返回状态码为200以及一个自定义的表示查询成功的标识值（这里返回200，可根据实际情况调整）
                return new ResponseEntity<>(200, HttpStatus.OK);
            } else {
                // 如果未查询到学生信息，返回状态码为404（NOT_FOUND）以及一个自定义的表示未找到的标识值（这里返回404，可根据实际情况调整）
                return new ResponseEntity<>(404, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            logger.error("查询考试信息出现数据库访问异常", e);
            // 如果出现数据库访问相关异常，返回状态码为500（Internal Server Error）以及一个自定义的数据库异常错误标识值（这里返回5001，可根据实际情况调整）
            return new ResponseEntity<>(5001, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("查询考试信息出现其他异常", e);
            // 捕获其他通用异常，同样返回500状态码及一个自定义的通用异常错误标识值（这里返回5000，可根据实际情况调整）
            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 删除学生信息的接口方法，接收学号作为路径参数，并完善错误处理逻辑
    @DeleteMapping("/delete/{class_id}")
    public ResponseEntity<Integer> deleteTeacher(@PathVariable String class_id)
    {
        // 调用服务层的删除学生方法
        teacherservice.deleteTeacher(class_id);
        return ResponseEntity.ok(200);
    }


}