package com.example.studentmanagementsystemtest.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.studentmanagementsystemtest.entity.Admin;
import com.example.studentmanagementsystemtest.mapper.Adminmapper;
import org.springframework.stereotype.Service;

@Service
public class Adminserviceimpl extends ServiceImpl< Adminmapper, Admin > implements Adminservice {

}
