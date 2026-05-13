package com.wuyue.video.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("video")
public class Video {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;       // 投稿人ID
    private String title;      // 标题
    private String coverUrl;   // 封面链接
    private String videoUrl;   // 视频源链接
    private String description;// 简介
    private Integer viewCount; // 播放量

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}