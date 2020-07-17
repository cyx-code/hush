package com.blog.hush.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.hush.entity.Article;
import com.blog.hush.vo.ArticleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {
    Article findById(@Param("id") Long id);

    Long insertAndBackId(@Param("article") Article article);

    List<ArticleVo> listArticleVo(@Param("start") long start, @Param("size") Long size);

    List<ArticleVo> listArchives();

    List<Article> listByTag(@Param("tag") Long tag);
}