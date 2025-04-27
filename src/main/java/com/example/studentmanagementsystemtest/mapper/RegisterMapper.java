package com.example.studentmanagementsystemtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.studentmanagementsystemtest.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RegisterMapper extends BaseMapper< User > {
    @Select("SELECT COUNT(*) FROM user WHERE username = #{username}")
    Integer existsByUsername(@Param("username") String username);
}
