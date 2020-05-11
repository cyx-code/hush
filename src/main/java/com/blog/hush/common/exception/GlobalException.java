package com.blog.hush.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 全局异常类
 */
public class GlobalException extends RuntimeException {
    @Getter
    @Setter
    private String msg;

    public GlobalException(String message) {
        super(message);
        this.msg = message;
    }
}
