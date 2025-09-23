package com.example.studentmanagementsystemtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.studentmanagementsystemtest.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Adminmapper extends BaseMapper< Admin > {
    // MyBatis-Plus已经提供了常用的CRUD方法，无需额外注解
    // 如需自定义方法，可在此添加
}