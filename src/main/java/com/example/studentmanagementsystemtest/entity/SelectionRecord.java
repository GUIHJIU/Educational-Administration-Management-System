package com.example.studentmanagementsystemtest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

// SelectionRecord.java
@TableName("selection_record")
public class SelectionRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Long courseId;
    @TableLogic
    private Integer deleted;
    // 其他字段及getter/setter
}
