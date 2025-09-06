package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.User;
import com.example.studentmanagementsystemtest.mapper.RegisterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/logon")
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private static final Set< String > VALID_POSITIONS = Set.of("student", "teacher", "admin");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    @Autowired
    RegisterMapper registerMapper;

    @PostMapping("/logon")
    public ResponseEntity< ? > RegisterUser(@RequestParam String username,
                                            @RequestParam String rawPassword,
                                            @RequestParam String position) {

        if (username.isEmpty() || rawPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("用户名和密码不能为空");
        }

        if (!PASSWORD_PATTERN.matcher(rawPassword).matches()) {
            return ResponseEntity.badRequest().body("密码需至少8位，包含大小写字母和数字");
        }
        if (!VALID_POSITIONS.contains(position)) {
            return ResponseEntity.badRequest().body("用户身份无效，必须是student、teacher或admin之一");
        }
        if (registerMapper.existsByUsername(username) > 0) {
            return ResponseEntity.status(409).body("用户名已存在");
        }
        try {
            Map< String, String > hashedPassword = encryptPassword(rawPassword);
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(hashedPassword.get("hash"));
            newUser.setSalt(hashedPassword.get("salt"));
            newUser.setPosition(position);
            registerMapper.insert(newUser);
            return ResponseEntity.ok("注册成功");
        } catch (Exception e) {
            logger.error("注册失败: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("服务器内部错误");
        }
    }

    public Map< String, String > encryptPassword(String rawPassword) {
        if (rawPassword.isEmpty())
            return null;
        byte[] salt = new byte[0];
        byte[] hash = new byte[0];
        try {
            SecureRandom random = new SecureRandom();
            salt = new byte[16];
            random.nextBytes(salt);

            String algorithm = "PBKDF2WithHmacSHA256";
            int iterations = 65536;
            int keyLength = 256;

            PBEKeySpec spec = new PBEKeySpec(rawPassword.toCharArray(), salt, iterations, keyLength);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
            hash = skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加密失败");
        }
        String saltstring = Base64.getEncoder().encodeToString(salt);
        String hashstring = Base64.getEncoder().encodeToString(hash);
        return Map.of(
                "salt", saltstring,
                "hash", hashstring
        );
    }
}
