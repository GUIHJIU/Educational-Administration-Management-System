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
}

