package com.wuyue.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyue.video.model.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}