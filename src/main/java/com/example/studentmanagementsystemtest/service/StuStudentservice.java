package com.example.studentmanagementsystemtest.service;

import com.example.studentmanagementsystemtest.mapper.Stustudentmapper;

import com.example.studentmanagementsystemtest.entity.StuStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuStudentservice {
    @Autowired
    private Stustudentmapper studentMapper;

    public StuStudent getStudentByName(String name)
    {
        return studentMapper.findByStudentId(name);
    }


}