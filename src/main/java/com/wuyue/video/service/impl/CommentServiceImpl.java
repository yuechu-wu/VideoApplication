package com.wuyue.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuyue.video.mapper.CommentMapper;
import com.wuyue.video.mapper.UserMapper;
import com.wuyue.video.model.dto.CommentPublishDTO;
import com.wuyue.video.model.entity.Comment;
import com.wuyue.video.model.entity.User;
import com.wuyue.video.model.vo.CommentVO;
import com.wuyue.video.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void publishComment(Long userId, CommentPublishDTO dto) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(dto, comment);
        comment.setUserId(userId);
        commentMapper.insert(comment);
    }

    @Override
    public List<CommentVO> getCommentTree(Long videoId) {
        // 1. 一次性查出这个视频下的所有评论（按时间倒序排列）
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("video_id", videoId).orderByDesc("create_time");
        List<Comment> allComments = commentMapper.selectList(wrapper);

        if (allComments.isEmpty()) {
            return new ArrayList<>();
        }

        // 2. 将所有 Comment 实体转换为 CommentVO，并补全用户信息（头像、昵称）
        List<CommentVO> allVOs = allComments.stream().map(c -> {
            CommentVO vo = new CommentVO();
            BeanUtils.copyProperties(c, vo);
            User user = userMapper.selectById(c.getUserId());
            if (user != null) {
                vo.setNickname(user.getNickname());
                vo.setAvatar(user.getAvatar());
            } else {
                vo.setNickname("未知用户");
            }
            vo.setReplies(new ArrayList<>()); // 初始化空列表，防止前端报错
            return vo;
        }).collect(Collectors.toList());

        // 3. 算法核心：找出所有一级评论，并把子评论塞进对应的父评论里
        List<CommentVO> rootComments = new ArrayList<>();

        // 用一个 Map 按照 parentId 把评论分组，极大提升查找效率
        Map<Long, List<CommentVO>> replyMap = allVOs.stream()
                .filter(vo -> vo.getParentId() != null)
                .collect(Collectors.groupingBy(CommentVO::getParentId));

        // 遍历所有评论，挑出一级评论，并去 Map 里领取属于它的子评论
        for (CommentVO vo : allVOs) {
            if (vo.getParentId() == null) {
                // 找找有没有回复它的子评论，如果有，塞进去
                List<CommentVO> children = replyMap.get(vo.getId());
                if (children != null) {
                    vo.setReplies(children);
                }
                rootComments.add(vo); // 放入根节点列表
            }
        }

        return rootComments;
    }
}