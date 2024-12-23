package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.user;
import com.example.studentmanagementsystemtest.service.loginservice;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

@RestController
@RequestMapping("/login")
public class logincontroller {
    @Autowired
    loginservice service;
    @PostMapping("/login")
    public user finduser(@RequestBody user user1)
    {
        System.out.println(service.findUser(user1));
        return service.findUser(user1);
    }
}
