package com.zpy.controller;

import com.zpy.pojo.CountNumber;
import com.zpy.pojo.MainMenu;
import com.zpy.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/main")
public class MainMenuController {

    @Autowired
    private StudentCourseService studentCourseService;
    @RequestMapping("/mainMenu")
    public List<MainMenu> mainMenu(){

        List<CountNumber>list=studentCourseService.queryAll();
        List<MainMenu>list1=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setType(list.get(i).getName());
            mainMenu.setMount(Integer.valueOf(list.get(i).getCount()));
            list1.add(mainMenu);
        }

        return list1;
    }
}
