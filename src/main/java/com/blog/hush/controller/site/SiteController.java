package com.blog.hush.controller.site;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.hush.common.constants.CommonConstants;
import com.blog.hush.common.constants.SiteConstants;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.controller.BaseController;
import com.blog.hush.entity.Article;
import com.blog.hush.entity.Category;
import com.blog.hush.entity.Comment;
import com.blog.hush.entity.Tag;
import com.blog.hush.service.ArticleService;
import com.blog.hush.service.CategoryService;
import com.blog.hush.service.CommentService;
import com.blog.hush.service.TagService;
import com.blog.hush.vo.Archive;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 博客前台控制层
 */
@Controller
public class SiteController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

    ExecutorService threadPool = new ThreadPoolExecutor(2,
            2, 3,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    /**
     * 初始化首页中的recent数据
     * @param model
     */
    private void initModel(Model model) {
        List<Article> articles = articleService.listRecentArticles(8);
        articles.forEach(article -> {
            article.setContent(null);
            article.setContentMd(null);
        });
        model.addAttribute(SiteConstants.RECENT_POSTS, articles);
        List<Comment> comments = commentService.listRecentComments(8);
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
            return "error/500";
        }
        try {
            if (StringUtils.isBlank(page)) {
                page = "1";
            }
            QueryPage queryPage = new QueryPage(SiteConstants.DEFAULT_COMMENT_LIMIT, Integer.parseInt(page));
            Article article = articleService.findById(Long.valueOf(id));
            // 如果文章数据是null或为发布状态则跳转至500
            if (article == null || article.getState().equals(CommonConstants.DEFAULT_DRAFT_STATUS)) {
                return "redirect:/error/500";
            }
            model.addAttribute(SiteConstants.ARTICLE_MODEL, article);
            Map comments = commentService.listComments(queryPage, id, SiteConstants.COMMENT_SORT_ARTICLE);
            model.addAttribute(SiteConstants.COMMENTS_MODEL, comments);
            initModel(model);
            threadPool.execute(() -> {
                LambdaUpdateWrapper<Article> wrapper = new LambdaUpdateWrapper<>();
                wrapper.eq(Article::getId, article.getId()).set(Article::getHits, article.getHits() + 1);
                articleService.update(wrapper);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/500";
        }
        return "site/page/article";
    }
    @GetMapping("/archives")
    public String archives(Model model) {
        initModel(model);
        List<Archive> archives = articleService.listGroupByMonth();
        model.addAttribute("archives", archives);
        return "site/page/archives";
    }

    @GetMapping("/category/{id}")
    public String category(@PathVariable Long id,
                           @RequestParam(name = "page", required = false) String page, Model model) {
        initModel(model);
        if (StrUtil.isBlank(page)) {
            page = "1";
        }
        List<Article> articleVos = articleService.listByCategory(id, page);
        Category category = categoryService.getById(id);
        model.addAttribute("category", category);
        model.addAttribute("articles", articleVos);
        return "site/page/category";
    }
    @GetMapping("/tag/{id}")
    public String tag(@PathVariable Long id,
                           @RequestParam(name = "page", required = false) String page, Model model) {
        initModel(model);
        if (StrUtil.isBlank(page)) {
            page = "1";
        }
        List<Article> articles = articleService.listByTag(id, page);
        Tag tag = tagService.getById(id);
        model.addAttribute("tag", tag);
        model.addAttribute("articles", articles);
        return "site/page/tag";
    }
    @GetMapping("/search")
    public String toSearch(Model model) {
        initModel(model);
        Random random = new Random();
        List<Tag> tags = tagService.listTags();
        tags.forEach(tag -> {
            int i = random.nextInt(SiteConstants.COLORS.length);
            tag.setColor(SiteConstants.COLORS[i]);
        });
        model.addAttribute("tags", tags);
        model.addAttribute("color", SiteConstants.COLORS[random.nextInt(SiteConstants.COLORS.length)]);
        return "site/page/search";
    }
    @PostMapping("/search")
    public String search(String condition, Model model) {
        model.addAttribute("condition", condition);
        List<Article> articles = articleService.listByCondition(condition);
        model.addAttribute("articles", articles);
        return "site/page/search_page";
    }
    @GetMapping("/about")
    public String about(Model model,
                        @RequestParam(name = "page", required = false) String page) {
        initModel(model);
        int pageNum = StrUtil.isBlank(page) ? 1 : Integer.parseInt(page);
        QueryPage queryPage = new QueryPage(SiteConstants.DEFAULT_COMMENT_LIMIT, pageNum);
        Map<String, Object> comments = commentService.listComments(queryPage, null, SiteConstants.COMMENT_SORT_ABOUT);
        model.addAttribute("comments", comments);
        return "site/page/about";
    }
}
