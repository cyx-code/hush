package com.blog.hush.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.entity.Article;
import com.blog.hush.entity.ArticleTag;
import com.blog.hush.entity.Tag;
import com.blog.hush.mapper.ArticleMapper;
import com.blog.hush.mapper.ArticleTagMapper;
import com.blog.hush.mapper.TagMapper;
import com.blog.hush.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Resource
    private TagMapper tagMapper;
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Override
    public List<Tag> listTags() {

        return tagMapper.listTags();
    }

    @Override
    public List<Tag> pageTags(QueryPage queryPage) {
        return tagMapper.pageTags(queryPage);
    }

    @Override
    public boolean deleteTag(Long id) {
        try {
            LambdaQueryWrapper<Tag> tagWrapper = new LambdaQueryWrapper<>();
            tagWrapper.eq(Tag::getId, id);
            tagMapper.delete(tagWrapper);
            LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ArticleTag::getTagId, id);
            articleTagMapper.delete(wrapper);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
