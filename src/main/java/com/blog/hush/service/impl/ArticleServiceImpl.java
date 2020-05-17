package com.blog.hush.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.hush.common.constants.CommonConstants;
import com.blog.hush.entity.*;
import com.blog.hush.mapper.*;
import com.blog.hush.service.ArticleService;
import org.apache.shiro.SecurityUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ArticleCategoryMapper articleCategoryMapper;
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Resource
    private TagMapper tagMapper;

    @Override
    public List<Article> listRecentArticles() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Article::getId);
        wrapper.eq(Article::getState, CommonConstants.DEFAULT_RELEASE_STATUS);
        IPage<Article> page = new Page<>(0, 8);
        IPage<Article> list = articleMapper.selectPage(page, wrapper);
        return list.getRecords();
    }

    @Override
    public IPage<Article> prepareArticles(IPage<Article> page) {

        // lambda查询对象
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 设置查询条件是按照Article的id降序排列
        queryWrapper.orderByDesc(Article::getId);
        // 设置查询条件Article的state是1的数据（文章已发布）
        queryWrapper.eq(Article::getState, CommonConstants.DEFAULT_RELEASE_STATUS);
        IPage<Article> list = articleMapper.selectPage(page, queryWrapper);
        List<Category> categories = categoryMapper.selectList(null);
        Map<Long, String> categoryMap = new HashMap<>(categories.size());
        categories.forEach(category -> {
            categoryMap.put(category.getId(), category.getIcon());
        });
        list.getRecords().forEach(article -> {
            String content = Jsoup.parse(article.getContent()).text();
            if (content.length() > 50) {
                content = content.substring(0, 40) + "...";
            }
            article.setBgIco(categoryMap.get(article.getCategory()));
            article.setContent(content);
            article.setContentMd(null);
            article.setCategory(null);
        });

        return list;
    }

    /**
     * 根据文章id查询相应文章，设置分类
     * @param id
     * @return
     */
    @Override
    public Article findById(Long id) {
        Article article = articleMapper.findById(id);
        ArrayList<Article> articles = new ArrayList<>();
        articles.add(article);
        prepareArticle(articles);
        return article;
    }

    /**
     * 封装标签数据
     * @param articles
     */
    public void prepareArticle(List<Article> articles) {
        if (articles.isEmpty()) {
            return;
        }
        articles.forEach(article ->  {
            List<Tag> tags = tagMapper.listTagsByArticleId(article.getId());
            article.setTags(tags);
        });
    }


    /**
     * 添加一篇文章
     * @param article
     * @return
     */
    @Override
    @Transactional
    public boolean insertArticle(Article article) {
        try {
            Date now = new Date();
            String username = (String) SecurityUtils.getSubject().getPrincipal();
            article.setAuthor("cyx");
            if ("on".equals(article.getState())) {
                article.setPublishTime(now);
            }
            article.setEditTime(now);
            article.setCreateTime(now);
            articleMapper.insertAndBackId(article);
            Long articleId = article.getId();
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setArticleId(articleId);
            articleCategory.setCategoryId(article.getCategory());
            articleCategoryMapper.insert(articleCategory);
            List<ArticleTag> articleTags = new ArrayList<>();
            article.getTags().forEach(tag -> {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tag.getId());
                articleTags.add(articleTag);
            });
            articleTagMapper.batchInsert(articleTags);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
