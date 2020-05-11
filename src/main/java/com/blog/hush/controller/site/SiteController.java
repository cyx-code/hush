package com.blog.hush.controller.site;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.hush.common.constants.CommonConstants;
import com.blog.hush.common.constants.SiteConstants;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.controller.BaseController;
import com.blog.hush.entity.Article;
import com.blog.hush.entity.Comment;
import com.blog.hush.service.ArticleService;
import com.blog.hush.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 博客前台控制层
 */
@Controller
public class SiteController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/error/500")
    public String error() {
        return "error/500";
    }

    /**
     * 初始化首页中的recent数据
     * @param model
     */
    private void initModel(Model model) {
        List<Article> articles = articleService.listRecentArticles();
        articles.forEach(article -> {
            article.setContent(null);
            article.setContentMd(null);
        });
        model.addAttribute(SiteConstants.RECENT_POSTS, articles);
        List<Comment> comments = commentService.listRecentComments();
        model.addAttribute(SiteConstants.RECENT_COMMENTS, comments);
    }

    @RequestMapping({"", "/", "/page/{p}"})
    public String index(@PathVariable(required = false) String p, Model model) {
        try {
            if (StringUtils.isBlank(p)) {
                p = "1";
            }
            // 创建分页对象
            IPage<Article> page = new Page<>(Integer.parseInt(p), SiteConstants.DEFAULT_PAGE_LIMIT);
            IPage<Article> list = articleService.prepareArticles(page);
            Map<String, Object> data = getData(page);
            data.put("current", list.getCurrent());
            data.put("pages", list.getPages());
            model.addAttribute(SiteConstants.INDEX_MODEL, data);
            initModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:error/500";
        }
        return "site/index";
    }

    @RequestMapping("/article/{id}")
    public String article(@PathVariable String id,
                          @RequestParam(name = "page", required = false) String page, Model model) {
        if (id == null) {
            return this.error();
        }
        try {
            if (StringUtils.isBlank(page)) {
                page = "1";
            }
            QueryPage queryPage = new QueryPage(Integer.parseInt(page), SiteConstants.DEFAULT_PAGE_LIMIT);
            Article article = articleService.findById(Long.valueOf(id));
            // 如果文章数据是null或为发布状态则跳转至500
            if (article == null || article.getState().equals(CommonConstants.DEFAULT_DRAFT_STATUS)) {
                return "redirect:/error/500";
            }
            model.addAttribute(SiteConstants.ARTICLE_MODEL, article);
//            Map comments = commentService.listComments(queryPage, id, SiteConstants.COMMENT_SORT_ARTICLE);
        } catch (Exception e) {
            return "redirect:/error/500";
        }
        return "site/page/article";
    }
}
