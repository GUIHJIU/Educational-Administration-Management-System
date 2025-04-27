package com.example.studentmanagementsystemtest.service;

import com.example.studentmanagementsystemtest.entity.User;
import com.example.studentmanagementsystemtest.mapper.loginmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class loginservice {
    @Autowired
    loginmapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(loginservice.class);

    public User findUser(User user1) {

        return mapper.selectuser(user1);
    }
}
