package com.wuyue.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wuyue.video.common.utils.JwtUtils;
import com.wuyue.video.mapper.UserMapper;
import com.wuyue.video.model.dto.UserAuthDTO;
import com.wuyue.video.model.dto.UserUpdateDTO;
import com.wuyue.video.model.entity.User;
import com.wuyue.video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void register(UserAuthDTO dto) {
        // 在这里拆开盒子拿数据
        String username = dto.getUsername();
        String password = dto.getPassword();

        // 校验是否存在
        if (userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username)) != null) {
            throw new RuntimeException("该用户名已被占用");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password)); // 加密存储
        user.setNickname("新用户_" + username);
        userMapper.insert(user);
    }

    @Override
    public String login(UserAuthDTO dto) {
        // 在这里拆开盒子拿数据
        String username = dto.getUsername();
        String password = dto.getPassword();

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (user == null || !encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 登录成功生成 Token
        return JwtUtils.createToken(Map.of("userId", user.getId(), "username", user.getUsername()));
    }

    @Override
    public void updateUserInfo(Long userId, UserUpdateDTO dto) {
        // MyBatis-Plus 的 updateById 有个非常强大的特性：它会自动忽略 null 值。
        User user = new User();
        user.setId(userId);
        user.setNickname(dto.getNickname());
        user.setAvatar(dto.getAvatar());
        user.setSign(dto.getSign());

        userMapper.updateById(user);
    }
}