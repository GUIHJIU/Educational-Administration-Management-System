package com.example.studentmanagementsystemtest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admininformation") // 指定数据库表名
public class Admin {
    @TableId(value = "id", type = IdType.AUTO) // 主键字段
    private int id;

    @TableField("admin_num") // 映射字段名
    private String adminNum;

    @TableField("admin_name")
    private String adminName;

    @TableField("admin_sex")
    private String adminSex;

    @TableField("admin_phone")
    private String adminPhone;

    @TableField("create_time")
    private String createTime;

    @TableField("update_time")
    private String updateTime;
}
