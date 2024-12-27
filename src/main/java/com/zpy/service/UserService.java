package com.zpy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zpy.pojo.User;

public interface UserService extends IService<User> {
    boolean login(String username, String password);
}
