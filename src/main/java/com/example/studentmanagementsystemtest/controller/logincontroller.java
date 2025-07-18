package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.User;
import com.example.studentmanagementsystemtest.service.loginservice;
import com.example.studentmanagementsystemtest.util.ErrorCode;
import com.example.studentmanagementsystemtest.util.JwtUtil;
import com.example.studentmanagementsystemtest.util.ResponseResult;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

@RestController
@RequestMapping("/login")
public class logincontroller {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(logincontroller.class);
    @Autowired
    loginservice service;


    // 登录接口完整实现
    @PostMapping("/login")
    public ResponseEntity< ResponseResult< User > > finduser(@RequestBody User user1) {
        try {
            // 查询用户
            User user = service.findUser(user1);
            if (user == null) {
                return ResponseEntity.status(401)
                        .body(ResponseResult.< User >error(ErrorCode.UNAUTHORIZED).data(null));
            }

            // 验证密码
            if (!verifyPassword(user1.getPassword(), user.getSalt(), user.getPassword())) {
                return ResponseEntity.status(401)
                        .body(ResponseResult.< User >error(ErrorCode.UNAUTHORIZED)
                                .message("用户名或密码错误")
                                .data(null));
            }

            // 生成token
            String accessToken = JwtUtil.generateAccessToken(user.getUsername());
            String refreshToken = JwtUtil.generateRefreshToken(user.getUsername());

            // 构造返回用户对象
            User responseUser = new User();
            responseUser.setUsername(user.getUsername());
            responseUser.setPosition(user.getPosition());
            responseUser.setId(user.getId());

            // 构造统一返回结构
            ResponseResult< User > responseResult = ResponseResult.success(responseUser)
                    .message("登录成功");

            // 返回结果
            return ResponseEntity.ok()
                    .header("Access-Control-Expose-Headers\", \"Authorization, Refresh-Token")
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Refresh-Token", refreshToken)
                    .body(responseResult);

        } catch (Exception e) {
            logger.error("登录异常", e);
            return ResponseEntity.status(500)
                    .body(ResponseResult.< User >error(ErrorCode.INTERNAL_ERROR)
                            .message("系统异常: " + e.getMessage())
                            .data(null));
        }
    }


    public boolean verifyPassword(String rawPassword, String storedSalt, String storedHash) {
        try {
            // 解码存储的盐值
            byte[] salt = Base64.getDecoder().decode(storedSalt);
            // 使用相同参数重新生成哈希
            String algorithm = "PBKDF2WithHmacSHA256";
            int iterations = 65536;
            int keyLength = 256;
            PBEKeySpec spec = new PBEKeySpec(
                    rawPassword.toCharArray(),
                    salt,
                    iterations,
                    keyLength
            );
            SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
            byte[] newHash = skf.generateSecret(spec).getEncoded();
            // 比较哈希值
            String newHashString = Base64.getEncoder().encodeToString(newHash);
            return newHashString.equals(storedHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
