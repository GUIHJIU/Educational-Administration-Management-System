package com.zpy.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("course")
public class Course {
    private Integer id;
    private String cname;
    private String major;
//    private String teacher;
    private String address;
    private Integer num;
    private Integer stock;
    private String cimage;
    private String cbook;
}
