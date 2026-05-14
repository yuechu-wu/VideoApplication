package com.wuyue.video.controller;

import com.wuyue.video.common.Result;
import com.wuyue.video.common.utils.UserContext;
import com.wuyue.video.model.dto.DanmakuDTO;
import com.wuyue.video.service.DanmakuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "弹幕管理", description = "视频弹幕的发送与拉取")
@RestController
@RequestMapping("/api/danmaku")
public class DanmakuController {

    @Autowired
    private DanmakuService danmakuService;

    @Operation(summary = "发送弹幕", description = "需要登录(带Token)")
    @PostMapping("/send")
    public Result<String> send(@RequestBody DanmakuDTO dto) {
        Long userId = UserContext.getUserId();
        danmakuService.sendDanmaku(userId, dto);
        return Result.success("发送弹幕成功");
    }

    @Operation(summary = "获取视频弹幕", description = "公开接口，前端播放器初始化时调用")
    @GetMapping("/list")
    public Result<List<DanmakuDTO>> getList(@RequestParam("videoId") Long videoId) {
        List<DanmakuDTO> list = danmakuService.getDanmakusByVideoId(videoId);
        return Result.success(list);
    }
}