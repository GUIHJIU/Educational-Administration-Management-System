package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.User;
import com.example.studentmanagementsystemtest.service.loginservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class logincontroller {
    @Autowired
    loginservice service;

    @PostMapping("/login")
    public User finduser(@RequestBody User user1) {
        System.out.println(service.findUser(user1));
        return service.findUser(user1);
    }
}
