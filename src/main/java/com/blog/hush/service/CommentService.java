package com.blog.hush.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.entity.Comment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface CommentService extends IService<Comment> {

    List<Comment> listRecentComments(int count);

    Map<String, Object> listComments(QueryPage queryPage, String id, int commentSortArticle);

    boolean insertOne(Comment comment, HttpServletRequest request);

    boolean deleteComments(Long id);

    List<Comment> listComments(QueryPage queryPage);

    boolean batchDelete(List<Long> ids);
}
