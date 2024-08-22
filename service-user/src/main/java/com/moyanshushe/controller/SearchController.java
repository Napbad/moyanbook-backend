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
import com.moyanshushe.model.dto.member.PublicMemberSpecification;
import com.moyanshushe.model.dto.user.PublicUserSpecification;
import com.moyanshushe.model.entity.Member;
import com.moyanshushe.model.entity.User;
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

    /**
     * @param specification
     * ItemSpecification {
     *     valueIn(id) as ids
     *     like(name)
     *     le(price) as maxPrice
     *     ge(price) as minPrice
     *     status
     *
     *     flat(user) {
     *         valueIn(id) as userIds
     *     } 会被自动设置为空
     *
     *     flat(categories) {
     *         valueIn(id) as labelIds
     *     }
     *
     *     page: Int?
     *     pageSize: Int?
     *
     *     orderByPrice: OrderRule?
     *     orderByCreateTime: OrderRule?
     *     orderByUpdateTime: OrderRule?
     * }
     * @return
     */
    @Api
    @PostMapping("/item")
    public ResponseEntity<Result> searchItem(@RequestBody ItemSpecification specification) {

        return commonServiceClient.fetchPublicItems(specification);
    }

    /**
     * 查找用户
     *
     * @param userSpecification 用户信息
     *                          UserSpecification {
     *                          id (can only search one at one time)
     *                          like(name)
     *                          type
     *                          email
     *                          phone
     *                          <p>
     *                          page: Int?
     *                          pageSize: Int?
     *                          }
     * @return 注册成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/user"})
    public ResponseEntity<Result> queryUser(@RequestBody PublicUserSpecification userSpecification) {
        log.info("user register: {}", userSpecification);

        Page<User> success = userInfoService.queryUser(userSpecification);

        return
                ResponseEntity.ok(Result.success(success));
    }

    /**
     * 查找用户
     *
     * @param memberSpecification 查询信息
     *                            MemberSpecification {
     *                            valueIn(id) as ids 字段为ids 的集合，表示查询在这个ids中的用户，为空则不考虑
     *                            like(name) 类似数据库的like，匹配名字
     *                            status
     *                            address {
     *                            id
     *                            }
     *                            email
     *                            phone
     *                            createPerson{
     *                            id
     *                            }
     *                            updatePerson{
     *                            id
     *                            }
     *                            ge(createTime) greater than
     *                            le(createTime) less than
     *                            ge(updateTime)
     *                            le(updateTime)
     *                            可选，默认0页，10条/页
     *                            <p>
     *                            page: Int?
     *                            pageSize: Int?
     *                            }
     * @return 注册成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/member"})
    public ResponseEntity<Result> queryUser(@RequestBody PublicMemberSpecification memberSpecification) {
        log.info("member query: {}", memberSpecification);

        Page<Member> success = memberInfoService.queryMember(memberSpecification);

        return
                ResponseEntity.ok(
                        Result.success(
                                success));
    }

}
