package com.blog.hush.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.hush.entity.Article;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper extends BaseMapper<Article> {
    Article findById(@Param("id") Long id);
}