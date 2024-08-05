package com.moyanshushe.controller;

import com.moyanshushe.constant.AuthorityConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

/*
 * Author: Napbad
 * Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/1")
    public String test1() {
        return "1";
    }

    @PostMapping("/2")
    public String test2(
            HttpServletRequest request, HttpServletResponse response
    ) {
        String header = request.getHeader(AuthorityConstant.USER_AUTHENTICATION_ID);

        log.info("header: {}", header);

        return "2";
    }

    @GetMapping("/0")
    public String test () {
        return "Hello, this is napbad";
    }
}
