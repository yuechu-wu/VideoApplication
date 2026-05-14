package com.wuyue.video.controller;

import com.wuyue.video.common.Result;
import com.wuyue.video.common.utils.AliOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件管理", description = "通用文件上传到阿里云 OSS")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @Operation(summary = "上传文件", description = "支持图片、视频等各类文件上传，返回 OSS 真实链接")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        try {
            String url = aliOssUtil.upload(file);
            return Result.success(url);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("文件上传失败");
        }
    }
}