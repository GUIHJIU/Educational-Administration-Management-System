package com.example.studentmanagementsystemtest.mapper;

import com.example.studentmanagementsystemtest.entity.user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface loginmapper {
    @Select("SELECT id, username, password, position FROM user WHERE username = #{username} AND password = #{password}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "position", column = "position")
    })
    user selectuser(user user1);
}
