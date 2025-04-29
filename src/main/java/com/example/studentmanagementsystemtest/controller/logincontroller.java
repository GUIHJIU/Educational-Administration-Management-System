package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.User;
import com.example.studentmanagementsystemtest.service.loginservice;
import com.example.studentmanagementsystemtest.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

@RestController
@RequestMapping("/login")
public class logincontroller {
    @Autowired
    loginservice service;

    @PostMapping("/login")
    public ResponseEntity< ? > finduser(@RequestBody User user1) {
        User user = service.findUser(user1);
        if (user == null)
            return ResponseEntity.status(401).body("Invalid credentials");
        if (!verifyPassword(user1.getPassword(), user.getSalt(), user.getPassword())) {
            ResponseEntity.status(401).body("Invalid credentials");
        }
        String token = JwtUtil.generateToken(user1.getUsername());
        System.out.println("验证成功" + token);
        return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
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
