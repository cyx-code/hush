package com.blog.hush.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.entity.Article;
import com.blog.hush.entity.ArticleCategory;
import com.blog.hush.entity.ArticleTag;
import com.blog.hush.entity.Category;
import com.blog.hush.mapper.ArticleCategoryMapper;
import com.blog.hush.mapper.ArticleMapper;
import com.blog.hush.mapper.ArticleTagMapper;
import com.blog.hush.mapper.CategoryMapper;
import com.blog.hush.service.CategoryService;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ArticleCategoryMapper articleCategoryMapper;
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Override
    @Transactional
    public boolean deleteCategory(Long id) {
        try {
            LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Article::getCategory, id);
            queryWrapper.select(Article::getId);
            List<Object> idObjs = articleMapper.selectObjs(queryWrapper);
            LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
            articleWrapper.eq(Article::getCategory, id);
            articleMapper.delete(articleWrapper);
            LambdaQueryWrapper<Category> categoryWrapper = new LambdaQueryWrapper<>();
            categoryWrapper.eq(Category::getId, id);
            categoryMapper.delete(categoryWrapper);
            LambdaQueryWrapper<ArticleCategory> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ArticleCategory::getCategoryId, id);
            articleCategoryMapper.delete(wrapper);
            List<Long> ids = idObjs.stream().map(e -> Long.parseLong(e.toString())).collect(Collectors.toList());
            articleTagMapper.batchDeleteByArticle(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Category> pageCategories(QueryPage queryPage) {
        return categoryMapper.pageCategories(queryPage);
    }
}
