package com.wuyue.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyue.video.model.entity.Video;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {
    // 继承了 BaseMapper 之后，MyBatis-Plus 会自动帮我们生成 insert、selectList 等所有基础 SQL
}