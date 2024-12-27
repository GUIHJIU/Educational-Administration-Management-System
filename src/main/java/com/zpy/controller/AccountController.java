package com.zpy.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zpy.pojo.Result;
import com.zpy.pojo.Student;
import com.zpy.pojo.Teacher;
import com.zpy.pojo.User;
import com.zpy.service.StudentService;
import com.zpy.service.TeacherService;
import com.zpy.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
public class AccountController {
    @Value("${location}")
    private String location;
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @RequestMapping("login")
    public String login(String username, String password, Integer role, Model model, HttpSession session){

        if (role==null){
            model.addAttribute("msg","请先选择身份");
            return "index";
        }


        if (role==1){
            boolean b=userService.login(username,password);
            if (b){
                QueryWrapper<User>qw=new QueryWrapper<>();
                qw.eq("username",username);
                User user=userService.getOne(qw);
                session.setAttribute("currentUser",username);
                session.setAttribute("password",password);
                session.setAttribute("userId",user.getId());
                session.setAttribute("image",user.getImage());
                session.setAttribute("phone",user.getPhone());
                session.setAttribute("email",user.getEmail());
                session.setAttribute("role",1);
                return "admin-home";
            }else {
                model.addAttribute("msg","用户名或密码错误");
                return "index";
            }
        }else if (role==2){

            boolean b=teacherService.login(username,password);
            if (b){
                QueryWrapper<Teacher>qw=new QueryWrapper<>();
                qw.eq("tname",username);
                Teacher teacher=teacherService.getOne(qw);
                session.setAttribute("currentUser",username);
                session.setAttribute("password",password);
                session.setAttribute("userId",teacher.getId());
                session.setAttribute("image",teacher.getTimage());
                session.setAttribute("phone",teacher.getPhone());
                session.setAttribute("email",teacher.getEmail());
                session.setAttribute("role",2);
                return "teacher-home";
            }else {
                model.addAttribute("msg","用户名或密码错误");
                return "index";
            }

        }else {

            boolean b=studentService.login(username,password);
            if (b){
                QueryWrapper<Student>qw=new QueryWrapper<>();
                qw.eq("sname",username);
                qw.eq("password", DigestUtil.md5Hex(password));
                Student student=studentService.getOne(qw);
                session.setAttribute("currentUser",username);
                session.setAttribute("password",password);
                session.setAttribute("userId",student.getId());
                session.setAttribute("image",student.getSimage());
                session.setAttribute("role",3);
                return "student-home";
            }else {
                model.addAttribute("msg","用户名或密码错误");
                return "index";
            }

        }
    }

    @RequestMapping("count")
    public String count(){
        return "admin-count";
    }
    @PostMapping("login1")
    @ResponseBody
    public Result login(@RequestBody User user, HttpSession session){

        Result result = new Result();
        boolean login = studentService.login(user.getUsername(), user.getPassword());
        if (login){
            result.setFlag(true);
            session.setAttribute("user",user.getUsername());
            QueryWrapper<Student>qw=new QueryWrapper<>();
            qw.eq("sname",user.getUsername());
            qw.eq("password",user.getPassword());
            Student one = studentService.getOne(qw);
            session.setAttribute("image",one.getSimage());
        }else {
            result.setFlag(false);
            result.setMessage("登录失败");
        }
        return result;
    }

    @GetMapping("/getUsername")
    @ResponseBody
    public User getUsername(HttpSession session) {

        String username = (String) session.getAttribute("user");
        String image = (String) session.getAttribute("image");
        User user = new User();
        user.setUsername(username);
        user.setImage(image);
        return user;
    }

    @RequestMapping("chat")
    public String chat(){
        return "student-chat";
    }

    @RequestMapping("logout")
    public String logout(){
        return "index";
    }

