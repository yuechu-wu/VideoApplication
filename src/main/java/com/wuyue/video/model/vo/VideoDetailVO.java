package com.wuyue.video.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VideoDetailVO {
    // 视频基础信息
    private Long id;
    private String title;
    private String videoUrl;
    private String coverUrl;
    private String description;
    private Integer viewCount;
    private LocalDateTime createTime;

    // UP主(作者)信息
    private Long authorId;
    private String authorName;
    private String authorAvatar;
}