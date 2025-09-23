package com.example.studentmanagementsystemtest.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.studentmanagementsystemtest.entity.Teacher;
import com.example.studentmanagementsystemtest.mapper.Teachermapper;
import org.springframework.stereotype.Service;

@Service
public class Teacherserviceimpl extends ServiceImpl< Teachermapper, Teacher > implements Teacherservice {
    // MyBatis-Plus已经提供了常用的CRUD方法，可以直接使用
    // 如需自定义业务逻辑，可在此添加
}
