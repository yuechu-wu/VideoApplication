package com.wuyue.video.controller;

import com.wuyue.video.common.Result;
import com.wuyue.video.model.dto.UserAuthDTO;
import com.wuyue.video.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "认证管理", description = "负责用户的注册与登录鉴权")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户注册", description = "提供用户名和密码进行新账号创建")
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserAuthDTO dto) {
        userService.register(dto);
        return Result.success("注册成功");
    }

    @Operation(summary = "用户登录", description = "验证账号密码并返回 JWT Token")
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserAuthDTO dto) {
        String token = userService.login(dto);
        return Result.success(token);
    }
}