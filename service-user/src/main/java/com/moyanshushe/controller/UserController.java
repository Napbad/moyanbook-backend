package com.moyanshushe.controller;

import com.moyanshushe.client.*;
import com.moyanshushe.constant.AccountConstant;
import com.moyanshushe.constant.JwtClaimsConstant;
import com.moyanshushe.constant.VerifyConstant;
import com.moyanshushe.constant.WebIOConstant;
import com.moyanshushe.exception.NoAuthorityException;
import com.moyanshushe.exception.common.InputInvalidException;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.UserLoginResult;
import com.moyanshushe.model.dto.address.AddressCreateInput;
import com.moyanshushe.model.dto.address.AddressSpecification;
import com.moyanshushe.model.dto.address_part1.AddressPart1Specification;
import com.moyanshushe.model.dto.address_part2.AddressPart2Specification;
import com.moyanshushe.model.dto.coupon.CouponSpecification;
import com.moyanshushe.model.dto.coupon.CouponView;
import com.moyanshushe.model.dto.item.*;
import com.moyanshushe.model.dto.category.CategorySpecification;
import com.moyanshushe.model.dto.order.OrderForAdd;
import com.moyanshushe.model.dto.order.OrderForDelete;
import com.moyanshushe.model.dto.order.OrderForUpdate;
import com.moyanshushe.model.dto.order.OrderSpecification;
import com.moyanshushe.model.dto.user.*;
import com.moyanshushe.model.entity.User;
import com.moyanshushe.properties.JwtProperties;
import com.moyanshushe.service.UserService;
import com.moyanshushe.utils.AliOssUtil;
import com.moyanshushe.utils.UserContext;
import com.moyanshushe.utils.JwtUtil;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.babyfish.jimmer.sql.ast.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * 用户控制器类，负责处理用户相关的HTTP请求
 */
@Api

