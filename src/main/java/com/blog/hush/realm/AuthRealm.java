package com.blog.hush.realm;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.hush.entity.User;
import com.blog.hush.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthRealm implements Realm {
    @Autowired
    private UserService userService;
    @Override
    public String getName() {
        return "authRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 得到用户名
        String username = (String) token.getPrincipal();
        String password = new String((char []) token.getCredentials());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(username), User::getUsername, username);
        User user = userService.getOne(wrapper);
        if (user == null || !user.getPassword().equals(password)) {
            throw new UnknownAccountException();
        }

        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
