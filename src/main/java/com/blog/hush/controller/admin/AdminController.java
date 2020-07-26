package com.blog.hush.controller.admin;

import com.blog.hush.common.constants.enums.CommonEnum;
import com.blog.hush.common.utils.FileUtil;
import com.blog.hush.common.utils.QiNiuUtil;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.common.utils.R;
import com.blog.hush.controller.BaseController;
import com.blog.hush.entity.Article;
import com.blog.hush.entity.Category;
import com.blog.hush.entity.Comment;
import com.blog.hush.entity.User;
import com.blog.hush.service.ArticleService;
import com.blog.hush.service.CategoryService;
import com.blog.hush.service.CommentService;
import com.blog.hush.service.UserService;
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
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;
    @Resource
    private QiNiuUtil qiNiuUtil;

    @GetMapping({"", "/", "index"})
    public String page() {
        return "admin/index";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        int articleCount = articleService.count();
        int commentCount = commentService.count();
        int hits = articleService.sumHits();
        List<Article> articles = articleService.listRecentArticles(5);
        List<Comment> comments = commentService.listComments(new QueryPage(8, 0));
        model.addAttribute("articleCount", articleCount);
        model.addAttribute("commentCount", commentCount);
        model.addAttribute("hits", hits);
        model.addAttribute("articles", articles);
        model.addAttribute("comments", comments);
        return "admin/welcome";
    }

    @GetMapping("/article/add")
    public String addArticle(Model model) {
        List<Category> categories = categoryService.list();
        model.addAttribute("categories", categories);
        return "admin/article-add";
    }

    @GetMapping("/article/manager")
    public String managerArticle(Model model) {
        List<Category> categories = categoryService.list();
        model.addAttribute("categories", categories);
        return "admin/article-manager";
    }

    @GetMapping("/comment/manager")
    public String managerComment() {
        return "admin/comment-manager";
    }

    @GetMapping("/category/manager")
    public String managerCategory() {
        return "admin/category-manager";
    }

    @GetMapping("/tag/manager")
    public String managerTag() { return "admin/tag-manager"; }

    @GetMapping("/password")
    public String password() {
        return "admin/user-password";
    }
    @PostMapping("/modifyPassword")
    @ResponseBody
    public R modifyPassword(@RequestBody Map<String, String> map, HttpSession session) {
        String username = session.getAttribute("username").toString();
        return userService.modifyPassword(username, map);
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
    @ResponseBody
    public R login(@RequestBody User user, HttpSession session) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        boolean flag;
        try {
            subject.login(token);
            flag = true;
            session.setAttribute("username", user.getUsername());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag ?  new R(CommonEnum.COMMON_SUCCESS) : new R(CommonEnum.LOGIN_ERROR);
    }
    @PostMapping("logout")
    @ResponseBody
    public R logout(HttpSession session) {
        SecurityUtils.getSubject().logout();
        session.removeAttribute("username");
        return new R(CommonEnum.COMMON_SUCCESS);
    }
}
