package com.zpy.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zpy.pojo.*;
import com.zpy.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("student")
public class StudentController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private TeacherService teacherService;
    @Value("${location}")
    private String location;
    @RequestMapping("listStudent")
    public String listStudent(@RequestParam(value = "pageNum",defaultValue = "1",required = false)Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "6",required = false)Integer pageSize, Model model, Student student){

        if (pageNum==null|| pageNum.equals("")||pageNum<=0){
            pageNum=1;
        }
        if (pageSize==null|| pageSize.equals("")||pageSize<=0){
            pageSize=6;
        }
        PageHelper.startPage(pageNum,pageSize);

        QueryWrapper<Student>qw=new QueryWrapper<>();
        if (student.getSname()!=null){
            qw.like("sname",student.getSname());
        }
        List<Student> list = studentService.list(qw);
        PageInfo<Student>pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin-student-list";

    }

    @RequestMapping("preSaveStudent")
    public String preSaveStudent(   Model model){
        List<College> list = collegeService.list(null);
        List<Major> list1 = majorService.list(null);
        model.addAttribute("majorList",list1);
        model.addAttribute("collegeList",list);
        return "admin-student-save";
    }

    @RequestMapping("saveStudent")
    public String saveStudent(Student student, MultipartFile file){

        if (!file.isEmpty()){
            transfile(student,file);
        }

        studentService.save(student);

        return "redirect:/student/listStudent";
    }

    private void transfile(Student student, MultipartFile file) {
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
        student.setSimage(path);
    }

    @RequestMapping("preUpdateStudent/{id}")
    public String preUpdateStudent(@PathVariable Integer id,Model model){
        Student byId = studentService.getById(id);
        model.addAttribute("student",byId);
        List<College> list = collegeService.list(null);
        List<Major> list1 = majorService.list(null);
        model.addAttribute("majorList",list1);
        model.addAttribute("collegeList",list);
        return "admin-student-update";
    }

    @RequestMapping("updateStudent")
    public String updateStudent(Student student,MultipartFile file){
        if (!file.isEmpty()){
            transfile(student,file);
        }
        boolean b = studentService.updateById(student);
        return "redirect:/student/listStudent";
    }

    @RequestMapping("delStudent/{id}")
    public String delStudent(@PathVariable Integer id){
        boolean b = studentService.removeById(id);
        return "redirect:/student/listStudent";
    }

    @ResponseBody
    @RequestMapping("batchDeleteStudent")
    public String batchDeleteStudent(String idList){
        String[] split = StrUtil.split(idList, ",");
        List<Integer>list=new ArrayList<>();

        for (String s : split) {
            if (!s.isEmpty()){
                list.add(Integer.valueOf(s));
            }
        }

        boolean b = studentService.removeByIds(list);
        if (b){
            return "OK";
        }else {
            return "error";
        }
    }

    @RequestMapping("listCourse")
    public String listCourse(@RequestParam(value = "pageNum",defaultValue = "1",required = false)Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "6",required = false)Integer pageSize, Course course,Model model){

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
        List<Course> list = courseService.list(null);
        PageInfo<Course>pageInf=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInf);
        return "student-course-list";
    }


    @RequestMapping("listMyCourse")
    public String listMyCourse(String cname, HttpSession session, Model model){
        Integer userId = (Integer) session.getAttribute("userId");
        List<StudentCourse>list=studentCourseService.listMyCourse(userId,cname);
        for (StudentCourse studentCourse : list) {
            QueryWrapper<TeacherCourse>qw=new QueryWrapper<>();
            qw.eq("cid", studentCourse.getCid());
            TeacherCourse one = teacherCourseService.getOne(qw);
            if (one==null){
                studentCourse.setTname("暂未分配教师");
            }else {
                studentCourse.setTname(teacherService.getById(one.getTid()).getTname());
            }

        }
        PageInfo<StudentCourse>pageInfo=new PageInfo<>(list);

        model.addAttribute("pageInfo",pageInfo);
        return "student-my-course";
    }

    @RequestMapping("selectCourse/{id}")
    public String selectCourse(@PathVariable Integer id,HttpSession session){
        StudentCourse studentCourse = new StudentCourse();
        Integer userId = (Integer) session.getAttribute("userId");
        QueryWrapper<StudentCourse>qw=new QueryWrapper<>();
        qw.eq("sid",userId);
        qw.eq("cid",id);
        qw.eq("status",0);
        StudentCourse one = studentCourseService.getOne(qw);
        if (one!=null){
            return "redirect:/student/listMyCourse";
        }else {
            studentCourse.setCid(id);
            studentCourse.setSid(userId);
            studentCourseService.save(studentCourse);

            Course byId = courseService.getById(id);
            byId.setNum(byId.getNum()+1);
            courseService.updateById(byId);
            return "redirect:/student/listMyCourse";
        }


    }

    @RequestMapping("tuixuan/{id}")
    public String tuixuan(@PathVariable Integer id){
        StudentCourse byId = studentCourseService.getById(id);
        byId.setStatus(1);
        studentCourseService.updateById(byId);

        Course course = courseService.getById(byId.getCid());
        course.setNum(course.getNum()-1);
        courseService.updateById(course);

        return "redirect:/student/listMyCourse";
    }
}
