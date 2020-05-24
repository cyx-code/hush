package com.blog.hush.vo;

import com.blog.hush.entity.Article;
import lombok.Data;

@Data
public class ArticleVo extends Article {
    private String stateDesc;
}
