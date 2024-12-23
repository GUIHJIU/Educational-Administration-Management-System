package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.Score;
import com.example.studentmanagementsystemtest.service.Scoreservice;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class Scorecontroller {
    private static final Logger logger = LoggerFactory.getLogger(Scorecontroller.class);
    // 注入学生服务层接口实现类，用于调用相关业务逻辑方法
    @Resource
    Scoreservice scoreservice;

    // 添加学生成绩的接口方法
    @PostMapping("/add")
    public ResponseEntity<Integer> add(@RequestBody Score student) {
        try {
            // 调用服务层的添加学生成绩方法
            scoreservice.add(student);
            // 如果添加成功，返回状态码为200
            return ResponseEntity.ok(200);
        } catch (DataAccessException e) {
            logger.error("添加学生成绩出现数据库访问异常", e);
            // 如果出现数据库访问相关异常，返回状态码为500（Internal Server Error）以及一个自定义的数据库异常错误标识值（这里返回5001，可根据实际情况调整）
            return new ResponseEntity<>(5001, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("添加学生成绩出现其他异常", e);
            // 捕获其他通用异常，同样返回500状态码及一个自定义的通用异常错误标识值（这里返回5000，可根据实际情况调整）
            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 获取学生成绩列表的接口方法
    @GetMapping("/List")
    public ResponseEntity<List<Score>> getStudentList() {
        try {
            // 调用服务层获取所有学生成绩的方法
            List<Score> studentList = scoreservice.getAllStudent();
            logger.info("成功获取到 {} 条学生成绩信息", studentList.size());
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (DataAccessException e) {
            logger.error("获取学生成绩列表出现数据库访问异常", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("获取学生成绩列表出现其他异常", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 更新学生成绩接口
    @PutMapping("/update")
    public ResponseEntity<Integer> updateStudent(@RequestBody Score student) {
        try {
            int result = scoreservice.updateStudent(student);
            if (result > 0) {
                return new ResponseEntity<>(200, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(400, HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException e) {
            logger.error("更新学生成绩出现数据库访问异常", e);
            return new ResponseEntity<>(5001, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("更新学生成绩出现其他异常", e);
            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据学生ID和课程ID获取学生成绩的接口方法
    @GetMapping("/{studentId}/{courseId}")
    public ResponseEntity<Double> getStudentScoreByStudentIdAndCourseId(@PathVariable int studentId, @PathVariable int courseId) {
        try {
            double score = scoreservice.getStudentScoreByStudentIdAndCourseId(studentId, courseId);
            if (score != -1) {
                // 如果查询到学生成绩，返回状态码为200以及一个自定义的表示查询成功的标识值（这里返回200，可根据实际情况调整）
                return new ResponseEntity<>(score, HttpStatus.OK);
            } else {
                // 如果未查询到学生成绩，返回状态码为404（NOT_FOUND）以及一个自定义的表示未找到的标识值（这里返回404，可根据实际情况调整）
                return new ResponseEntity<>(404.0, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            logger.error("根据学生ID和课程ID查询学生成绩出现数据库访问异常", e);
            // 如果出现数据库访问相关异常，返回状态码为500（Internal Server Error）以及一个自定义的数据库异常错误标识值（这里返回5001，可根据实际情况调整）
            return new ResponseEntity<>(5001.0, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("根据学生ID和课程ID查询学生成绩出现其他异常", e);
            // 捕获其他通用异常，同样返回500状态码及一个自定义的通用异常错误标识值（这里返回5000，可根据实际情况调整）
            return new ResponseEntity<>(5000.0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 删除学生成绩的接口方法，接收学生ID和课程ID作为路径参数，并完善错误处理逻辑
    @DeleteMapping("/delete/{studentId}/{courseId}")
    public ResponseEntity<Integer> deleteStudentScoreById(@PathVariable int studentId, @PathVariable int courseId) {
        // 调用服务层的删除学生成绩方法
        scoreservice.deleteStudentScore(studentId, courseId);
        return ResponseEntity.ok(200);
    }
}
