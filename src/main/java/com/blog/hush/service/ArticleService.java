package com.blog.hush.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.hush.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    List<Article> listRecentArticles();

    IPage<Article> prepareArticles(IPage<Article> page);

    Article findById(Long id);

    boolean insertArticle(Article article);
}
