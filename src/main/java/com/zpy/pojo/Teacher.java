package com.zpy.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("teacher")
public class Teacher {
    private Integer id;
    private String tname;
    private String password;
    private String sex;
    private String email;
    private String phone;
    private Integer age;
    private String major;
    private String timage;
}
