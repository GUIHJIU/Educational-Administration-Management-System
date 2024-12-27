package com.zpy.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("teacher_course")
public class TeacherCourse {
    @TableId
    private Integer id;
    private Integer tid;
    private Integer cid;

    @TableField(exist = false)
    private Teacher teacher;
    @TableField(exist = false)
    private Course course;
}
