package com.wuyue.video.controller;

import com.wuyue.video.common.Result;
import com.wuyue.video.common.utils.UserContext;
import com.wuyue.video.model.dto.UserUpdateDTO;
import com.wuyue.video.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理", description = "个人中心信息维护")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "修改个人信息", description = "需要 Token，支持修改昵称、头像和签名")
    @PutMapping("/profile")
    public Result<String> updateProfile(@RequestBody UserUpdateDTO dto) {
        Long userId = UserContext.getUserId();
        userService.updateUserInfo(userId, dto);
        return Result.success("个人信息修改成功");
    }
}