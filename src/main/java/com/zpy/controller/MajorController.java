package com.zpy.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zpy.pojo.College;
import com.zpy.pojo.Major;
import com.zpy.service.CollegeService;
import com.zpy.service.MajorService;
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
@RequestMapping("major")
public class MajorController {
    @Autowired
    private MajorService majorService;
    @Autowired
    private CollegeService  collegeService;
    @RequestMapping("listMajor")
    public String listMajor(@RequestParam(value = "pageNum",defaultValue = "1",required = false)Integer pageNum,
                            @RequestParam(value = "pageSize",defaultValue = "6",required = false)Integer pageSize,
                            Model model, Major major){
        if (pageNum==null||pageNum.equals("")||pageNum<=0){
            pageNum=1;
        }
        if (pageSize==null||pageSize.equals("")||pageSize<=0){
            pageSize=6;
        }
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Major>qw=new QueryWrapper<>();
        if (major.getMname()!=null){
            qw.like("mname",major.getMname());
        }
        if (major.getCollege()!=null){
            qw.like("college",major.getCollege());
        }
        List<Major> list = majorService.list(qw);
        PageInfo<Major>pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        List<College> list1 = collegeService.list(null);
        model.addAttribute("collegeList",list1);
        return "admin-major-list";
    }
    @RequestMapping("preSaveMajor")
    public String preSaveMajor(Model model){
        List<College> list1 = collegeService.list(null);
        model.addAttribute("collegeList",list1);
        return "admin-major-save";
    }

    @RequestMapping("saveMajor")
    public String saveMajor(Major major){
        majorService.save(major);
        return "redirect:/major/listMajor";
    }

    @RequestMapping("preUpdateMajor/{id}")
    public String preUpdateMajor(@PathVariable Integer id,Model model){
        Major byId = majorService.getById(id);
        model.addAttribute("major",byId);
        List<College> list1 = collegeService.list(null);
        model.addAttribute("collegeList",list1);
        return "admin-major-update";
    }

    @RequestMapping("updateMajor")
    public String updateMajor(Major major){
        majorService.updateById(major);
        return "redirect:/major/listMajor";
    }

    @RequestMapping("delMajor/{id}")
    public String delMajor(@PathVariable Integer id){
        boolean b = majorService.removeById(id);
        return "redirect:/major/listMajor";
    }

    @ResponseBody
    @RequestMapping("batchDeleteMajor")
    public String batchDeleteStudent(String idList){
        String[] split = StrUtil.split(idList, ",");
        List<Integer>list=new ArrayList<>();

        for (String s : split) {
            if (!s.isEmpty()){
                list.add(Integer.valueOf(s));
            }
        }

        boolean b = majorService.removeByIds(list);
        if (b){
            return "OK";
        }else {
            return "error";
        }
    }
}
