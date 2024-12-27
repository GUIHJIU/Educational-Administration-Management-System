package com.zpy.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("college")
public class College {
    private Integer id;
    private String cname;
    private String descr;
}
