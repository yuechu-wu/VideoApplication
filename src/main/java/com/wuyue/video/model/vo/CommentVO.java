package com.wuyue.video.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "评论展示视图对象")
public class CommentVO {
    private Long id;
    private Long videoId;
    private Long userId;
    private Long parentId;
    private String content;
    private LocalDateTime createTime;

    @Schema(description = "评论者昵称")
    private String nickname;

    @Schema(description = "评论者头像")
    private String avatar;

    @Schema(description = "子评论列表(嵌套)")
    private List<CommentVO> replies;
}