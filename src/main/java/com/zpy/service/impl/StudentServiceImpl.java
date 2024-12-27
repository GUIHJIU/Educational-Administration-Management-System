package com.zpy.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zpy.mapper.StudentMapper;
import com.zpy.pojo.Student;
import com.zpy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Override
    public boolean login(String username, String password) {
        QueryWrapper<Student>qw=new QueryWrapper<>();
        qw.eq("sname",username);
        qw.eq("password", DigestUtil.md5Hex(password));
        Student student = studentMapper.selectOne(qw);
        if (student==null){
            return false;
        }else {
            return true;
        }

    }
}
