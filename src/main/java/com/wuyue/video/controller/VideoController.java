package com.wuyue.video.controller;

import com.wuyue.video.common.Result;
import com.wuyue.video.common.utils.UserContext;
import com.wuyue.video.model.dto.VideoUploadDTO;
import com.wuyue.video.model.entity.Video;
import com.wuyue.video.model.vo.VideoDetailVO;
import com.wuyue.video.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "视频管理", description = "视频投稿、展示与搜索核心接口")
@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Operation(summary = "视频投稿", description = "需要 Token，上传标题、简介及链接")
    @PostMapping("/upload")
    public Result<String> upload(@RequestBody VideoUploadDTO dto) {
        Long userId = UserContext.getUserId();
        videoService.uploadVideo(userId, dto);
        return Result.success("视频投稿成功");
    }

    @Operation(summary = "首页推荐", description = "公开接口，随机获取10条视频")
    @GetMapping("/recommend")
    public Result<List<Video>> recommend() {
        return Result.success(videoService.getRandomRecommendations());
    }

    @Operation(summary = "视频详情", description = "公开接口，获取详情并增加播放量")
    @GetMapping("/{id}")
    public Result<VideoDetailVO> getDetail(@PathVariable("id") Long id) {
        return Result.success(videoService.getVideoDetail(id));
    }

    @Operation(summary = "获取我的作品", description = "需要 Token，查看当前登录用户投递的所有视频")
    @GetMapping("/my")
    public Result<List<Video>> getMyVideos() {
        return Result.success(videoService.getMyVideos(UserContext.getUserId()));
    }

    @Operation(summary = "视频搜索", description = "公开接口，根据关键字模糊匹配标题和简介")
    @GetMapping("/search")
    public Result<List<Video>> searchVideos(@RequestParam("keyword") String keyword) {
        return Result.success(videoService.searchVideos(keyword));
    }
}