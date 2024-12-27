package com.zpy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zpy.mapper.CourseMapper;
import com.zpy.pojo.Course;
import com.zpy.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>implements CourseService {
}
