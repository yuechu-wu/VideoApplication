package com.wuyue.video.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

@Component
public class AliOssUtil {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    /**
     * 文件上传工具方法
     * @param file 前端传来的文件对象
     * @return 上传成功后的真实网络 URL
     */
    public String upload(MultipartFile file) throws Exception {
        // 1. 获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();

        // 2. 避免文件重名，使用 UUID 重新生成文件名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + extension;

        // 3. 创建 OSSClient 实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 4. 执行上传请求 (参数：Bucket名称, 存储的文件名, 文件的输入流)
            ossClient.putObject(bucketName, newFileName, inputStream);
        } finally {
            // 5. 必须关闭 OSSClient，否则会造成内存泄漏
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        // 6. 拼接出真实的访问路径并返回 (格式：https://bucket名.endpoint/文件名)
        String url = "https://" + bucketName + "." + endpoint + "/" + newFileName;
        return url;
    }
}