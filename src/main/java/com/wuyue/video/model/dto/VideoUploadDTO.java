package com.wuyue.video.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// 以 VideoUploadDTO 为例
@Data
@Schema(description = "视频上传信息")
public class VideoUploadDTO {
    @Schema(description = "视频标题", example = "【五月视频】我的第一个Vlog")
    private String title;

    @Schema(description = "视频封面URL")
    private String coverUrl;

    @Schema(description = "视频文件URL")
    private String videoUrl;

    @Schema(description = "视频描述/简介")
    private String description;
}