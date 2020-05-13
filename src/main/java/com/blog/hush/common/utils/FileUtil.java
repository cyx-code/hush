package com.blog.hush.common.utils;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.log.level.Level;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

public class FileUtil {
    private static Log LOG = LogFactory.get();

    /**
     * 上传文件至本地，并返回路径
     * @param file
     * @return
     */
    public static String upload(MultipartFile file) {
        String filePath = null;
        try {
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.indexOf("."), filename.length());
            long time = new Date().getTime();

            String userDir = System.getProperty("user.dir");
            filePath = userDir + File.separator + "src" + File.separator + "upload" + File.separator + String.valueOf(time) + suffix;
            File uploadFile = new File(filePath);
            if (!uploadFile.getParentFile().exists()) {
                uploadFile.getParentFile().mkdirs();
            }
            file.transferTo(uploadFile);
        } catch (Exception e) {
            LOG.log(Level.ERROR, e,"文件上传失败");
        }
        return filePath;
    }
}
