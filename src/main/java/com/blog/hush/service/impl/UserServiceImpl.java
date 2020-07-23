package com.blog.hush.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.hush.common.constants.enums.CommonEnum;
import com.blog.hush.common.utils.R;
import com.blog.hush.entity.User;
import com.blog.hush.mapper.UserMapper;
import com.blog.hush.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public R modifyPassword(String username, Map<String, String> map) {
        String oldPassword = map.getOrDefault("oldPassword", "");
        String newPassword = map.getOrDefault("newPassword", "");
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(wrapper);
        if (!user.getPassword().equals(oldPassword)) {
            return new R(CommonEnum.PASSWORD_ERROR);
        }
        user.setPassword(newPassword);
        userMapper.updateById(user);
        return new R(CommonEnum.COMMON_SUCCESS);
    }
}
