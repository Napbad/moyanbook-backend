package com.moyanshushe.service.impl;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.model.entity.Objects;
import com.moyanshushe.model.entity.User;
import com.moyanshushe.service.UserService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    public void testRegister() {

    }

    @Test
    @Order(2)
    public void testVerify() {

    }
}
