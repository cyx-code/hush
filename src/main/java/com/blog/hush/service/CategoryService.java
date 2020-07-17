package com.blog.hush.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    /**
     * 删除一个栏目，将关联的文章也一并删除
     * @param id
     * @return
     */
    boolean deleteCategory(Long id);
    /**
     * 分页查询栏目信息
     */
    List<Category> pageCategories(QueryPage queryPage);
}
