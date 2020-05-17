package com.blog.hush.controller;

import com.blog.hush.common.utils.R;
import com.blog.hush.controller.BaseController;
import com.blog.hush.entity.Tag;
import com.blog.hush.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController extends BaseController {
    @Autowired
    private TagService tagService;
    @GetMapping("/list")
    public R list() {
        List<Tag> tags = tagService.list();
        R<List> r = new R(tags);
        return r;
    }
}