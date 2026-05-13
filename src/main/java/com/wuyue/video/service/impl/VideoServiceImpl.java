package com.wuyue.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wuyue.video.mapper.UserMapper;
import com.wuyue.video.mapper.VideoMapper;
import com.wuyue.video.model.dto.VideoUploadDTO;
import com.wuyue.video.model.entity.User;
import com.wuyue.video.model.entity.Video;
import com.wuyue.video.model.vo.VideoDetailVO;
import com.wuyue.video.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UserMapper userMapper; // 注入 UserMapper 用来查作者信息

    @Override
    public void uploadVideo(Long userId, VideoUploadDTO dto) {
        Video video = new Video();
        video.setUserId(userId);
        video.setTitle(dto.getTitle());
        video.setVideoUrl(dto.getVideoUrl());
        video.setDescription(dto.getDescription());
        video.setViewCount(0); // 新视频初始播放量为0

        // 如果没有上传封面，提供一张默认的卡通动态感封面图
        if (dto.getCoverUrl() == null || dto.getCoverUrl().isBlank()) {
            video.setCoverUrl("https://example.com/default-cartoon-cover.jpg");
        } else {
            video.setCoverUrl(dto.getCoverUrl());
        }

        videoMapper.insert(video);
    }

    @Override
    public List<Video> getRandomRecommendations() {
        // 利用 MyBatis-Plus 执行随机查询获取 10 条视频
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("ORDER BY RAND() LIMIT 10");
        return videoMapper.selectList(queryWrapper);
    }

    @Override
    public List<Video> getMyVideos(Long userId) {
        // 查询该用户的所有视频，并按发布时间倒序排列 (最新的在最前面)
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).orderByDesc("create_time");
        return videoMapper.selectList(queryWrapper);
    }

    @Override
    public List<Video> searchVideos(String keyword) {
        // 模糊搜索：标题或简介中包含关键字的视频，按播放量倒序排列
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", keyword)
                .or()
                .like("description", keyword)
                .orderByDesc("view_count");
        return videoMapper.selectList(queryWrapper);
    }

    @Override
    public VideoDetailVO getVideoDetail(Long videoId) {
        // 1. 查询视频本身
        Video video = videoMapper.selectById(videoId);
        if (video == null) {
            throw new RuntimeException("视频不存在或已被删除");
        }

        // 2. 增加播放量 (利用 MyBatis-Plus 构建原生 SQL：update video set view_count = view_count + 1 where id = ?)
        UpdateWrapper<Video> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", videoId).setSql("view_count = view_count + 1");
        videoMapper.update(null, updateWrapper);

        // 3. 将最新的播放量加 1，以反映在当前返回的数据中
        video.setViewCount(video.getViewCount() + 1);

        // 4. 查询视频作者的信息
        User author = userMapper.selectById(video.getUserId());

        // 5. 组装 VO 返回给前端
        VideoDetailVO vo = new VideoDetailVO();
        // BeanUtils 可以自动把同名的属性（比如 title, videoUrl）拷贝过去
        BeanUtils.copyProperties(video, vo);

        if (author != null) {
            vo.setAuthorId(author.getId());
            vo.setAuthorName(author.getNickname());
            vo.setAuthorAvatar(author.getAvatar());
        } else {
            vo.setAuthorName("账号已注销");
        }

        return vo;
    }
}