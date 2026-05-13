package com.wuyue.video.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                // 拦截规则保持不变
                .addPathPatterns("/api/user/**", "/api/videos/upload", "/api/videos/my")
                // 放行规则中新增 /api/videos/search
                .excludePathPatterns("/api/auth/**", "/api/videos/recommend", "/api/videos/search", "/api/videos/*");
    }
}