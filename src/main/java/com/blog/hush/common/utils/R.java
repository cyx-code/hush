package com.blog.hush.common.utils;

import com.blog.hush.common.constants.CommonConstants;
import com.blog.hush.common.constants.enums.CommonEnum;
import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@ToString
public class R<T> implements Serializable {

    @Getter
    @Setter
    private int code = CommonConstants.SUCCESS;

    @Getter
    @Setter
    private Object msg = "success";

    @Getter
    @Setter
    private T data;


    public R() {super();}

    public R(T data) {
        this.data = data;
    }

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public R(CommonEnum enums) {
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public R(CommonEnum enums, T data) {
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
        this.data = data;
    }
    public R(Throwable e) {
        super();
        this.code = CommonConstants.ERROR;
        this.msg = e.getMessage();
    }

    public R(String msg, Throwable e) {
        super();
        this.msg = msg;
        this.code = CommonConstants.ERROR;
    }
}
