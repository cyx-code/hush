package com.blog.hush.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@TableName("tb_log")
public class Log {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作描述
     */
    private String operation;

    /**
     * 耗时(毫秒)
     */
    private Long time;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 操作参数
     */
    private String params;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    private Date createTime;

    /**
     * 操作地点
     */
    private String location;

}