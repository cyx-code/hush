package com.blog.hush.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.hush.common.utils.R;
import com.blog.hush.entity.User;

import java.util.Map;

public interface UserService extends IService<User> {
    R modifyPassword(String username, Map<String, String> map);
}
