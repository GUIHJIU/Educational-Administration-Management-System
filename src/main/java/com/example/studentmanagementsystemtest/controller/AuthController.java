package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.Exception.AuthException;
import com.example.studentmanagementsystemtest.util.ErrorCode;
import com.example.studentmanagementsystemtest.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/refresh")
    public ResponseEntity< String > refreshToken(
            @RequestHeader("Refresh-Token") String refreshToken) {

        if (!JwtUtil.isTokenValid(refreshToken)) {
            throw new AuthException(ErrorCode.UNAUTHORIZED);
        }

        String username = JwtUtil.getUsername(refreshToken);
        String newAccessToken = JwtUtil.generateAccessToken(username);

        return ResponseEntity.ok(newAccessToken);
    }

    @PostMapping("/validate")
    public ResponseEntity< String > validateToken(
            @RequestHeader("Authorization") String authHeader) {

        // 检查 Authorization 头格式
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthException(ErrorCode.UNAUTHORIZED);
        }

        // 提取 token
        String token = authHeader.substring(7);

        // 验证 token 有效性
        if (!JwtUtil.isTokenValid(token)) {
            throw new AuthException(ErrorCode.UNAUTHORIZED);
        }

        // 获取用户名（可选，可用于进一步验证）
        String username = JwtUtil.getUsername(token);

        return ResponseEntity.ok("Token is valid");
    }
}

