package com.example.test1.controller;

import com.example.test1.entity.id;
import com.example.test1.service.idservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/id")
public class idcontroller {
    @Autowired
    idservice service;
    @GetMapping("/findid")
    public List< id > findid(){
        return service.findId();
    }
}
