package com.blog.hush.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.hush.entity.ArticleTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
    int batchInsert(@Param("articleTags") List<ArticleTag> articleTags);
}