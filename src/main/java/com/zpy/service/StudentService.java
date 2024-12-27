package com.zpy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zpy.mapper.StudentMapper;
import com.zpy.pojo.Student;

public interface StudentService extends IService<Student> {
    boolean login(String username, String password);
}
