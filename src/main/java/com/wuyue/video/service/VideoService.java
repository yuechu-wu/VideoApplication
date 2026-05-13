package com.wuyue.video.service;

import com.wuyue.video.model.dto.VideoUploadDTO;
import com.wuyue.video.model.entity.Video;
import com.wuyue.video.model.vo.VideoDetailVO;

import java.util.List;

public interface VideoService {
    void uploadVideo(Long userId, VideoUploadDTO dto);
    List<Video> getRandomRecommendations();
    // 获取我的作品
    List<Video> getMyVideos(Long userId);

    // 搜索视频
    List<Video> searchVideos(String keyword);

    // 获取视频详情（包含增加播放量）
    VideoDetailVO getVideoDetail(Long videoId);
}