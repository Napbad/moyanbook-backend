package com.moyanshushe.controller;

/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/13 上午9:36
    @Description:
        此文件用于处理搜索相关的请求，包括物品、用户和会员的搜索操作。
*/

import com.moyanshushe.client.CommonServiceClient;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.item.PublicItemSpecification;
import com.moyanshushe.model.dto.member.PublicMemberSpecification;
import com.moyanshushe.model.dto.user.PublicUserSpecification;
import com.moyanshushe.model.entity.Member;
import com.moyanshushe.model.entity.User;
import com.moyanshushe.service.ItemService;
import com.moyanshushe.service.MemberInfoService;
import com.moyanshushe.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 搜索类，处理搜索功能
 */
@Api
@Slf4j

@RestController
@RequestMapping({"/search"})
@RequiredArgsConstructor
public class SearchController {

    private final UserInfoService userInfoService;
    private final MemberInfoService memberInfoService;
    private final CommonServiceClient commonServiceClient;
    private final ItemService itemService;

    /**
     * 搜索物品
     *
     * @param specification 物品搜索规格
     * @return 返回搜索结果
     */
    @Api
    @PostMapping("/item")
    public ResponseEntity<Result> searchItem(@RequestBody PublicItemSpecification specification) {

        return ResponseEntity.ok(Result.success(itemService.queryPublic(specification)));
    }

    /**
     * 查找用户
     *
     * @param userSpecification 用户搜索规格
     * @return 返回搜索结果
     */
    @Api
    @PostMapping({"/user"})
    public ResponseEntity<Result> queryUser(@RequestBody PublicUserSpecification userSpecification) {
        log.info("query user: {}", userSpecification);

        Page<User> result = userInfoService.queryUser(userSpecification);

        return ResponseEntity.ok(Result.success(result));
    }

    /**
     * 查找会员
     *
     * @param memberSpecification 会员搜索规格
     * @return 返回搜索结果
     */
    @Api
    @PostMapping({"/member"})
    public ResponseEntity<Result> queryMember(@RequestBody PublicMemberSpecification memberSpecification) {
        log.info("query member: {}", memberSpecification);

        Page<Member> result = memberInfoService.queryMember(memberSpecification);

        return ResponseEntity.ok(Result.success(result));
    }

}
