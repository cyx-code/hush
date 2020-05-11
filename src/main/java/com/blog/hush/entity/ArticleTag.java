package com.blog.hush.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@TableName("tb_article_tag")
public class ArticleTag implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文章ID
     */
    @TableField("article_id")
    @NotNull
    private Long articleId;

    /**
     * 标签ID
     */
    @TableField("tag_id")
    @NotNull
    private Long tagId;
}