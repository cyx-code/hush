package com.blog.hush.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> findAll(@Param("state") String state, @Param("queryPage") QueryPage queryPage);
}