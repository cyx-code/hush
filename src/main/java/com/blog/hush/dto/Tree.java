package com.blog.hush.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论树
 */
@Data
public class Tree<T> {

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 父节点ID
     */
    private Long pId;

    /**
     * 文章ID
     */
    private Long aId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人
     */
    private String name;

    /**
     * 给谁评论
     */
    private String target;

    /**
     * 设备
     */
    private String device;

    /**
     * URL 地址
     */
    private String url;

    /**
     * 评论时间
     */
    private String time;

    /**
     * 评论分类
     */
    private Integer sort;

    /**
     * 所有子级回复、评论列表
     */
    private List<Tree<T>> children = new ArrayList<>();
}
