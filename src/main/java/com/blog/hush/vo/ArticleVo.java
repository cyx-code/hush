package com.blog.hush.vo;

import com.blog.hush.entity.Article;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleVo extends Article {
    private String stateDesc;
    private String month;
    private String day;
}
