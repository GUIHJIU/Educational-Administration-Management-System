package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.service.AnalysisService;
import com.example.studentmanagementsystemtest.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @PostMapping("/report/{studentId}")
    public ResponseResult< Map< String, Object > > generateStudentReport(
            @PathVariable Long studentId,
            @RequestParam(defaultValue = "deepseek") String modelType) {

        Map< String, Object > result = analysisService.generateStudentReport(studentId, modelType);

        if ((Boolean) result.get("success")) {
            return ResponseResult.success(result);
        } else {
            return ResponseResult.error(500, (String) result.get("message"));
        }
    }
}
