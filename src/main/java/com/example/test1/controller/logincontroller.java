package com.example.test1.controller;

import com.example.test1.entity.user;
import com.example.test1.service.loginservice;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
@RequestMapping("/login")
public class logincontroller {
    @Autowired
    loginservice service;
    @PostMapping("/login")
    public user finduser(user user1)
    {

    return service.findUser(user1);
    }
}
