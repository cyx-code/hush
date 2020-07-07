package com.blog.hush.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_comment")
public class Comment implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父级ID，给哪个留言进行回复
     */
    @TableField("p_id")
    private Long pId;

    /**
     * 子级ID，给哪个留言下的回复进行评论
     */
    @TableField("c_id")
    private Long cId;

    /**
     * 文章ID
     */
    @TableField("article_id")
    private Long articleId;

    /**
     * 文章标题
     */
    @TableField("article_title")
    private String articleTitle;

    /**
     * 昵称
     */
    private String name;

    /**
     * 给谁留言
     */
    @TableField("c_name")
    private String forWho;

    /**
     * 留言时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private Date time;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 分类：0:默认，文章详情页，1:友链页，2:关于页
     */
    private Integer sort;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 设备
     */
    private String device;

    /**
     * 地址
     */
    private String address;

    /**
     * 留言内容
     */
    private String content;
}