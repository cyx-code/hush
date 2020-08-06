package com.blog.hush.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lionsoul.ip2region.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * IP工具类
 */
public class IPUtil {
    private static final Log LOGGER = LogFactory.getLog(IPUtil.class);

    private static final String UNKNOWN = "unknown";

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
        /**
         * springboot 在打成jar后，不能直接对classpath下直接做文件操作，得通过stream操作文件
         */
        String tmpdir = System.getProperties().getProperty("java.io.tmpdir");
        File file = new File(tmpdir + "ip2region.db");
        try {
            // 获得类路径下的文件
            ClassPathResource resource = new ClassPathResource("ip2region/ip2region.db");
            InputStream stream = resource.getStream();
            FileUtil.writeFromStream(stream, file);
        } catch (Exception e) {
            LOGGER.error("获取文件出现异常", e);
        }
        try {
            if (file != null) {
                // 定义配置
                DbConfig config = new DbConfig();
                DbSearcher searcher = new DbSearcher(config, file.getPath());
                // 使用反射定义方法，使用b树查询
                Method method = searcher.getClass().getMethod("btreeSearch", String.class);
                if (Util.isIpAddress(ip) == false) {
                    System.out.println("Error: Invalid ip address");
                    return "";
                }
                // 执行方法
                DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
                searcher.close();
                return dataBlock.getRegion();
            } else {
                LOGGER.info("文件未加载到");
            }
        } catch (Exception e) {
            LOGGER.error("查询ip地址区域出现异常", e);
        }
        return "";
    }

}
