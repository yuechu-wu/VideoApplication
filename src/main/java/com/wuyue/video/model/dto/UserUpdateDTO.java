package com.wuyue.video.model.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String nickname; // 昵称
    private String avatar;   // 头像
    private String sign;     // 个人签名
}