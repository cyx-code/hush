package com.blog.hush.service.impl;

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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
        comment.setDevice(browser.getName() + "||" + operatingSystem.getName());
        int count = commentMapper.insert(comment);
        return count > 0;
    }

    // todo 文章详情页面显示评论数据
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
            map.put("pages", (int) Math.ceil(treeList.size() / queryPage.getLimit()));
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
        return map;
    }

}
