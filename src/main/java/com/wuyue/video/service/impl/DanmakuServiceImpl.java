package com.wuyue.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuyue.video.mapper.DanmakuMapper;
import com.wuyue.video.model.dto.DanmakuDTO;
import com.wuyue.video.model.entity.Danmaku;
import com.wuyue.video.service.DanmakuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DanmakuServiceImpl implements DanmakuService {

    @Autowired
    private DanmakuMapper danmakuMapper;

    @Override
    public void sendDanmaku(Long userId, DanmakuDTO dto) {
        Danmaku danmaku = new Danmaku();
        BeanUtils.copyProperties(dto, danmaku);
        danmaku.setUserId(userId);

        // 如果前端没传颜色，默认白色
        if (danmaku.getColor() == null || danmaku.getColor().isBlank()) {
            danmaku.setColor("#FFFFFF");
        }

        danmakuMapper.insert(danmaku);
    }

    @Override
    public List<DanmakuDTO> getDanmakusByVideoId(Long videoId) {
        // 查询该视频的所有弹幕，并按照时间轴(video_time)从小到大排序
        QueryWrapper<Danmaku> wrapper = new QueryWrapper<>();
        wrapper.eq("video_id", videoId).orderByAsc("video_time");

        List<Danmaku> danmakuList = danmakuMapper.selectList(wrapper);

        // 把数据库里的实体列表，转换成前端需要的 DTO 列表返回
        return danmakuList.stream().map(d -> {
            DanmakuDTO dto = new DanmakuDTO();
            BeanUtils.copyProperties(d, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}