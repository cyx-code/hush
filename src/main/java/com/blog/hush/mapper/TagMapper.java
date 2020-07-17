package com.blog.hush.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> listTagsByArticleId(@Param("id") Long id);
    List<Tag> listTags();

    List<Tag> pageTags(QueryPage queryPage);
}