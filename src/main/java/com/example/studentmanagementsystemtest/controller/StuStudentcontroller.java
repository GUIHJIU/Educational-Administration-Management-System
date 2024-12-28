package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.StuStudent;
import com.example.studentmanagementsystemtest.service.StuStudentservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StuStudentcontroller {
    private static final Logger logger = LoggerFactory.getLogger(StuStudentcontroller.class);

    @Autowired
    private StuStudentservice studentService;

    @GetMapping("/student-info/{username}")
    public ResponseEntity<?> getStudentInfo(@PathVariable String username) {
        try {
            String decodedName = URLDecoder.decode(username, StandardCharsets.UTF_8);
            logger.info("Received request for student name: {}", decodedName);

            StuStudent student = studentService.getStudentByName(decodedName);

            if (student == null) {
                logger.warn("No student found with name: {}", decodedName);
                return ResponseEntity.status(404)
                        .body(Map.of(
                                "message", "未找到学生信息",
                                "detail", "没有找到姓名为 " + decodedName + " 的学生"
                        ));
            }

            return ResponseEntity.ok(student);
        } catch (Exception e) {
            logger.error("Error getting student info for name: " + username, e);
            return ResponseEntity.status(500)
                    .body(Map.of(
                            "message", "获取学生信息失败",
                            "detail", e.getMessage()
                    ));
        }
    }

    @GetMapping("/")
    public ResponseEntity<String> root() {
        return ResponseEntity.ok("API root is working");
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        logger.info("Received test request");
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "API is working"
        ));
    }


}

class ErrorResponse {
    private String message;
    private String detail;

    public ErrorResponse(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    // Getters and setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
}