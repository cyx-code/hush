package com.blog.hush.controller;

import com.blog.hush.common.constants.enums.CommonEnum;
import com.blog.hush.common.utils.R;
import com.blog.hush.entity.Article;
import com.blog.hush.entity.User;
import com.blog.hush.service.ArticleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
