package com.example.test1.service;

import com.example.test1.mapper.idmapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.test1.entity.id;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class idservice {
    @Autowired
    idmapper mapper;
    public List <id> findId(){
        return mapper.selectid();
    }
}
