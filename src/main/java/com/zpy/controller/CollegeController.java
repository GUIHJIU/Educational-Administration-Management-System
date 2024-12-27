package com.zpy.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zpy.pojo.College;
import com.zpy.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("college")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @RequestMapping("listCollege")
    public String listCollege(@RequestParam(value = "pageNum",defaultValue = "1",required = false)Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "6",required = false)Integer pageSize, College college, Model model){
        if (pageNum==null||pageNum.equals("")||pageNum<=0){
            pageNum=1;
        }
        if (pageSize==null||pageSize.equals("")||pageSize<=0){
            pageSize=6;
        }
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<College>qw=new QueryWrapper<>();
        if (college.getCname()!=null){
            qw.like("cname",college.getCname());
        }
        List<College> list = collegeService.list(qw);
        PageInfo<College>pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin-college-list";
    }

    @RequestMapping("preSaveCollege")
    public String preSaveCollege(){
        return "admin-college-save";
    }

    @RequestMapping("saveCollege")
    public String saveCollege(College college){
        collegeService.save(college);
        return "redirect:/college/listCollege";
    }

    @RequestMapping("preUpdateCollege/{id}")
    public String preUpdateCollege(@PathVariable Integer id,Model model){
        College byId = collegeService.getById(id);
        model.addAttribute("college",byId);
        return "admin-college-update";
    }

    @RequestMapping("updateCollege")
    public String updateCollege(College college){
        collegeService.updateById(college);
        return "redirect:/college/listCollege";
    }

    @RequestMapping("delCollege/{id}")
    public String delCollege(@PathVariable Integer id){
        boolean b = collegeService.removeById(id);
        return "redirect:/college/listCollege";
    }

    @ResponseBody
    @RequestMapping("batchDeleteCollege")
    public String batchDeleteStudent(String idList){
        String[] split = StrUtil.split(idList, ",");
        List<Integer>list=new ArrayList<>();

        for (String s : split) {
            if (!s.isEmpty()){
                list.add(Integer.valueOf(s));
            }
        }

        boolean b = collegeService.removeByIds(list);
        if (b){
            return "OK";
        }else {
            return "error";
        }
    }
}
