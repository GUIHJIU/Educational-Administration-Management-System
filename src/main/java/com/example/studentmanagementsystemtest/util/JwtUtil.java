package com.example.studentmanagementsystemtest.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = System.getenv("JWT_SECRET"); // 从外部获取密钥

    static {
        if (System.getenv("JWT_SECRET") == null) {
            throw new IllegalStateException("未配置环境变量 JWT_SECRET！");
        }
    }

    static SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    private static final long EXPIRATION = 864_000_000; // 10天

    // 生成Token（包含签发时间）
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    // 验证并解析Claims
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 获取用户名（基于解析后的Claims）
    public static String getUsername(String token) {
        return parseToken(token).getSubject();
    }
}
