package com.zpy.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zpy.pojo.Course;
import com.zpy.pojo.Major;
import com.zpy.service.CourseService;
import com.zpy.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private MajorService  majorService;
    @Value("${location}")
    private String location;
    @RequestMapping("listCourse")
    public String listCourse(@RequestParam(value = "pageNum",defaultValue = "1",required = false)Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "6",required = false)Integer pageSize, Model model, Course course){
        if (pageNum==null||pageNum.equals("")||pageNum<=0){
            pageNum=1;
        }
        if (pageSize==null||pageSize.equals("")||pageSize<=0){
            pageSize=6;
        }
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Course>qw=new QueryWrapper<>();
        if (course.getCname()!=null){
            qw.like("cname",course.getCname());
        }
        List<Course> list = courseService.list(qw);
        PageInfo<Course>pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin-course-list";
    }

    @RequestMapping("preSaveCourse")
    public String preSaveCourse(Model model){
        List<Major> list = majorService.list(null);
        model.addAttribute("majorList",list);
        return "admin-course-save";
    }

    @RequestMapping("saveCourse")
    public String saveCourse(Course course, MultipartFile file,MultipartFile fileBook){
        if (!file.isEmpty()){
            transfilea(course,file);
        }
        if (!fileBook.isEmpty()){
            transfileb(course,fileBook);
        }
        boolean save = courseService.save(course);
        return "redirect:/course/listCourse";
    }

    private void transfileb(Course course, MultipartFile fileBook) {
        String originalFilename = fileBook.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String prefix=System.nanoTime()+"";
        String path=prefix+suffix;

        File file1 = new File(location);
        if (!file1.exists()){
            file1.mkdirs();
        }
        File file2 = new File(file1, path);
        try {
            fileBook.transferTo(file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        course.setCbook(path);
    }

    private void transfilea(Course course, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String prefix=System.nanoTime()+"";
        String path=prefix+suffix;

        File file1 = new File(location);
        if (!file1.exists()){
            file1.mkdirs();
        }
        File file2 = new File(file1, path);
        try {
            file.transferTo(file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        course.setCimage(path);
    }

    @RequestMapping("preUpdateCourse/{id}")
    public String preUpdateCourse(@PathVariable Integer id,Model model){
        Course byId = courseService.getById(id);
        model.addAttribute("course",byId);
        List<Major> list = majorService.list(null);
        model.addAttribute("majorList",list);
        return "admin-course-update";
    }

    @RequestMapping("updateCourse")
    public String updateCourse(Course course,MultipartFile file){
        if (!file.isEmpty()){
            transfilea(course,file);
        }
        courseService.updateById(course);
        return "redirect:/course/listCourse";
    }

    @RequestMapping("delCourse/{id}")
    public String delCourse(@PathVariable Integer id){
        boolean b = courseService.removeById(id);
        return "redirect:/course/listCourse";
    }

    @ResponseBody
    @RequestMapping("batchDeleteCourse")
    public String batchDeleteCourse(String idList){
        String[] split = StrUtil.split(idList, ",");
        List<Integer>list=new ArrayList<>();

        for (String s : split) {
            if (!s.isEmpty()){
                list.add(Integer.valueOf(s));
            }
        }

        boolean b = courseService.removeByIds(list);
        if (b){
            return "OK";
        }else {
            return "error";
        }
    }
}
