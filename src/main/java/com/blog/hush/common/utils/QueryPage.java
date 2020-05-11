package com.blog.hush.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryPage {
    /**
     * 每页多少条
     */
    private int limit;
    /**
     * 当前页
     */
    private int page;
}
