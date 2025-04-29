package com.example.studentmanagementsystemtest.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import lombok.*;

@Data
@TableName("user")
public class User {
    private String username;
    private String password;
    private int id;
    private String position;
    private String salt;


}
