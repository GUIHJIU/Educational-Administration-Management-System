package com.zpy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zpy.mapper.CollegeMapper;
import com.zpy.pojo.College;
import com.zpy.service.CollegeService;
import org.springframework.stereotype.Service;

@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College>implements CollegeService {
}
