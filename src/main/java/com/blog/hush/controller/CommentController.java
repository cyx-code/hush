package com.blog.hush.controller;

import com.blog.hush.common.utils.R;
import com.blog.hush.entity.Comment;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
@Api(value = "CommentController", tags = {"评论管理接口"})
public class CommentController {
    @PostMapping("/add")
    public R add(@RequestBody Comment comment) {
        System.out.println(comment);
        return new R();
    }
}
