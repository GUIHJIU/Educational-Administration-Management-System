package com.zpy.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("major")
public class Major {
    private Integer id;
    private String mname;
    private String college;
    private String descr;
}
