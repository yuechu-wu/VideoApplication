package com.wuyue.video.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtUtils {
    // ✨ 修改1：不要随机生成，改用固定的字符串密钥（至少32个字符）
    private static final String SECRET_STR = "wuyue_video_project_secret_key_2026_safe";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_STR.getBytes(StandardCharsets.UTF_8));

    // ✨ 修改2：计算时长时，每一个数字后面都加上 L，确保按 long 计算，防止溢出
    private static final long EXPIRE = 1000L * 60L * 60L * 24L; // 24小时

    public static String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(KEY)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}