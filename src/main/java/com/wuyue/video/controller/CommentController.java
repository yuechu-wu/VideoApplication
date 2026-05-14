package com.wuyue.video.controller;

import com.wuyue.video.common.Result;
import com.wuyue.video.common.utils.UserContext;
import com.wuyue.video.model.dto.CommentPublishDTO;
import com.wuyue.video.model.vo.CommentVO;
import com.wuyue.video.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "评论管理", description = "视频评论的发布与展示")
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "发布评论", description = "需要登录，支持一级评论和回复")
    @PostMapping("/publish")
    public Result<String> publish(@RequestBody CommentPublishDTO dto) {
        Long userId = UserContext.getUserId();
        commentService.publishComment(userId, dto);
        return Result.success("评论发布成功");
    }

    @Operation(summary = "获取视频评论树", description = "公开接口，展示完整的嵌套评论")
    @GetMapping("/tree")
    public Result<List<CommentVO>> getTree(@RequestParam("videoId") Long videoId) {
        return Result.success(commentService.getCommentTree(videoId));
    }
}