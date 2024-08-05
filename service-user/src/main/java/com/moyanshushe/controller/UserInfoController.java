package com.moyanshushe.controller;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/7 上午23:36
    @Description: 
    - A file created by Napbad.
*/

import com.moyanshushe.constant.AccountConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.user.PublicUserSpecification;
import com.moyanshushe.model.dto.user.UserForRegister;
import com.moyanshushe.model.entity.User;
import com.moyanshushe.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api
@Slf4j
@RestController
@RequestMapping({"/user-info"})
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    /**
     * 查找用户
     *
     * @param userSpecification 用户注册信息
     * @return 注册成功返回200和成功消息，失败返回400和错误消息
     */
//    @Api
    @PostMapping({"/query"})
    public ResponseEntity<Result> queryUser(@RequestBody PublicUserSpecification userSpecification) {
        log.info("user query: {}", userSpecification);

        Page<User> success = userInfoService.queryUser(userSpecification);

        return
                ResponseEntity.ok(
                        Result.success(
                               success));
    }
}
