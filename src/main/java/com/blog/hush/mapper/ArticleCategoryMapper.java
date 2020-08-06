package com.blog.hush.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.hush.entity.ArticleCategory;

import java.util.List;

public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {
    int batchDeleteByArticle(List<Long> ids);
}