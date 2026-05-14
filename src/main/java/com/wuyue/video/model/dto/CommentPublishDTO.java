package com.wuyue.video.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "发表评论参数")
public class CommentPublishDTO {
    @Schema(description = "视频ID")
    private Long videoId;

    @Schema(description = "父评论ID(回复某人时填，直接评论视频则不填或传null)")
    private Long parentId;

    @Schema(description = "评论内容")
    private String content;
}