package com.moyanshushe.controller;

/*
 * Author: Napbad
 * Version: 1.0
 */


import com.moyanshushe.client.CommonServiceClient;
import com.moyanshushe.constant.AccountConstant;
import com.moyanshushe.constant.JwtClaimsConstant;
import com.moyanshushe.constant.VerifyConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.address.AddressSpecification;
import com.moyanshushe.model.dto.item.ItemSpecification;
import com.moyanshushe.model.dto.label.LabelSpecification;
import com.moyanshushe.model.dto.member.*;
import com.moyanshushe.model.dto.order.OrderSpecification;
import com.moyanshushe.model.entity.Member;
import com.moyanshushe.properties.JwtProperties;
import com.moyanshushe.service.MemberAccountService;
import com.moyanshushe.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.client.meta.Api;
import org.babyfish.jimmer.sql.ast.tuple.Tuple2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api
@Slf4j
@RestController
@RequestMapping({"/member"})
public class MemberController {
    private final MemberAccountService memberAccountService;
    private final JwtProperties jwtProperties;
    private final CommonServiceClient commonServiceClient;


    // 构造函数：初始化用户服务和JWT属性
    public MemberController(MemberAccountService memberAccountService,
                           JwtProperties jwtProperties,
                           CommonServiceClient commonServiceClient) {
        this.memberAccountService = memberAccountService;
        this.jwtProperties = jwtProperties;
        this.commonServiceClient = commonServiceClient;

        log.info("MemberController initialized");
    }

    /**
     * 注册用户
     *
     * @param memberForRegister 用户注册信息
     * @return 注册成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/register"})
    public ResponseEntity<Result> registerMember(@RequestBody MemberForRegister memberForRegister) {
        log.info("member register: {}", memberForRegister);

        boolean success =
                this.memberAccountService.memberRegister(memberForRegister);

        return success ?
                ResponseEntity.ok(Result.success(AccountConstant.ACCOUNT_REGISTER_SUCCESS))
                : ResponseEntity.badRequest().body(Result.error(AccountConstant.ACCOUNT_REGISTER_FAILURE));
    }

    /**
     * 用户登录
     *
     * @param memberForLogin 用户登录信息
     * @return 登录成功返回200和包含JWT的成功消息，失败返回401和错误消息
     */
    @Api
    @PostMapping({"/login"})
    public ResponseEntity<Result> loginMember(@RequestBody MemberForLogin memberForLogin) {

        log.info("member login: id: {}, name: {}, email: {}, phone: {}"
                , memberForLogin.getId(), memberForLogin.getName(), memberForLogin.getEmail(), memberForLogin.getPhone());

        Member member = this.memberAccountService.memberLogin(memberForLogin);
        if (member != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(JwtClaimsConstant.MEMBER_ID, member.id());
            String jwt = JwtUtil.createJWT(this.jwtProperties.getSecretKey(), this.jwtProperties.getTtl(), map);

            return ResponseEntity.ok(Result.success(new Tuple2<>(member, jwt)));

        } else {
            return ResponseEntity.status(401).body(Result.error(AccountConstant.ACCOUNT_LOGIN_FAILURE));
        }

    }

    /**
     * 更新用户信息
     *
     * @param memberForUpdate 用户更新信息
     * @return 更新成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/update"})
    public ResponseEntity<Result> update(@RequestBody MemberForUpdate memberForUpdate) {
        log.info("member update: {}", memberForUpdate.getId());

        boolean isChanged = this.memberAccountService.memberUpdate(memberForUpdate);

        return isChanged
                ? ResponseEntity.ok(Result.success(AccountConstant.ACCOUNT_CHANGE_SUCCESS))
                : ResponseEntity.badRequest().body(Result.error(AccountConstant.ACCOUNT_CHANGE_FAILURE));
    }

    @Api
    @PostMapping({"/change-password"})
    public ResponseEntity<Result> changePassword(@RequestBody MemberForUpdatePassword memberForUpdatePassword) {
        log.info("member change password: {}", memberForUpdatePassword.getId());

//        boolean isUpdated = this.memberService.memberUpdatePassword(memberForUpdatePassword);

//        return isUpdated
//                ? ResponseEntity.ok(Result.success(AccountConstant.USER_CHANGE_SUCCESS))
//                : ResponseEntity.badRequest().body(Result.error(AccountConstant.USER_CHANGE_FAILURE));
        return ResponseEntity.ok(Result.success(Result.success(AccountConstant.ACCOUNT_CHANGE_SUCCESS)));
    }

    /**
     * 绑定用户信息
     *
     * @param memberForBinding 用户绑定信息
     * @return 绑定成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/bind"})
    public ResponseEntity<Result> bind(MemberForBinding memberForBinding) {

        log.info("member: {} binding", memberForBinding.getId());

//        boolean bindSuccess = this.memberService.bind(memberForBinding);

//        return bindSuccess
//                ? ResponseEntity.ok(Result.success(AccountConstant.USER_BIND_SUCCESS))
//                : ResponseEntity.badRequest().body(Result.error(AccountConstant.USER_BIND_FAILURE));
        return ResponseEntity.ok(Result.success(Result.success(AccountConstant.ACCOUNT_BIND_SUCCESS)));
    }

    /**
     * 用户登出
     *
     * @param id 用户ID
     * @return 登出成功返回200和成功消息
     */
    @Api
    @PostMapping({"/logout"})
    public ResponseEntity<Result> logout(@RequestParam Long id) {
        log.info("member: {} logout", id);

        return ResponseEntity.ok().body(Result.success(AccountConstant.ACCOUNT_LOGOUT_SUCCESS));
    }

    /**
     * 验证用户信息
     *
     * @param memberForVerify 用户验证信息
     * @return 验证成功返回200和成功消息
     */
    @Api
    @PostMapping({"/verify"})
    public ResponseEntity<Result> verify(@RequestBody MemberForVerify memberForVerify) {
        log.info("member verify: {}", memberForVerify);
        this.memberAccountService.memberVerify(memberForVerify);
        return ResponseEntity.ok().body(Result.success(VerifyConstant.VERIFY_CODE_SENT));
    }

    /**
     * 根据指定的物品规格获取物品信息。
     *
     * @param specification 物品规格详情
     * @return 返回物品查询结果
     */
    @Api
    @PostMapping("/item/fetch")
    public ResponseEntity<Result> fetchItem(@RequestBody ItemSpecification specification) {
        return commonServiceClient.fetchItems(specification);
    }


    /**
     * 根据指定条件获取标签信息。
     *
     * @param label 查询标签的条件
     * @return 返回标签查询结果
     */
    @Api
    @PostMapping("/label/fetch")
    public ResponseEntity<Result> fetchLabels(@RequestBody LabelSpecification label) {
        return commonServiceClient.queryLabels(label);
    }

    /**
     * 根据指定条件获取地址信息。
     *
     * @param addressForQuery 查询地址的条件
     * @return 返回地址查询结果
     */
    @Api
    @PostMapping("/address/get")
    public ResponseEntity<Result> getAddress(@RequestBody AddressSpecification addressForQuery) {
        return commonServiceClient.getAddress(addressForQuery);
    }

    /**
     * 根据指定条件获取订单信息。
     *
     * @param specification 查询订单的条件
     * @return 返回订单查询结果
     */
    @Api
    @PostMapping("/order/fetch")
    public ResponseEntity<Result> getOrder(@RequestBody OrderSpecification specification) {
        return commonServiceClient.getOrder(specification);
    }
}