    @RequestMapping("profile")
    public String profile(HttpSession session,Model model){
        Integer role = (Integer) session.getAttribute("role");
//        String currentUser = (String) session.getAttribute("currentUser");
        String password = (String) session.getAttribute("password");
        Integer userId = (Integer) session.getAttribute("userId");
        if (role==1){
            User byId = userService.getById(userId);
            byId.setPassword(password);
            model.addAttribute("user",byId);
            return "admin-profile";
        }else if (role==2){
            Teacher byId = teacherService.getById(userId);
            byId.setPassword(password);
            model.addAttribute("user",byId);
            return "teacher-profile";
        }else {
            Student byId = studentService.getById(userId);
            byId.setPassword(password);
            model.addAttribute("user",byId);
            return "student-profile";
        }
    }


    @RequestMapping("updateAdminProfile")
    public String updateAdminProfile(User user, MultipartFile file){
        if (!file.isEmpty()){
            transfileAdmin(user,file);
        }
        String s = DigestUtil.md5Hex(user.getPassword());
        user.setPassword(s);
        boolean b = userService.updateById(user);
        return "redirect:/profile";
    }

    private void transfileAdmin(User user, MultipartFile file) {
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

        user.setImage(path);
    }

    @RequestMapping("updateTeacherProfile")
    public String updateReaderProfile(Teacher reader,MultipartFile file){
        if (!file.isEmpty()){
            transfileTeacher(reader,file);
        }
        String s = DigestUtil.md5Hex(reader.getPassword());
        reader.setPassword(s);
        boolean b = teacherService.updateById(reader);
        return "redirect:/profile";
    }

    private void transfileTeacher(Teacher reader, MultipartFile file) {
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

        reader.setTimage(path);
    }

    @RequestMapping("updateStudentProfile")
    public String updateStudentProfile(Student user,MultipartFile file){
        if (!file.isEmpty()){
            transfileStudent(user,file);
        }
        String s = DigestUtil.md5Hex(user.getPassword());
        user.setPassword(s);
        boolean b = studentService.updateById(user);
        return "redirect:/profile";
    }

    private void transfileStudent(Student user, MultipartFile file) {
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

        user.setSimage(path);
    }

    @RequestMapping("toRegister")
    public String toRegister(){
        return "register";
    }
    @RequestMapping("toLogin")
    public String toLogin(){
        return "index";
    }

    @RequestMapping("register")
    public String register(Integer role,String userName,String userPwd,String confirmPwd,Model model){
        if (role == null) {
            model.addAttribute("msg", "请选择身份");
            return "register";
        }
        if (!userPwd.equals(confirmPwd)){
            model.addAttribute("msg","两次输入的密码不一致");
            return "register";
        }

        if (role==1){
            QueryWrapper<User>qw=new QueryWrapper<>();
            qw.eq("username",userName);
            User one = userService.getOne(qw);
            if (one!=null){
                model.addAttribute("msg","该用户已存在");
                return "register";
            }else {
                User user = new User();
                user.setUsername(userName);
                user.setPassword(DigestUtil.md5Hex(userPwd));
                boolean save = userService.save(user);
                return "index";
            }

        }else if (role==2){
            QueryWrapper<Teacher>qw=new QueryWrapper<>();
            qw.eq("tname",userName);
            Teacher one = teacherService.getOne(qw);
            if (one!=null){
                model.addAttribute("msg","该用户已存在");
                return "register";
            }else {
                Teacher user = new Teacher();
                user.setTname(userName);
                user.setPassword(DigestUtil.md5Hex(userPwd));
                boolean save = teacherService.save(user);
                return "index";
            }
        }else {
            QueryWrapper<Student>qw=new QueryWrapper<>();
            qw.eq("sname",userName);
            Student one = studentService.getOne(qw);
            if (one!=null){
                model.addAttribute("msg","该用户已存在");
                return "register";
            }else {
                Student user = new Student();
                user.setSname(userName);
                user.setPassword(DigestUtil.md5Hex(userPwd));
                boolean save = studentService.save(user);
                return "index";
            }
        }


    }
}
