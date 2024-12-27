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
@RequestMapping("teacher")
public class TeacherController {
    @Value("${location}")
    private String location;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private CourseService courseService;
    @RequestMapping("listTeacher")
    public String listTeacher(@RequestParam(defaultValue = "1",value = "pageNum",required = false)Integer pageNum,
                              @RequestParam(defaultValue = "6",value = "pageSize",required = false)Integer pageSize, Model model, Teacher teacher){
        if (pageNum==null||pageNum.equals("")||pageNum<=0){
            pageNum=1;
        }
        if (pageSize==null||pageSize.equals("")||pageSize<=0){
            pageSize=6;
        }
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Teacher>qw=new QueryWrapper<>();
        if (teacher.getTname()!=null){
            qw.like("tname",teacher.getTname());
        }
        List<Teacher> list = teacherService.list(qw);
        PageInfo<Teacher>pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin-teacher-list";
    }

    @RequestMapping("preSaveTeacher")
    public String preSaveTeacher(Model model){
        List<Major> list = majorService.list(null);
        model.addAttribute("majorList",list);
        return "admin-teacher-save";
    }

    @RequestMapping("saveTeacher")
    public String saveTeacher(Teacher teacher, MultipartFile file){
        if (!file.isEmpty()){
            transfile(teacher,file);
        }
        teacherService.save(teacher);
        return "redirect:/teacher/listTeacher";
    }
    private void transfile(Teacher teacher, MultipartFile file) {
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
        teacher.setTimage(path);
    }

    @RequestMapping("preUpdateTeacher/{id}")
    public String preUpdateTeacher(@PathVariable Integer id, Model model){
        List<Major> list = majorService.list(null);
        model.addAttribute("majorList",list);
        Teacher byId = teacherService.getById(id);
        model.addAttribute("teacher",byId);
        return "admin-teacher-update";
    }

    @RequestMapping("updateTeacher")
    public String updateTeacher(Teacher teacher,MultipartFile file){
        if (!file.isEmpty()){
            transfile(teacher,file);
        }
        teacherService.updateById(teacher);
        return "redirect:/teacher/listTeacher";
    }

    @RequestMapping("delTeacher/{id}")
    public String delTeacher(@PathVariable Integer id){
        boolean b = teacherService.removeById(id);
        return "redirect:/teacher/listTeacher";
    }

    @ResponseBody
    @RequestMapping("batchDeleteTeacher")
    public String batchDeleteStudent(String idList){
        String[] split = StrUtil.split(idList, ",");
        List<Integer>list=new ArrayList<>();

        for (String s : split) {
            if (!s.isEmpty()){
                list.add(Integer.valueOf(s));
            }
        }

        boolean b = teacherService.removeByIds(list);
        if (b){
            return "OK";
        }else {
            return "error";
        }
    }


    @RequestMapping("listMyCourse")
    public String listMyCourse(String cname, HttpSession session, Model model){
        Integer userId = (Integer) session.getAttribute("userId");
        List<TeacherCourse>list=teacherCourseService.listMyCourse(userId,cname);
        PageInfo<TeacherCourse>pageInfo=new PageInfo<>(list);

        model.addAttribute("pageInfo",pageInfo);
        return "teacher-my-course";
    }

    @RequestMapping("/check/{id}")
    public String check(@PathVariable Integer id,Model model){

        QueryWrapper<StudentCourse>qw=new QueryWrapper<>();
        qw.eq("cid",id);
        List<StudentCourse> list = studentCourseService.list(qw);
        List<Student>studentList=new ArrayList<>();
        for (StudentCourse studentCourse : list) {
            Student byId = studentService.getById(studentCourse.getSid());
            studentList.add(byId);
        }
        model.addAttribute("studentList",studentList);
        return "teacher-my-course-student";


    }


    @RequestMapping("preUpload/{cid}")
    public String preUpload(@PathVariable Integer cid,Model model){
        Course byId = courseService.getById(cid);
        model.addAttribute("course",byId);
        return "teacher-upload";
    }

    @RequestMapping("upload")
    public String upload(Course course,MultipartFile file){
        if (!file.isEmpty()){
            transFileBook(course,file);
        }
        courseService.updateById(course);
        return "redirect:/teacher/listMyCourse";
    }

    private void transFileBook(Course course, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        File file1 = new File(location);
        if (!file1.exists()){
            file1.mkdirs();
        }
        File file2 = new File(file1, originalFilename);
        try {
            file.transferTo(file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        course.setCbook(originalFilename);
    }
}
