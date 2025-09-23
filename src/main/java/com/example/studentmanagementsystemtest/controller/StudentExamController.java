package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.Exam;
import com.example.studentmanagementsystemtest.service.StudentExamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/studentexams")
public class StudentExamController {
    private static final Logger logger = LoggerFactory.getLogger(TeacherExamController.class);
    @Autowired
    private StudentExamService studentExamService;

    @GetMapping("/studentid")
    public List< Exam > getstudentexamList(@RequestParam("studentid") Integer studentid) {
            System.out.println("1");
        try {
            List< Exam > getstudentexamList = studentExamService.getClassInfoByStudentId(studentid);
            logger.info("成功获取到 {} 条考试信息", getstudentexamList.size());
            System.out.println(getstudentexamList);
            return new ResponseEntity<>(getstudentexamList, HttpStatus.OK).getBody();
        } catch (DataAccessException e)
        {
            logger.error("获取考试信息列表出现数据库访问异常", e);
            return (List< Exam >) new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e)
        {
            logger.error("获取考信息列表出现其他异常", e);
            return (List< Exam >) new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
}
