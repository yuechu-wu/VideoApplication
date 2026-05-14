package com.wuyue.video.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "弹幕数据传输对象")
public class DanmakuDTO {

    @Schema(description = "视频ID", example = "1")
    private Long videoId;

    @Schema(description = "弹幕内容", example = "前方高能！")
    private String content;

    @Schema(description = "视频内时间点(秒)", example = "12.5")
    private Double videoTime;

    @Schema(description = "弹幕颜色", example = "#FF0000")
    private String color;
}