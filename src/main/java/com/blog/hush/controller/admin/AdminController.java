package com.blog.hush.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String home() {
        return "admin/index";
    }

    @GetMapping("/admin/article/write")
    public String writeArticle() {
        return "admin/article/write";
    }
}
