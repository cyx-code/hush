package com.blog.hush.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_tag")
public class Tag implements Serializable {
    private static final long serialVersionUID = -8393612034988874752L;
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    private String name;
}