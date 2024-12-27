package com.zpy.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zpy.pojo.Course;
import com.zpy.pojo.Student;
import com.zpy.pojo.StudentCourse;
import com.zpy.pojo.TeacherCourse;
import com.zpy.service.CourseService;
import com.zpy.service.StudentCourseService;
import com.zpy.service.StudentService;
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
@RequestMapping("studentCourse")
public class StudentCourseController {

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private StudentService  studentService;
    @Autowired
    private CourseService courseService;
    @RequestMapping("listStudentCourse")
    public String listTeacherCourse(@RequestParam(value = "pageNum",defaultValue = "1",required = false)Integer pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "6",required = false)Integer pageSize, Model model, StudentCourse studentCourse){

        if (pageNum==null||pageNum.equals("")||pageNum<=0){
            pageNum=1;
        }
        if (pageSize==null||pageSize.equals("")||pageSize<=0){
            pageSize=6;
        }
        PageHelper.startPage(pageNum,pageSize);
        List<StudentCourse> list=studentCourseService.listStudentCourse(studentCourse.getId());
        PageInfo<StudentCourse>pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin-select-course-list";
    }



    @RequestMapping("preSaveStudentCourse")
    public String preSaveTeacherCourse(Model model){
        List<Student> teacherList = studentService.list(null);
        List<Course> courseList = courseService.list(null);
        model.addAttribute("studentList",teacherList);
        model.addAttribute("courseList",courseList);
        return "admin-select-course-save";
    }



    @RequestMapping("saveStudentCourse")
    public String saveTeacherCourse(Integer sid, Integer cid, Model model, HttpSession session){

        QueryWrapper<StudentCourse> qw=new QueryWrapper<>();
        qw.eq("sid",sid);
        qw.eq("cid",cid);
        StudentCourse one = studentCourseService.getOne(qw);
        if (one!=null){
            model.addAttribute("msg","该学生已选该课程");
            List<Student> teacherList = studentService.list(null);
            List<Course> courseList = courseService.list(null);
            model.addAttribute("studentList",teacherList);
            model.addAttribute("courseList",courseList);
//            model.addAttribute("msg","该教师已分配该课程");
            return "admin-select-course-save";
        }
        Student student = studentService.getById(sid);
        Course course = courseService.getById(cid);

        if (course.getNum()>=course.getStock()){
            model.addAttribute("msg","该课程已满员");
            List<Student> teacherList = studentService.list(null);
            List<Course> courseList = courseService.list(null);
            model.addAttribute("studentList",teacherList);
            model.addAttribute("courseList",courseList);
//            model.addAttribute("msg","该教师已分配该课程");
            return "admin-select-course-save";
        }


        course.setNum(course.getNum()+1);
        courseService.updateById(course);

        StudentCourse teacherCourse = new StudentCourse();
        teacherCourse.setCid(cid);
        teacherCourse.setSid(sid);
        studentCourseService.save(teacherCourse);
        return "redirect:/studentCourse/listStudentCourse";
    }


    @RequestMapping("preUpdateStudentCourse/{id}")
    public String preUpdateTeacherCourse(@PathVariable Integer id, Model model, HttpSession session){
        session.setAttribute("studentCourseId",id);

        StudentCourse teacherCourse = studentCourseService.getById(id);
        List<Course> courseList = courseService.list(null);
        List<Student> teacherList = studentService.list(null);
        model.addAttribute("studentList",teacherList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("studentCourse",teacherCourse);
        return "admin-select-course-update";
    }

    @RequestMapping("updateStudentCourse")
    public String preUpdateTeacherCourse(Integer sid,Integer cid, Model model, HttpSession session){
        Integer studentCourseId = (Integer) session.getAttribute("studentCourseId");
        QueryWrapper<StudentCourse> qw=new QueryWrapper<>();
        qw.eq("sid",sid);
        qw.eq("cid",cid);
        StudentCourse one = studentCourseService.getOne(qw);
        if (one!=null){
            model.addAttribute("msg","该学生已选该课程");
            StudentCourse byId = studentCourseService.getById(studentCourseId);
            List<Course> courseList = courseService.list(null);
            List<Student> teacherList = studentService.list(null);
            model.addAttribute("studentList",teacherList);
            model.addAttribute("courseList",courseList);
            model.addAttribute("studentCourse",byId);
            return "admin-select-course-update";
        }

        Student student = studentService.getById(sid);
        Course course = courseService.getById(cid);
        if (student.getMajor()!=null){
            if (!student.getMajor().equals(course.getMajor())){
                model.addAttribute("msg","该学生和该课程不属于同一专业");

                StudentCourse byId = studentCourseService.getById(studentCourseId);
                List<Course> courseList = courseService.list(null);
                List<Student> teacherList = studentService.list(null);
                model.addAttribute("studentList",teacherList);
                model.addAttribute("courseList",courseList);
                model.addAttribute("studentCourse",byId);
                return "admin-select-course-update";

            }
        }
        StudentCourse teacherCourse = new StudentCourse();
        teacherCourse.setId(studentCourseId);
        teacherCourse.setSid(sid);
        teacherCourse.setCid(cid);
        boolean b = studentCourseService.updateById(teacherCourse);
        return "redirect:/studentCourse/listStudentCourse";
    }

    @RequestMapping("delStudentCourse/{id}")
    public String delStudentCourse(@PathVariable Integer id){
        boolean b = studentCourseService.removeById(id);
        return "redirect:/studentCourse/listStudentCourse";
    }

    @ResponseBody
    @RequestMapping("batchDeleteStudentCourse")
    public String batchDeleteStudent(String idList){
        String[] split = StrUtil.split(idList, ",");
        List<Integer>list=new ArrayList<>();

        for (String s : split) {
            if (!s.isEmpty()){
                list.add(Integer.valueOf(s));
            }
        }

        boolean b = studentCourseService.removeByIds(list);
        if (b){
            return "OK";
        }else {
            return "error";
        }
    }
}
