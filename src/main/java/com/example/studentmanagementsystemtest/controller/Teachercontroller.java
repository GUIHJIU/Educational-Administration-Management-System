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
    @Resource
    Teacherservice teacherservice;

    @PostMapping("/add")
    public ResponseEntity<Integer> add(@RequestBody Teacher teacher) {
        try {

            teacherservice.add(teacher);

            return ResponseEntity.ok(200);
        } catch (DataAccessException e)
        {
            logger.error("添加考试信息出现数据库访问异常", e);

            return new ResponseEntity<>(5001, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e)
        {
            logger.error("添加考试信息出现其他异常", e);

            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/List")
    public ResponseEntity<List<Teacher>> getTeacherList() {
        try {

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


    @GetMapping("/{class_id}")
    public ResponseEntity<Integer> getTeacher(@PathVariable String class_id) {
        try {
            Teacher teacher = teacherservice.getTeacher(class_id);
            if (teacher!= null) {

                return new ResponseEntity<>(200, HttpStatus.OK);
            } else {

                return new ResponseEntity<>(404, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            logger.error("查询考试信息出现数据库访问异常", e);

            return new ResponseEntity<>(5001, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("查询考试信息出现其他异常", e);

            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{class_id}")
    public ResponseEntity<Integer> deleteTeacher(@PathVariable String class_id)
    {

        teacherservice.deleteTeacher(class_id);
        return ResponseEntity.ok(200);
    }


}