@RestController
@RequestMapping({"/user"})
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final JwtProperties jwtProperties;
    private final CommonServiceClient commonServiceClient;
    private final AliOssUtil aliOssUtil;

    // 构造函数：初始化用户服务和JWT属性
    public UserController(UserService userService,
                          JwtProperties jwtProperties,
                          CommonServiceClient commonServiceClient, AliOssUtil aliOssUtil) {
        this.userService = userService;
        this.jwtProperties = jwtProperties;
        this.commonServiceClient = commonServiceClient;

        log.info("UserController initialized");
        this.aliOssUtil = aliOssUtil;
    }

    /**
     * 注册用户
     *
     * @param userForRegister 用户注册信息
     *                        UserForRegister {
     *                        id
     *                        name!
     *                        email!
     *                        password!
     *                        captcha: String!
     *                        address {
     *                        id
     *                        }
     *                        status  1 -> normal(default)  2 -> unsafe  3 ->  freeze
     *                        type    1 -> normal(students)  2 -> store
     *                        }
     * @return 注册成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/register"})
    public ResponseEntity<Result> registerUser(@RequestBody UserForRegister userForRegister) {
        log.info("user register: {}", userForRegister);

        boolean success = this.userService.userRegister(userForRegister);

        return success ?
                ResponseEntity.ok(Result.success(AccountConstant.ACCOUNT_REGISTER_SUCCESS))
                : ResponseEntity.badRequest().body(Result.error(AccountConstant.ACCOUNT_REGISTER_FAILURE));
    }

    /**
     * 用户登录
     *
     * @param userForLogin 用户登录信息
     *                     input UserForLogin {
     *                     id  --- choose one
     *                     name  ---
     *                     phone  ---
     *                     email  ---
     *                     password !
     *                     }
     * @return 登录成功返回200，用户信息和包含JWT的成功消息，失败返回401和错误消息
     */
    @Api
    @PostMapping({"/login"})
    public ResponseEntity<Result> loginUser(@RequestBody UserForLogin userForLogin) {

        if ((userForLogin.getEmail() == null
                && userForLogin.getPhone() == null
                && userForLogin.getName() == null
                && userForLogin.getId() == null)) {
            throw new InputInvalidException();
        }

        log.info("user login: id: {}, name: {}, email: {}, phone: {}"
                , userForLogin.getId(), userForLogin.getName(), userForLogin.getEmail(), userForLogin.getPhone());

        User user = this.userService.userLogin(userForLogin);
        if (user != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(JwtClaimsConstant.ID, user.id());
            String jwt = JwtUtil.createJWT(this.jwtProperties.getSecretKey(), this.jwtProperties.getTtl(), map);

            return ResponseEntity.ok(Result.success(new UserLoginResult(
                    user, jwt
            )));

        } else {
            return ResponseEntity.status(401).body(Result.error(AccountConstant.ACCOUNT_LOGIN_FAILURE));
        }
    }

    /**
     * 更新用户信息
     *
     * @param userForUpdate 用户更新信息
     *                      UserForUpdate {
     *                      id!
     *                      name (all below are optional)
     *                      gender
     *                      age
     *                      profileUrl
     *                      address {
     *                      id
     *                      }
     *                      }
     * @return 更新成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/update"})
    public ResponseEntity<Result> update(@RequestBody UserForUpdate userForUpdate) {
        log.info("user update: {}", userForUpdate.getId());
        aliOssUtil.checkUrlIsAliOss(userForUpdate.getProfileUrl());

        boolean isChanged = this.userService.userUpdate(userForUpdate);

        return isChanged
                ? ResponseEntity.ok(Result.success(AccountConstant.ACCOUNT_CHANGE_SUCCESS))
                : ResponseEntity.badRequest().body(Result.error(AccountConstant.ACCOUNT_CHANGE_FAILURE));
    }

    /**
     * @param userForUpdatePassword UserForUpdatePassword {
     *                              id!
     *                              password!
     *                              email!
     *                              newPassword: String!
     *                              captcha: String!
     *                              }
     * @return 更改成功返回200和成功消息，失败返回错误消息
     */
    @Api
    @PostMapping({"/change-password"})
    public ResponseEntity<Result> changePassword(@RequestBody UserForUpdatePassword userForUpdatePassword) {
        log.info("user change password: {}", userForUpdatePassword.getId());

        boolean isUpdated = this.userService.updatePassword(userForUpdatePassword);

        return isUpdated
                ? ResponseEntity.ok(Result.success(AccountConstant.ACCOUNT_CHANGE_SUCCESS))
                : ResponseEntity.badRequest().body(Result.error(AccountConstant.ACCOUNT_CHANGE_FAILURE));
    }

    /**
     * 绑定用户信息
     *
     * @param userForBinding 用户绑定信息
     *                       UserForBinding {
     *                       id!
     *                       phone -- choose one
     *                       email --
     *                       captcha: String!
     *                       }
     * @return 绑定成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/bind"})
    public ResponseEntity<Result> bind(@RequestBody UserForBinding userForBinding) {

        log.info("user: {} binding", userForBinding.getId());

        boolean bindSuccess = this.userService.bind(userForBinding);

        return bindSuccess
                ? ResponseEntity.ok(Result.success(AccountConstant.ACCOUNT_BIND_SUCCESS))
                : ResponseEntity.badRequest().body(Result.error(AccountConstant.ACCOUNT_BIND_FAILURE));
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
        log.info("user: {} logout", id);
        return ResponseEntity.ok().body(Result.success(AccountConstant.ACCOUNT_LOGOUT_SUCCESS));
    }

    /**
     * 验证用户信息
     *
     * @param userForVerify 用户验证信息
     *                      <p>
     *                      UserForVerify {
     *                      id
     *                      phone --- choose one
     *                      email ---
     *                      }
     * @return 验证成功返回200和成功消息
     */
    @Api
    @PostMapping({"/verify"})
    public ResponseEntity<Result> verify(@RequestBody UserForVerify userForVerify) {
        log.info("user verify: {}", userForVerify);
        this.userService.userVerify(userForVerify);
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

        if (specification.getUserIds() == null
                || specification.getUserIds().size() != 1
                || !((ArrayList<?>) specification.getUserIds()).getFirst().equals(UserContext.getUserId())
        ) {
            throw new InputInvalidException();
        }

        specification.setUserIds(new ArrayList<>(Collections.singletonList(UserContext.getUserId())));

        return commonServiceClient.fetchItems(specification);
    }

    /**
     * 添加一个新的物品。
     *
     * @param itemForAdd 待添加的物品详情
     * @return 返回添加结果
     */
    @Api
    @PostMapping("/item/add")
    public ResponseEntity<Result> addItem(@RequestBody ItemForAdd itemForAdd) {

        if (UserContext.getUserId() == null) {
            throw new NoAuthorityException();
        }
        if (!UserContext.getUserId().equals(itemForAdd.getUserId())) {
            throw new InputInvalidException();
        }

        itemForAdd.setUserId(UserContext.getUserId());

        return commonServiceClient.addItem(itemForAdd);
    }

    /**
     * 更新一个物品的信息。
     *
     * @param itemForUpdate 待更新的物品详情
     * @return 返回更新结果
     */
    @Api
    @PostMapping("/item/update")
    public ResponseEntity<Result> updateItem(@RequestBody ItemForUpdate itemForUpdate) {
        if (itemForUpdate.getUser() == null) {
            return ResponseEntity.badRequest().body(Result.error(WebIOConstant.INPUT_INVALID));
        }
        if (itemForUpdate.getUser().getId() == 0) {
            throw new InputInvalidException();
        }

        return commonServiceClient.updateItem(itemForUpdate);
    }

    /**
     * 根据指定条件删除物品。
     *
     * @param itemForDelete 删除物品的条件
     * @return 返回删除结果
     */
    @Api
    @PostMapping("/item/delete")
    public ResponseEntity<Result> deleteItem(@RequestBody ItemForDelete itemForDelete) {
        itemForDelete.setOperatorId(UserContext.getUserId());

        return commonServiceClient.deleteItems(itemForDelete);
    }

    /**
     * 根据指定条件获取标签信息。
     *
     * @param category 查询标签的条件
     * @return 返回标签查询结果
     */
    @Api
    @PostMapping("/category/query")
    public ResponseEntity<Result> queryCategories(@RequestBody CategorySpecification category) {
        return commonServiceClient.queryCategories(category);
    }

    /**
     * 上传图片。
     *
     * @param file 待上传的图片文件
     * @return 返回上传结果
     */
    @Api
    @PostMapping("/file/upload/image")
    public CompletableFuture<Result> uploadImage(@RequestParam("file") MultipartFile file) {
        return commonServiceClient.uploadImage(file);
    }

    /**
     * 根据指定条件获取地址信息。
     *
     * @param addressForQuery 查询地址的条件
     * @return 返回地址查询结果
     */
    @Api
    @PostMapping("/address/query")
    public ResponseEntity<Result> getAddress(@RequestBody AddressSpecification addressForQuery) {
        return commonServiceClient.queryAddress(addressForQuery);
    }

    @Api
    @PostMapping("address/add")
    public ResponseEntity<Result> addAddress(@RequestBody AddressCreateInput addressForQuery) {
        return commonServiceClient.addAddress(addressForQuery);
    }

    @Api
    @PostMapping("address-part1/query")
    public ResponseEntity<Result> getAddressPart1(@RequestBody AddressPart1Specification specification) {
        return commonServiceClient.queryAddressPart1(specification);
    }

    @Api
    @PostMapping("address-part2/query")
    public ResponseEntity<Result> getAddressPart2(@RequestBody AddressPart2Specification specification) {
        return commonServiceClient.readAddressPart2(specification);
    }

    /**
     * 根据指定条件获取订单信息。
     *
     * @param specification 查询订单的条件
     * @return 返回订单查询结果
     */
    @Api
    @PostMapping("/order/query")
    public ResponseEntity<Result> getOrder(@RequestBody OrderSpecification specification) {
        specification.setUserId(UserContext.getUserId());

        return commonServiceClient.queryOrder(specification);
    }

    /**
     * 添加一个新的订单。
     *
     * @param orderForAdd 待添加的订单详情
     * @return 返回添加结果
     */
    @Api
    @PostMapping("/order/add")
    public ResponseEntity<Result> addOrder(@RequestBody OrderForAdd orderForAdd) {
        if (!Objects.equals(orderForAdd.getUserId(), UserContext.getUserId())) {

            log.info("user: {} add order: {} failed", UserContext.getUserId(), orderForAdd);

            throw new NoAuthorityException();
        }

        return commonServiceClient.addOrder(orderForAdd);
    }

    /**
     * 更新一个订单的信息。
     *
     * @param orderForUpdate 待更新的订单详情
     * @return 返回更新结果
     */
    @Api
    @PostMapping("/order/update")
    public ResponseEntity<Result> updateOrder(@RequestBody OrderForUpdate orderForUpdate) {
        if (orderForUpdate.getUserId() == null) {
            throw new NoAuthorityException();
        }

        int userId = orderForUpdate.getUserId();

        if (userId != UserContext.getUserId()) {
            throw new NoAuthorityException();
        }

        return commonServiceClient.updateOrder(orderForUpdate);
    }

    /**
     * 根据指定条件删除订单。
     *
     * @param itemForDelete 删除订单的条件
     * @return 返回删除结果
     */
    @Api
    @PostMapping("/order/delete")
    public ResponseEntity<Result> deleteOrder(@RequestBody OrderForDelete itemForDelete) {
        if (!Objects.equals(itemForDelete.getUserId(), UserContext.getUserId())) {

            throw new NoAuthorityException();
        }

        return commonServiceClient.deleteOrder(itemForDelete);
    }

    /**
     * 获取优惠券
     *
     * @param specification 优惠券规格说明，通过RequestBody接收前端传来的JSON数据。
     * @return 返回一个ResponseEntity对象，其中包含获取优惠券操作的结果。如果操作成功，body中包含具体结果数据。
     */
    @Api
    @PostMapping("/coupon/query")
    public Page<CouponView> getCoupon(@RequestBody CouponSpecification specification) {
        // 调用commonServiceClient的getCoupon方法获取优惠券信息，并将结果封装成成功响应返回
        specification.setUserId(UserContext.getUserId());

        return commonServiceClient.queryCoupon(specification);
    }
}
