package com.moyanshushe.controller;

/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/13 上午9:36
    @Description:
*/

import com.moyanshushe.client.CommonServiceClient;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.item.ItemSpecification;
import com.moyanshushe.model.dto.user.PublicUserSpecification;
import com.moyanshushe.model.entity.User;
import com.moyanshushe.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@Slf4j
@RestController
@RequestMapping({"/search"})
@RequiredArgsConstructor
public class SearchController {

    private final UserInfoService userInfoService;
    private final CommonServiceClient commonServiceClient;

    @Api
    @PostMapping("/item")
    public ResponseEntity<Result> searchItem(ItemSpecification specification) {
        return commonServiceClient.fetchPublicItems(specification);
    }
    /**
     * 查找用户
     *
     * @param userSpecification 用户信息
     * @return 注册成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/user"})
    public ResponseEntity<Result> queryUser(@RequestBody PublicUserSpecification userSpecification) {
        log.info("user register: {}", userSpecification);

        Page<User> success = userInfoService.queryUser(userSpecification);

        return
                ResponseEntity.ok(
                        Result.success(
                                success));
    }
}
