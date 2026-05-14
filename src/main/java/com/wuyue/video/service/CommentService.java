package com.wuyue.video.service;

import com.wuyue.video.model.dto.CommentPublishDTO;
import com.wuyue.video.model.vo.CommentVO;
import java.util.List;

public interface CommentService {
    void publishComment(Long userId, CommentPublishDTO dto);
    List<CommentVO> getCommentTree(Long videoId);
}