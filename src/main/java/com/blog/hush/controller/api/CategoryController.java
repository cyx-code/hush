package com.blog.hush.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.hush.common.constants.enums.CommonEnum;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.common.utils.R;
import com.blog.hush.entity.Category;
import com.blog.hush.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@Api(value = "CategoryController", tags = {"分类接口"})
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public Map list(Integer page, Integer limit) {
        QueryPage queryPage = new QueryPage();
        queryPage.setPage((page - 1) * limit);
        queryPage.setLimit(limit);
        List<Category> categories = categoryService.pageCategories(queryPage);
        int count = categoryService.count();
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "success");
        res.put("count", count);
        res.put("data", categories);
        return res;
    }
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        boolean result = categoryService.deleteCategory(id);
        return result ? new R(CommonEnum.COMMON_SUCCESS) : new R(CommonEnum.SYSTEM_ERROR);
    }
    @PostMapping
    public R add(@RequestBody Category category) {
        boolean result = categoryService.save(category);
        return result ? new R(CommonEnum.COMMON_SUCCESS) : new R(CommonEnum.SYSTEM_ERROR);
    }
    @PutMapping
    public R edit(@RequestBody Category category) {
        boolean result = categoryService.updateById(category);
        return result ? new R(CommonEnum.COMMON_SUCCESS) : new R(CommonEnum.SYSTEM_ERROR);
    }
}
