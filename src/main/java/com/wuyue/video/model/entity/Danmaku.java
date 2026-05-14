package com.wuyue.video.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("danmaku")
public class Danmaku {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long videoId;     // 所属视频ID
    private Long userId;      // 发送者ID
    private String content;   // 弹幕内容

    // ✨ 注意这里改成和你数据库一致的 videoTime 和 Double 类型 ✨
    private Double videoTime; // 视频内时间点(秒)

    private String color;     // 弹幕颜色

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}