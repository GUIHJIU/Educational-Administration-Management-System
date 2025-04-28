package com.example.studentmanagementsystemtest.mapper;

import com.example.studentmanagementsystemtest.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class registerMapperTest {
    @Autowired
    RegisterMapper mapper;

    @Test
    void testInsertMP() {
        User user = new User();
        user.setUsername("adsfs");
        user.setPosition("sdfewtfsdf");
        mapper.insert(user);

    }
}
