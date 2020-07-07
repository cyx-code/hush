package com.blog.hush.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.hush.common.constants.enums.CommonEnum;
import com.blog.hush.common.utils.R;
import com.blog.hush.entity.Comment;
import com.blog.hush.service.CommentService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
    @GetMapping
    public Map list(Long page, Long limit) {
        IPage<Comment> pageInfo = new Page<>(page, limit);
        pageInfo.setCurrent(page);
        pageInfo.setSize(limit);
        IPage<Comment> commentPage = commentService.page(pageInfo);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "success");
        res.put("count", commentPage.getTotal());
        res.put("data", commentPage.getRecords());
        return res;
    }
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        boolean result = commentService.removeById(id);
        return result ? new R(CommonEnum.COMMON_SUCCESS) : new R(CommonEnum.SYSTEM_ERROR);
    }
}
