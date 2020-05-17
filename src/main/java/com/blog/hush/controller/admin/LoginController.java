package com.blog.hush.controller.admin;

import com.blog.hush.common.utils.R;
import com.blog.hush.controller.BaseController;
import io.swagger.annotations.Api;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "LoginController", tags = {"登录接口"})
public class LoginController extends BaseController {

    @PostMapping("/login")
    public R login(@RequestParam("username") String username,
                   @RequestParam("password") String password) {
        Subject subject = getSubject();
        return null;
    }
}
