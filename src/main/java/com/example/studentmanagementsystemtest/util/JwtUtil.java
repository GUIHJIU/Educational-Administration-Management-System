package com.example.studentmanagementsystemtest.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

    // 访问Token过期时间（1小时）
    private static final long ACCESS_TOKEN_EXPIRATION = 3600_000;
    // 刷新Token过期时间（10天）
    private static final long REFRESH_TOKEN_EXPIRATION = 864_000_000;

    // 生成访问Token
    public static String generateAccessToken(String username) {
        return buildToken(username, ACCESS_TOKEN_EXPIRATION);
    }

    // 生成刷新Token
    public static String generateRefreshToken(String username) {
        return buildToken(username, REFRESH_TOKEN_EXPIRATION);
    }

    // 构建Token的公共方法
    private static String buildToken(String username, long expiration) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    // 验证并解析Token
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 获取用户名
    public static String getUsername(String token) {
        return parseToken(token).getSubject();
    }

    // 验证Token是否有效
    public static boolean isTokenValid(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().after(new Date());
        } catch (ExpiredJwtException e) {
            // Token已过期
            return false;
        } catch (Exception e) {
            // 其他异常（签名错误、格式错误等）
            return false;
        }
    }

    // 获取Token过期时间
    public static Date getTokenExpiration(String token) {
        return parseToken(token).getExpiration();
    }
}
