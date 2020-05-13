package com.blog.hush.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:qiniu.properties"})
public class QiNiuUtil {

    @Value("${qiniu.accessKey}")
    private String ak;
    @Value("${qiniu.secretKey}")
    private String sk;
    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.url}")
    private String url;

    public String upload(String path) {
        Configuration configuration = new Configuration(Zone.huanan());
        UploadManager manager = new UploadManager(configuration);
        Auth auth = Auth.create(ak, sk);
        String upToken = auth.uploadToken(bucket);
        String key = null;
        String qiniuUrl = null;
        try {
            Response response = manager.put(path, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            qiniuUrl = url + putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return qiniuUrl;
    }
}
