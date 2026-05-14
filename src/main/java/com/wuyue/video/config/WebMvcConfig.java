package com.wuyue.video.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    // 1. 门禁（拦截器）配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                // 必须带 Token 才能访问的接口
                .addPathPatterns("/api/user/**", "/api/videos/upload", "/api/videos/my", "/api/danmaku/send", "/api/comments/publish")
                // 不需要 Token 就能访问的接口（放行规则）
                .excludePathPatterns("/api/auth/**", "/api/videos/recommend", "/api/videos/search", "/api/videos/*", "/api/danmaku/list", "/api/comments/tree","/api/user/space/*","/api/videos/user/*","/api/file/upload");
    }

    // 2. 🌟 解决前端跨域报错的核心配置 🌟
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有接口被跨域调用
                .allowedOriginPatterns("*") // 允许所有前端网址来源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求方式
                .allowedHeaders("*") // 允许携带任何请求头(包括咱们的 Token)
                .allowCredentials(true) // 允许携带 Cookie
                .maxAge(3600); // 跨域允许时间
    }
}