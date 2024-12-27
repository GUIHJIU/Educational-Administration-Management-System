package com.zpy.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zpy.mapper.UserMapper;
import com.zpy.pojo.User;
import com.zpy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean login(String username, String password) {

        QueryWrapper<User>qw=new QueryWrapper<>();
        qw.eq("username",username);
        qw.eq("password", DigestUtil.md5Hex(password));
        User user = userMapper.selectOne(qw);
        if (user==null){
            return false;
        }else {
            return true;
        }

    }
}
