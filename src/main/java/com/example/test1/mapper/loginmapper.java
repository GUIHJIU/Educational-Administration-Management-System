package com.example.test1.mapper;

import com.example.test1.entity.user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface loginmapper {
    @Select("select * from user where username=#{username} and password=#{password}")
    user selectuser(user user1);
}
