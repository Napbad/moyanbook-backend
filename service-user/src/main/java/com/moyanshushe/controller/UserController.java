package com.moyanshushe.controller;

import com.moyanshushe.client.*;
import com.moyanshushe.constant.*;
import com.moyanshushe.exception.NoAuthorityException;
import com.moyanshushe.exception.common.InputInvalidException;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.UserLoginResult;
import com.moyanshushe.model.dto.address.AddressCreateInput;
import com.moyanshushe.model.dto.address.AddressSpecification;
import com.moyanshushe.model.dto.address.AddressView;
import com.moyanshushe.model.dto.address_part1.AddressPart1Specification;
import com.moyanshushe.model.dto.address_part1.AddressPart1View;
import com.moyanshushe.model.dto.address_part2.AddressPart2Specification;
import com.moyanshushe.model.dto.address_part2.AddressPart2View;
import com.moyanshushe.model.dto.category.CategorySubstance;
import com.moyanshushe.model.dto.coupon.CouponSpecification;
import com.moyanshushe.model.dto.coupon.CouponView;
import com.moyanshushe.model.dto.item.*;
import com.moyanshushe.model.dto.category.CategorySpecification;
import com.moyanshushe.model.dto.order.OrderForAdd;
import com.moyanshushe.model.dto.order.OrderForDelete;
import com.moyanshushe.model.dto.order.OrderForUpdate;
import com.moyanshushe.model.dto.order.OrderSpecification;
import com.moyanshushe.model.dto.user.*;
import com.moyanshushe.model.entity.Address;
import com.moyanshushe.model.entity.Order;
import com.moyanshushe.model.entity.User;
import com.moyanshushe.properties.JwtProperties;
import com.moyanshushe.service.*;
import com.moyanshushe.service.impl.AddressServiceImpl;
import com.moyanshushe.service.impl.CouponServiceImpl;
import com.moyanshushe.utils.AliOssUtil;
import com.moyanshushe.utils.UserContext;
import com.moyanshushe.utils.JwtUtil;
import com.moyanshushe.utils.security.AccountUtil;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final AddressService addressService;
    private final AddressPart1Service addressPart1Service;
    private final AddressPart2Service addressPart2Service;
    private final OrderService orderService;
    private final CouponService couponService;

    // 构造函数：初始化用户服务和JWT属性
    public UserController(UserService userService,
                          JwtProperties jwtProperties,
                          CommonServiceClient commonServiceClient,
                          AliOssUtil aliOssUtil,
                          ItemService itemService,
                          CategoryService categoryService,
                          AddressService addressService,
                          AddressPart1Service addressPart1Service,
                          AddressPart2Service addressPart2Service,
                          OrderService orderService,
                          CouponService couponService) {
        this.userService = userService;
        this.jwtProperties = jwtProperties;
        this.commonServiceClient = commonServiceClient;
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.addressService = addressService;
        this.addressPart1Service = addressPart1Service;
        this.addressPart2Service = addressPart2Service;
        this.orderService = orderService;
        this.couponService = couponService;

        log.info("UserController initialized");
        this.aliOssUtil = aliOssUtil;
    }

    /**
     * 注册用户
     *
     * @param userForRegister 用户注册信息
     * @return 注册成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/register"})
    public ResponseEntity<Result> registerUser(@RequestBody UserForRegister userForRegister) {
        log.info("user register: {}", userForRegister);

        User user = this.userService.userRegister(userForRegister);

        UserRegisterReturnView view = new UserRegisterReturnView(user);

        return ResponseEntity.ok(Result.success(
                AccountConstant.ACCOUNT_REGISTER_SUCCESS,
                view));
    }

    /**
     * 用户登录
     *
     * @param userForLogin 用户登录信息
     * @return 登录成功返回200，用户信息和包含JWT的成功消息，失败返回401和错误消息
     */
    @Api
    @PostMapping({"/login"})
    public ResponseEntity<Result> loginUser(@RequestBody UserForLogin userForLogin) {

        int count = 0;

        if (AccountUtil.checkEmail(userForLogin.getEmail())) {
            count++;
        }

        if (AccountUtil.checkPhone(userForLogin.getPhone())) {
            count++;
        }

        if (AccountUtil.checkName(userForLogin.getName())) {
            count++;
        }

        if (userForLogin.getId() != null) {
            count++;
        }

        if (count != 1) {
            throw new InputInvalidException("only one login way is supported in one time");
        }

        log.info("user login: id: {}, name: {}, email: {}, phone: {}"
                , userForLogin.getId(), userForLogin.getName(), userForLogin.getEmail(), userForLogin.getPhone());

        UserLoginView user = this.userService.userLogin(userForLogin);
        if (user != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(JwtClaimsConstant.ID, user.getId());
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
     * @return 更新成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/update"})
    public ResponseEntity<Result> update(@RequestBody UserForUpdate userForUpdate) {
        log.info("user update: {}", userForUpdate.getId());
        aliOssUtil.checkUrlIsAliOss(userForUpdate.getProfileUrl());

        User update = this.userService.userUpdate(userForUpdate);

        return
                ResponseEntity.ok(Result.success(AccountConstant.ACCOUNT_CHANGE_SUCCESS,
                        new UserUpdateView(update)));
    }

    /**
     * @param userForUpdatePassword UserForUpdatePassword
     * @return 更改成功返回200和成功消息，失败返回错误消息
     */
    @Api
    @PostMapping({"/change-password"})
    public ResponseEntity<Result> changePassword(@RequestBody UserForUpdatePassword userForUpdatePassword) {
        log.info("user change password: {}, {}", userForUpdatePassword.getId(), userForUpdatePassword.getEmail());

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
//    @Api
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
     * @return 验证成功返回200和成功消息
     */
    @Api
    @PostMapping({"/verify"})
    public ResponseEntity<Result> verify(@RequestBody UserForVerify userForVerify) {
        int count = 0;

        if (AccountUtil.checkEmail(userForVerify.getEmail())) {
            count++;
        }
        if (AccountUtil.checkPhone(userForVerify.getPhone())) {
            count++;
        }

        if (count != 1) {
            throw new InputInvalidException("only one can be sent captcha to.");
        }

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
    @PostMapping("/item/query")
    public ResponseEntity<Result> fetchItem(@RequestBody ItemSpecification specification) {
        return ResponseEntity.ok(Result.success(itemService.query(specification)));
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

        itemForAdd.getImages().stream()
                .map(ItemForAdd.TargetOf_images::getImageUrl)
                .forEach(
                        aliOssUtil::checkUrlIsAliOss
                );

        return ResponseEntity.ok(Result.success(itemService.add(itemForAdd)));
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
        Objects.requireNonNull(itemForUpdate.getImages()).stream()
                .map(ItemForUpdate.TargetOf_images::getImageUrl)
                .forEach(
                        aliOssUtil::checkUrlIsAliOss
                );
        return ResponseEntity.ok(Result.success(itemService.update(itemForUpdate)));
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

        itemService.delete(itemForDelete);

        return ResponseEntity.ok(Result.success(ItemConstant.ITEM_DELETE_SUCCESS));
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
        Page<CategorySubstance> result = categoryService.query(category);
        return ResponseEntity.ok(Result.success(result));

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
        log.info("Received query request with specification: {}", addressForQuery);

        Page<AddressView> page = addressService.query(addressForQuery);

        log.info("Returning address list with page: {}", page);

        return ResponseEntity.ok(
                Result.success(page)
        );
    }

    @Api
    @PostMapping("/address/add")
    public ResponseEntity<Result> addAddress(@RequestBody AddressCreateInput address) {
        log.info("Received add request with address: {}", address);

        Address result = addressService.add(address);

        log.info("Returning add result: {}", result);

        return ResponseEntity.ok(
                Result.success(AddressConstant.ADDRESS_ADD_SUCCESS, result)
        );
    }

    @Api
    @PostMapping("/address-part1/query")
    public ResponseEntity<Result> getAddressPart1(@RequestBody AddressPart1Specification specification) {
        log.info("Received read request with specification: {}", specification);

        Page<AddressPart1View> page = addressPart1Service.query(specification);

        return ResponseEntity.ok(Result.success(page));
    }

    @Api
    @PostMapping("/address-part2/query")
    public ResponseEntity<Result> getAddressPart2(@RequestBody AddressPart2Specification specification) {
        Page<AddressPart2View> page = addressPart2Service.query(specification);

        return ResponseEntity.ok(Result.success(page));
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

        Order result = orderService.add(orderForAdd);
        return ResponseEntity.ok(
                Result.success(OrderConstant.ORDER_ADD_SUCCESS));
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

        Boolean result = orderService.update(orderForUpdate);

        return ResponseEntity.ok(
                Boolean.TRUE.equals(result) ? Result.success(OrderConstant.ORDER_UPDATE_SUCCESS)
                        : Result.error(OrderConstant.ORDER_UPDATE_FAIL));
    }

    /**
     * 根据指定条件删除订单。
     *
     * @param orderForDelete 删除订单的条件
     * @return 返回删除结果
     */
    @Api
    @PostMapping("/order/delete")
    public ResponseEntity<Result> deleteOrder(@RequestBody OrderForDelete orderForDelete) {
        if (!Objects.equals(orderForDelete.getUserId(), UserContext.getUserId())) {
            throw new NoAuthorityException();
        }

        orderService.delete(orderForDelete);

        return ResponseEntity.ok(
                Result.success(OrderConstant.ORDER_DELETE_SUCCESS));
    }

    /**
     * 获取优惠券
     *
     * @param specification 优惠券规格说明，通过RequestBody接收前端传来的JSON数据。
     * @return 返回一个ResponseEntity对象，其中包含获取优惠券操作的结果。如果操作成功，body中包含具体结果数据。
     */
    @Api
    @PostMapping("/coupon/query")
    public ResponseEntity<Result> getCoupon(@RequestBody CouponSpecification specification) {
        // 调用commonServiceClient的getCoupon方法获取优惠券信息，并将结果封装成成功响应返回
        specification.setUserId(UserContext.getUserId());

        Page<CouponView> page = couponService.query(specification, CouponView.class);

        return ResponseEntity.ok(
                Result.success(page)
        );
    }
}
