package com.blog.hush.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.hush.common.constants.enums.CommonEnum;
import com.blog.hush.common.utils.R;
import com.blog.hush.entity.Article;
import com.blog.hush.entity.User;
import com.blog.hush.service.ArticleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/article")
@Api(value = "ArticleController", tags = {"文章管理接口"})
public class ArticleController extends BaseController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("add")
    public R add(@RequestBody Article article) {
        return articleService.insertArticle(article) ? new R(CommonEnum.COMMON_SUCCESS) : new R(CommonEnum.SYSTEM_ERROR);
    }
    @GetMapping("list")
    public Map list(Long page, Long limit) {
        new ConcurrentHashMap<>();
        IPage<Article> pageInfo = new Page<>(page, limit);
        pageInfo.setCurrent(page);
        pageInfo.setSize(limit);
        IPage<Article> articleIPage = articleService.page(pageInfo);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "success");
        res.put("count", articleIPage.getTotal());
        res.put("data", articleIPage.getRecords());
        return res;
    }
}
