package com.zpy.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student")
public class Student {
    private Integer id;
    private String sname;
    private String password;
    private String sex;

    private Integer age;
    private String major;
    private String college;
    private String simage;
}
