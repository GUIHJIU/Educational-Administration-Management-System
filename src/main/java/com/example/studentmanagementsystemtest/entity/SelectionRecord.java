package com.example.studentmanagementsystemtest.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("select_record")
public class SelectionRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField(value = "studentId")
    private Long studentId;
    @TableField(value = "courseId")
    private Long courseId;
    @TableLogic
    private Integer deleted;
}
