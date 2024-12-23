package com.example.test1.mapper;
import com.example.test1.entity.id;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface idmapper {
    @Select("select * from test.testproblem")
    public List<id> selectid();
}
