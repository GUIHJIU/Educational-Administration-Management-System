package com.zpy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zpy.pojo.Teacher;

public interface TeacherService extends IService<Teacher> {
    boolean login(String username, String password);
}
