package com.example.test1.service;

import com.example.test1.entity.user;
import com.example.test1.mapper.loginmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class loginservice {
    @Autowired
    loginmapper mapper;
    public user findUser(user user1){
        return mapper.selectuser(user1);
    }
}
