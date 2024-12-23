package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.Student;
import com.example.studentmanagementsystemtest.service.Studentservice;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class Studentcontroller {
    private static final Logger logger = LoggerFactory.getLogger(Studentcontroller.class);
    // 注入学生服务层接口实现类，用于调用相关业务逻辑方法
    @Resource
    Studentservice studentservice;

    // 添加学生信息的接口方法
    @PostMapping("/add")
    public ResponseEntity<Integer> add(@RequestBody Student student) {
        try {
            // 调用服务层的添加学生方法
            studentservice.add(student);
            // 如果添加成功，返回状态码为200
            return ResponseEntity.ok(200);
        } catch (DataAccessException e)
        {
            logger.error("添加学生信息出现数据库访问异常", e);
            // 如果出现数据库访问相关异常，返回状态码为500（Internal Server Error）以及一个自定义的数据库异常错误标识值（这里返回5001，可根据实际情况调整）
            return new ResponseEntity<>(5001, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e)
        {
            logger.error("添加学生信息出现其他异常", e);
            // 捕获其他通用异常，同样返回500状态码及一个自定义的通用异常错误标识值（这里返回5000，可根据实际情况调整）
            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 获取学生信息列表的接口方法
    @GetMapping("/List")
    public ResponseEntity<List<Student>> getStudentList() {
        try {
            // 调用服务层获取所有学生信息的方法
            List<Student> studentList = studentservice.getAllStudent();
            logger.info("成功获取到 {} 条学生信息", studentList.size());
            System.out.println(studentList);
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (DataAccessException e) {
            logger.error("获取学生信息列表出现数据库访问异常", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("获取学生信息列表出现其他异常", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 更新学生信息接口
    @PutMapping("/update")
    public ResponseEntity<Integer> updateStudent(@RequestBody Student student)
    {
        try
        {
            int result = studentservice.updateStudent(student);
            if (result > 0)
            {
                return new ResponseEntity<>(200, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(400, HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException e) {
            logger.error("更新学生信息出现数据库访问异常", e);
            return new ResponseEntity<>(5001, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("更新学生信息出现其他异常", e);
            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据学号获取学生信息的接口方法
    @GetMapping("/search/{stu_num}")
    public ResponseEntity<Student> getStudentBystu_num(@PathVariable int stu_num)
    {
        try {
            Student student = studentservice.getStudentBystu_num(stu_num);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (DataAccessException e) {
            logger.error("根据学号查询学生信息出现数据库访问异常", e);
            // 如果出现数据库访问相关异常，返回状态码为500（Internal Server Error）
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("根据学号查询学生信息出现其他异常", e);
            // 捕获其他通用异常，同样返回500状态码
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 删除学生信息的接口方法，接收学号作为路径参数，并完善错误处理逻辑
    @DeleteMapping("/delete/{stu_num}")
    public ResponseEntity<Integer> deleteStudentById(@PathVariable int stu_num)
    {
        // 调用服务层的删除学生方法
        studentservice.deleteStudent(stu_num);
        return ResponseEntity.ok(200);
    }


}