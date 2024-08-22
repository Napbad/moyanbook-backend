package com.moyanshushe.controller;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/7 上午23:37
    @Description: 
    - moyan
*/

import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.member.PublicMemberSpecification;
import com.moyanshushe.model.entity.Member;
import com.moyanshushe.service.MemberInfoService;
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
@RequiredArgsConstructor
@RequestMapping({"/member-info"})
public class MemberInfoController {

    private final MemberInfoService memberInfoService;

    /**
     * 查找用户
     *
     * @param memberSpecification 用户注册信息
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
    @PostMapping({"/query"})
    public ResponseEntity<Result> queryUser(@RequestBody PublicMemberSpecification memberSpecification) {
        log.info("member query: {}", memberSpecification);

        Page<Member> success = memberInfoService.queryMember(memberSpecification);

        return
                ResponseEntity.ok(
                        Result.success(
                                success));
    }
}
