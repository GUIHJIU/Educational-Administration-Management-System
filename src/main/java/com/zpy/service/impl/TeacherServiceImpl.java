package com.zpy.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zpy.mapper.TeacherMapper;
import com.zpy.pojo.Teacher;
import com.zpy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public boolean login(String username, String password) {
        QueryWrapper<Teacher>qw=new QueryWrapper<>();
        qw.eq("tname",username);
        qw.eq("password", DigestUtil.md5Hex(password));
        Teacher teacher = teacherMapper.selectOne(qw);
        if (teacher==null){
            return false;
        }else {
            return true;
        }

    }
}
