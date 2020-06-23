package com.blog.hush.common.utils;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.log.level.Level;
import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * IP工具类
 */
public class IPUtil {
    private static Log LOGGER = LogFactory.get(IPUtil.class);
    private static final String UNKNOWN = "unknown";
    private static final String PATH = IPUtil.class.getResource("/ip2region/ip2region.db").getPath();

    /**
     * 得到用户的真实ip地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 得到ip地址对应的区域
     * 源码：https://github.com/lionsoul2014/ip2region/blob/master/binding/java/src/main/java/org/lionsoul/ip2region/test/TestSearcher.java
     * @param ip 地址
     * @return
     */
    public static String getIpRegion(String ip) {
        File file = new File(PATH);
        if (!file.exists()) {
            // 获取操作系统的临时目录
            String tmpdir = System.getProperties().getProperty("java.io.tmpdir");
            String path = tmpdir + "ip2region.db";
            file = new File(path);
            try {
                // 复制文件到操作系统临时目录
                FileUtils.copyInputStreamToFile(IPUtil.class.getClassLoader().getResourceAsStream("classpath:ip2region.db"), file);
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, "将IP对应的区域文件复制到临时目录出现异常", e);
            }
        }
        try {
            // 定义配置
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, file.getPath());
            // 使用反射定义方法，使用b树查询
            Method method = searcher.getClass().getMethod("btreeSearch", String.class);
            if ( Util.isIpAddress(ip) == false ) {
                System.out.println("Error: Invalid ip address");
                return "";
            }
            // 执行方法
            DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
            searcher.close();
            return dataBlock.getRegion();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "查询ip地址区域出现异常", e);
        }
        return "";
    }

}
