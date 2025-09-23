package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.Admin;
import com.example.studentmanagementsystemtest.service.Adminservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    Adminservice adminservice;

    // 添加管理员信息的接口方法
    @PostMapping("/add")
    public ResponseEntity< Integer > add(@RequestBody Admin admin) {
        try {
            // 调用服务层的添加管理员方法
            boolean isSuccess = adminservice.save(admin);
            // 如果添加成功，返回状态码为200
            return ResponseEntity.ok(isSuccess ? 200 : 400);
        } catch (Exception e) {
            logger.error("添加管理员信息出现异常", e);
            // 捕获其他通用异常，同样返回500状态码及一个自定义的通用异常错误标识值（这里返回5000，可根据实际情况调整）
            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 获取管理员信息列表的接口方法
    @GetMapping("/List")
    public ResponseEntity< List< Admin > > getAdminList() {
        try {
            // 调用服务层获取所有管理员信息的方法
            List< Admin > adminList = adminservice.list();
            logger.info("成功获取到 {} 条管理员信息", adminList.size());
            return new ResponseEntity<>(adminList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("获取管理员信息列表出现异常", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 更新管理员信息接口
    @PutMapping("/update")
    public ResponseEntity< Integer > updateAdmin(@RequestBody Admin admin) {
        try {
            boolean isSuccess = adminservice.updateById(admin);
            if (isSuccess) {
                return new ResponseEntity<>(200, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(400, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("更新管理员信息出现异常", e);
            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据ID获取管理员信息的接口方法
    @GetMapping("/{id}")
    public ResponseEntity< Admin > getAdminById(@PathVariable int id) {
        try {
            Admin admin = adminservice.getById(id);
            if (admin != null) {
                return new ResponseEntity<>(admin, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("根据ID查询管理员信息出现异常", e);
            // 捕获其他通用异常，同样返回500状态码
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 删除管理员信息的接口方法，接收ID作为路径参数
    @DeleteMapping("/delete/{id}")
    public ResponseEntity< Integer > deleteAdminById(@PathVariable int id) {
        try {
            // 调用服务层的删除管理员方法
            boolean isSuccess = adminservice.removeById(id);
            return ResponseEntity.ok(isSuccess ? 200 : 400);
        } catch (Exception e) {
            logger.error("删除管理员信息出现异常", e);
            return new ResponseEntity<>(5000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
