package com.example.studentmanagementsystemtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.studentmanagementsystemtest.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface loginmapper extends BaseMapper< User > {

}
