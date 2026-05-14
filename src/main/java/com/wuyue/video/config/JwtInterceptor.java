package com.wuyue.video.config;

import com.wuyue.video.common.utils.JwtUtils;
import com.wuyue.video.common.utils.UserContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // ✨ 核心修复：遇到浏览器的 OPTIONS 跨域预检请求，直接放行 ✨
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 1. 从请求头中获取 Token (通常前端会放在 Authorization 字段，并带有 Bearer 前缀)
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 去掉 "Bearer " 前缀
            try {
                // 2. 解析 Token
                Claims claims = JwtUtils.parseToken(token);
                Long userId = claims.get("userId", Long.class);

                // 3. 将解析出的用户 ID 存入上下文，方便后面的 Controller 直接获取
                UserContext.setUserId(userId);
                return true; // 放行
            } catch (Exception e) {
                throw new RuntimeException("Token 无效或已过期，请重新登录");
            }
        }
        throw new RuntimeException("未提供身份认证 Token，拒绝访问");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求结束后必须清理，防止内存泄漏和串号
        UserContext.remove();
    }
}