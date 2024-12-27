package com.zpy.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zpy.pojo.Course;
import com.zpy.pojo.Teacher;
import com.zpy.pojo.TeacherCourse;
import com.zpy.service.CourseService;
import com.zpy.service.TeacherCourseService;
import com.zpy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("teacherCourse")
public class TeacherCourseController {
    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @RequestMapping("listTeacherCourse")
    public String listTeacherCourse(@RequestParam(value = "pageNum",defaultValue = "1",required = false)Integer pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "6",required = false)Integer pageSize, Model model, TeacherCourse teacherCourse){

        if (pageNum==null||pageNum.equals("")||pageNum<=0){
            pageNum=1;
        }
        if (pageSize==null||pageSize.equals("")||pageSize<=0){
            pageSize=6;
        }
        PageHelper.startPage(pageNum,pageSize);
        List<TeacherCourse>list=teacherCourseService.listTeacherCourse(teacherCourse.getId());
        PageInfo<TeacherCourse>pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin-teacher-course-list";
    }

    @RequestMapping("preSaveTeacherCourse")
    public String preSaveTeacherCourse(Model model){
        List<Course> list = courseService.list(null);
        List<Teacher> list1 = teacherService.list(null);
        model.addAttribute("teacherList",list1);
        model.addAttribute("courseList",list);
        return "admin-teacher-course-save";
    }

    @RequestMapping("saveTeacherCourse")
    public String saveTeacherCourse(Integer tid, Integer cid, Model model, HttpSession session){

        QueryWrapper<TeacherCourse> qw=new QueryWrapper<>();
        qw.eq("tid",tid);
        qw.eq("cid",cid);
        TeacherCourse one = teacherCourseService.getOne(qw);
        if (one!=null){
            model.addAttribute("msg","该教师已分配该课程");
            List<Teacher> teacherList = teacherService.list(null);
            List<Course> courseList = courseService.list(null);
            model.addAttribute("teacherList",teacherList);
            model.addAttribute("courseList",courseList);
//            model.addAttribute("msg","该教师已分配该课程");
            return "admin-teacher-course-save";
        }
        Teacher teacher = teacherService.getById(tid);
        Course course = courseService.getById(cid);
        if (!teacher.getMajor().equals(course.getMajor())){
            model.addAttribute("msg","该教师和该课程不属于同一专业");
            List<Teacher> teacherList = teacherService.list(null);
            List<Course> courseList = courseService.list(null);
            model.addAttribute("teacherList",teacherList);
            model.addAttribute("courseList",courseList);
//            model.addAttribute("msg","该教师和该课程不属于同一专业");
            return "admin-teacher-course-save";
        }
        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setCid(cid);
        teacherCourse.setTid(tid);
        teacherCourseService.save(teacherCourse);
        return "redirect:/teacherCourse/listTeacherCourse";
    }


    @RequestMapping("preUpdateTeacherCourse/{id}")
    public String preUpdateTeacherCourse(@PathVariable Integer id, Model model, HttpSession session){
        session.setAttribute("teacherCourseId",id);
        TeacherCourse teacherCourse = teacherCourseService.getById(id);
        List<Course> courseList = courseService.list(null);
        List<Teacher> teacherList = teacherService.list(null);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("teacherCourse",teacherCourse);
        return "admin-teacher-course-update";
    }

    @RequestMapping("updateTeacherCourse")
    public String preUpdateTeacherCourse(Integer tid,Integer cid, Model model, HttpSession session){
        Integer teacherCourseId = (Integer) session.getAttribute("teacherCourseId");
        Teacher teacher = teacherService.getById(tid);
        Course course = courseService.getById(cid);
        if (!teacher.getMajor().equals(course.getMajor())){
            model.addAttribute("msg","该教师和该课程不属于同一专业");
            List<Teacher> teacherList = teacherService.list(null);
            List<Course> courseList = courseService.list(null);
            model.addAttribute("teacherList",teacherList);
            model.addAttribute("courseList",courseList);
//            model.addAttribute("msg","该教师和该课程不属于同一专业");
            return "admin-teacher-course-update";
        }
        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setId(teacherCourseId);
        teacherCourse.setTid(tid);
        teacherCourse.setCid(cid);
        boolean b = teacherCourseService.updateById(teacherCourse);
        return "redirect:/teacherCourse/listTeacherCourse";
    }

    @RequestMapping("delTeacherCourse/{id}")
    public String delTeacherCourse(@PathVariable Integer id){
        boolean b = teacherService.removeById(id);
        return "redirect:/teacherCourse/listTeacherCourse";
    }


    @ResponseBody
    @RequestMapping("batchDeleteTeacherCourse")
    public String batchDeleteStudent(String idList){
        String[] split = StrUtil.split(idList, ",");
        List<Integer>list=new ArrayList<>();

        for (String s : split) {
            if (!s.isEmpty()){
                list.add(Integer.valueOf(s));
            }
        }

        boolean b = teacherCourseService.removeByIds(list);
        if (b){
            return "OK";
        }else {
            return "error";
        }
    }
}
