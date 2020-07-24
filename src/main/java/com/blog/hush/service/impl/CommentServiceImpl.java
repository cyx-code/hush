package com.blog.hush.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.hush.common.constants.CommonConstants;
import com.blog.hush.common.exception.GlobalException;
import com.blog.hush.common.utils.IPUtil;
import com.blog.hush.common.utils.QueryPage;
import com.blog.hush.common.utils.TreeUtil;
import com.blog.hush.dto.Tree;
import com.blog.hush.entity.Comment;
import com.blog.hush.mapper.CommentMapper;
import com.blog.hush.service.CommentService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> listRecentComments() {
        List<Comment> comments = commentMapper.findAll(CommonConstants.DEFAULT_RELEASE_STATUS, new QueryPage(8, 0));
        return comments;
    }

    /**
     * 插入一条评论数据
     * @param comment 评论
     * @param request 网络请求
     * @return
     */
    @Override
    public boolean insertOne(Comment comment, HttpServletRequest request) {
        // 得到真实IP地址
        String ip = IPUtil.getIpAddress(request);
        // 得到ip地址对应的区域
        String address = IPUtil.getIpRegion(ip);
        // 获取到请求头中的User-Agent
        String header = request.getHeader(CommonConstants.USER_AGENT);
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        // 得到用户的使用的浏览器
        Browser browser = userAgent.getBrowser();
        // 得到用户使用的设备信息
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        comment.setIp(ip);
        comment.setAddress(address);
        comment.setTime(new Date());
        comment.setDevice(browser.getName() + " in " + operatingSystem.getName());
        int count = commentMapper.insert(comment);
        return count > 0;
    }

    @Override
    @Transactional
    public boolean deleteComments(Long id) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getPId, id);
        commentMapper.delete(wrapper);
        commentMapper.deleteById(id);
        return false;
    }

    @Override
    public Map<String, Object> listComments(QueryPage queryPage, String articleId, int sort) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(articleId)) {
            queryWrapper.eq(StringUtils.isNotBlank(articleId), Comment::getArticleId, articleId);
        }
        queryWrapper.eq(Comment::getSort, sort);
        queryWrapper.orderByDesc(Comment::getId);
        // 先将所有评论查出来
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<Tree<Comment>> commentsTree = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 封装成评论树
        comments.forEach(c -> {
            Tree<Comment> tree = new Tree<>();
            tree.setId(c.getId());
            tree.setAId(c.getArticleId());
            tree.setPId(c.getPId());
            tree.setContent(c.getContent());
            tree.setName(c.getName());
            tree.setTarget(c.getForWho());
            tree.setDevice(c.getDevice());
            tree.setTime(sdf.format(c.getTime()));
            tree.setSort(c.getSort());
            commentsTree.add(tree);
        });
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 构建评论树
            List<Tree<Comment>> treeList = TreeUtil.build(commentsTree);
            if (treeList == null) {
                treeList = new ArrayList<>();
            }
            if (treeList.size() == 0) {
                map.put("rows", new ArrayList<>());
            } else {
                int start = (queryPage.getPage() - 1) * queryPage.getLimit();
                int end = queryPage.getPage() * queryPage.getLimit();
                if (queryPage.getPage() * queryPage.getLimit() >= treeList.size()) {
                    end = treeList.size();
                }
                map.put("rows", treeList.subList(start, end));
            }
            map.put("count", comments.size());
            map.put("total", treeList.size());
            map.put("current", queryPage.getPage());
            int num = treeList.size() % queryPage.getLimit();
            int pages = num == 0 ? treeList.size() / queryPage.getLimit() : treeList.size() / queryPage.getLimit() + 1;
            map.put("pages", pages);
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
        return map;
    }


}
