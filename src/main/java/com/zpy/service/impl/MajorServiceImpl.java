package com.zpy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zpy.mapper.MajorMapper;
import com.zpy.pojo.Major;
import com.zpy.service.MajorService;
import org.springframework.stereotype.Service;

@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major>implements MajorService {
}
