package com.wuyue.video.service;

import com.wuyue.video.model.dto.UserAuthDTO;
import com.wuyue.video.model.dto.UserUpdateDTO;

public interface UserService {
    // 升级为接收 DTO 对象
    void register(UserAuthDTO dto);

    // 升级为接收 DTO 对象
    String login(UserAuthDTO dto);

    void updateUserInfo(Long userId, UserUpdateDTO dto);
}