package com.blog.hush.controller.api;

import com.blog.hush.common.constants.enums.CommonEnum;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.common.utils.R;
import com.blog.hush.controller.BaseController;
import com.blog.hush.entity.Tag;
import com.blog.hush.service.TagService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tag")
@Api(value = "TagController", tags = {"标签数据接口"})
public class TagController extends BaseController {
    @Autowired
    private TagService tagService;
    @GetMapping("/list")
    public R list() {
        List<Tag> tags = tagService.list();
        R<List> r = new R(tags);
        return r;
    }
    @GetMapping
    public Map list(Integer page, Integer limit) {
        QueryPage queryPage = new QueryPage();
        queryPage.setPage((page - 1) * limit);
        queryPage.setLimit(limit);
        List<Tag> tags = tagService.pageTags(queryPage);
        int count = tagService.count();
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "success");
        res.put("count", count);
        res.put("data", tags);
        return res;
    }
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        boolean result = tagService.deleteTag(id);
        return result ? new R(CommonEnum.COMMON_SUCCESS) : new R(CommonEnum.SYSTEM_ERROR);
    }
    @PostMapping
    public R add(@RequestBody Tag tag) {
        boolean result = tagService.save(tag);
        return result ? new R(CommonEnum.COMMON_SUCCESS) : new R(CommonEnum.SYSTEM_ERROR);
    }
    @PutMapping
    public R edit(@RequestBody Tag tag) {
        boolean result = tagService.updateById(tag);
        return result ? new R(CommonEnum.COMMON_SUCCESS) : new R(CommonEnum.SYSTEM_ERROR);
    }
}
