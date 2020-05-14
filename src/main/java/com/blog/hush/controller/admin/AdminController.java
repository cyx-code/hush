package com.blog.hush.controller.admin;

import com.blog.hush.common.utils.FileUtil;
import com.blog.hush.common.utils.QiNiuUtil;
import com.blog.hush.entity.Category;
import com.blog.hush.entity.User;
import com.blog.hush.service.CategoryService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private CategoryService categoryService;
    @Resource
    private QiNiuUtil qiNiuUtil;


    @GetMapping("/")
    public String home() {
        return "admin/home";
    }

    @GetMapping("/article/write")
    public String writeArticle(Model model) {
        List<Category> categories = categoryService.list();
        model.addAttribute("categoies", categories);
        return "admin/article/add";
    }

    @PostMapping("/article/upload")
    @ResponseBody
    public Map upload(MultipartFile file) {
        Map<String, String> result = new HashMap<>();
        try {
            String localPath = FileUtil.upload(file);
            String qiniuPath = qiNiuUtil.upload(localPath);
            result.put("status", "success");
            result.put("path", qiniuPath);
        } catch (Exception e) {
            result.put("status", "error");
            result.put("reason", e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }
    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }
    @PostMapping("/login")
    public String login(User user, Model model) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        HashMap<Object, Object> result = new HashMap<>();
        try {
            subject.login(token);
            subject.isAuthenticated();
            result.put("result", "success");
            result.put("info", "登录成功");
            return "/admin/home";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            result.put("result", "error");
            result.put("info", "用户名或密码不正确");
        }
        model.addAttribute("result", result);
        return "/admin/login";
    }
}
