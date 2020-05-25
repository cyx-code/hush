package com.blog.hush.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.hush.entity.Article;
import com.blog.hush.vo.ArticleVo;

import java.util.List;

public interface ArticleService extends IService<Article> {
    List<Article> listRecentArticles();

    IPage<Article> prepareArticles(IPage<Article> page);

    Article findById(Long id);

    boolean insertArticle(Article article);

    List<ArticleVo> listArticleVo(Long start, Long limit);

    boolean modify(Article article);

    boolean delete(Long id);
}
