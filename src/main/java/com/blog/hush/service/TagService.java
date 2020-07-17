package com.blog.hush.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.entity.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {
    List<Tag> listTags();

    List<Tag> pageTags(QueryPage queryPage);

    boolean deleteTag(Long id);
}
