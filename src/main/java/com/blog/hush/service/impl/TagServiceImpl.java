package com.blog.hush.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.hush.entity.Tag;
import com.blog.hush.mapper.TagMapper;
import com.blog.hush.service.TagService;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
}
