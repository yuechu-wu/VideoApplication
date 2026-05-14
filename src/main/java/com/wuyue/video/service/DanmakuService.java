package com.wuyue.video.service;

import com.wuyue.video.model.dto.DanmakuDTO;
import java.util.List;

public interface DanmakuService {
    // 发送弹幕
    void sendDanmaku(Long userId, DanmakuDTO dto);

    // 获取某个视频的所有弹幕
    List<DanmakuDTO> getDanmakusByVideoId(Long videoId);
}