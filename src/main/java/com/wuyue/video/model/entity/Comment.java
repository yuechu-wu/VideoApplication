package com.wuyue.video.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long videoId;
    private Long userId;
    private Long parentId; // 父评论ID，如果是一级评论则为 null
    private String content;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}