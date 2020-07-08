package com.blog.hush.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class Archive {
    private String month;
    private List<ArticleVo> articles;

}
