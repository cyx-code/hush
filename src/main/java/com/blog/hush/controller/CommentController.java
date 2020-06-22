package com.blog.hush.controller;

import com.blog.hush.common.constants.enums.CommonEnum;
import com.blog.hush.common.utils.R;
import com.blog.hush.entity.Comment;
import com.blog.hush.service.CommentService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/comment")
@Api(value = "CommentController", tags = {"评论数据接口"})
public class CommentController {
    @Resource
    private CommentService commentService;

    @PostMapping
    public R add(@RequestBody Comment comment, HttpServletRequest request) {
        boolean result = commentService.insertOne(comment, request);
        return result ? new R<>(CommonEnum.SYSTEM_ERROR) : new R<>(CommonEnum.COMMON_SUCCESS);
    }
}
