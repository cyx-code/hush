package com.blog.hush.common.utils;

import com.blog.hush.common.constants.CommonConstants;
import com.blog.hush.common.constants.enums.CommonEnum;
import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@ToString
public class ResponseData<T> implements Serializable {

    @Getter
    @Setter
    private int code = CommonConstants.SUCCESS;

    @Getter
    @Setter
    private Object msg = "success";

    @Getter
    @Setter
    private T data;


    public ResponseData() {super();}

    public ResponseData(T data) {
        this.data = data;
    }

    public ResponseData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseData(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseData(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public ResponseData(CommonEnum enums) {
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public ResponseData(Throwable e) {
        super();
        this.code = CommonConstants.ERROR;
        this.msg = e.getMessage();
    }

    public ResponseData(String msg, Throwable e) {
        super();
        this.msg = msg;
        this.code = CommonConstants.ERROR;
    }
}
