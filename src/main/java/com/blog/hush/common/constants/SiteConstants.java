package com.blog.hush.common.constants;

/**
 * 博客前台模块常量值
 */
public interface SiteConstants {
    /**
     * 前台默认文章数
     */
    int DEFAULT_PAGE_LIMIT = 9;
    /**
     * 首页前端接受参数
     */
    String INDEX_MODEL = "list";

    /**
     * 最新的文章
     */
    String RECENT_POSTS = "RecentPosts";

    /**
     * 最新的评论
     */
    String RECENT_COMMENTS = "RecentComments";

    /**
     * 文章详情页
     */
    String ARTICLE_MODEL = "article";

    String COMMENTS_MODEL = "comments";

    int COMMENT_SORT_ARTICLE = 0;

    int COMMENT_SORT_ABOUT = 2;

    int DEFAULT_COMMENT_LIMIT = 4;

    /**
     * 搜索页标签颜色
     */
    String[] COLORS = {"bg-red", "bg-yellow", "bg-green", "bg-blue",
            "bg-purple", "bg-white", "bg-orange", "bg-turquoise", "bg-emerald", "bg-peterriver",
            "bg-wetasphalt", "bg-silver"};
}
