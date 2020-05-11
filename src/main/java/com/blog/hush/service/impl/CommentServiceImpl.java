package com.blog.hush.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.hush.common.constants.CommonConstants;
import com.blog.hush.common.exception.GlobalException;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.common.utils.TreeUtil;
import com.blog.hush.dto.Tree;
import com.blog.hush.entity.Comment;
import com.blog.hush.mapper.CommentMapper;
import com.blog.hush.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> listRecentComments() {
        List<Comment> comments = commentMapper.findAll(CommonConstants.DEFAULT_RELEASE_STATUS, new QueryPage(0, 8));
        return comments;
    }

    @Override
    public Map<String, Object> listComments(QueryPage queryPage, String articleId, int sort) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(articleId), Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getSort, sort);
        queryWrapper.orderByDesc(Comment::getId);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<Tree<Comment>> commentsTree = new ArrayList<>();
        comments.forEach(c -> {
            Tree<Comment> tree = new Tree<>();
            tree.setId(c.getId());
            tree.setAId(c.getArticleId());
            tree.setPId(c.getPId());
            tree.setContent(c.getContent());
            tree.setName(c.getName());
            tree.setTarget(c.getCName());
            tree.setUrl(c.getUrl());
            tree.setDevice(c.getDevice());
            tree.setTime(c.getTime());
            tree.setSort(c.getSort());
            commentsTree.add(tree);
        });
        HashMap<String, Object> map = new HashMap<>();
        try {
            List<Tree<Comment>> treeList = TreeUtil.build(commentsTree);
            if (treeList == null) {
                treeList = new ArrayList<>();
            }
            if (treeList.size() == 0) {
                map.put("rows", new ArrayList<>());
            } else {
//                int start = (queryPage.getPage() - 1)
            }
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
        return map;
    }
}
