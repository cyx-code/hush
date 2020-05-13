package com.blog.hush.controller.admin;

import com.blog.hush.common.utils.FileUtil;
import com.blog.hush.common.utils.QiNiuUtil;
import com.blog.hush.entity.Category;
import com.blog.hush.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    private CategoryService categoryService;
    @Resource
    private QiNiuUtil qiNiuUtil;


    @GetMapping("/admin")
    public String home() {
        return "admin/home";
    }

    @GetMapping("/admin/article/write")
    public String writeArticle(Model model) {
        List<Category> categories = categoryService.list();
        model.addAttribute("categoies", categories);
        return "admin/article/add";
    }

    @PostMapping("/admin/article/upload")
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
}
