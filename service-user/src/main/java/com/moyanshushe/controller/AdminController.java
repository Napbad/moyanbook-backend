package com.moyanshushe.controller;

import com.moyanshushe.client.CommonServiceClient;
import com.moyanshushe.constant.AccountConstant;
import com.moyanshushe.constant.JwtClaimsConstant;
import com.moyanshushe.constant.VerifyConstant;
import com.moyanshushe.exception.common.InputInvalidException;
import com.moyanshushe.model.AdminLoginResult;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.admin.*;
import com.moyanshushe.model.entity.Admin;
import com.moyanshushe.properties.JwtProperties;
import com.moyanshushe.service.AdminService;
import com.moyanshushe.utils.JwtUtil;
import org.babyfish.jimmer.client.meta.Api;
import org.babyfish.jimmer.sql.ast.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 管理员，负责处理管理员账户相关的HTTP请求
 */
@Api

@RestController
@RequestMapping({"/admin"})
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService;
    private final JwtProperties jwtProperties;

    // 构造函数：初始化用户服务和JWT属性
    public AdminController(AdminService adminService,
                           JwtProperties jwtProperties,
                           CommonServiceClient commonServiceClient) {
        this.adminService = adminService;
        this.jwtProperties = jwtProperties;

        log.info("AdminController initialized");
    }

    /**
     * 注册用户
     *
     * @param adminForRegister 用户注册信息
     *                         AdminForRegister {
     *                         id
     *                         name
     *                         email
     *                         password
     *                         captcha: String
     *                         address {
     *                         id
     *                         }
     *                         status
     *                         type
     *                         }
     * @return 注册成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/register"})
    public ResponseEntity<Result> registerAdmin(@RequestBody AdminForRegister adminForRegister) {
        log.info("admin register: {}", adminForRegister);

        boolean success = this.adminService.adminRegister(adminForRegister);

        return success ?
                ResponseEntity.ok(Result.success(AccountConstant.ACCOUNT_REGISTER_SUCCESS))
                : ResponseEntity.badRequest().body(Result.error(AccountConstant.ACCOUNT_REGISTER_FAILURE));
    }

    /**
     * 用户登录
     *
     * @param adminForLogin 用户登录信息
     *                      AdminForLogin {
     *                      id  ---  选填其一
     *                      name  ---
     *                      phone ---
     *                      email ---
     *                      password  --- 必填
     *                      }
     * @return 登录成功返回200和包含JWT的成功消息，失败返回401和错误消息
     */
    @Api
    @PostMapping({"/login"})
    public ResponseEntity<Result> loginAdmin(@RequestBody AdminForLogin adminForLogin) {

        log.info("admin login: name: {}, email: {}, phone: {}"
                , adminForLogin.getName(), adminForLogin.getEmail(), adminForLogin.getPhone());

        Admin admin = this.adminService.adminLogin(adminForLogin);
        if (admin != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(JwtClaimsConstant.ID, admin.id());
            String jwt = JwtUtil.createJWT(this.jwtProperties.getSecretKey(), this.jwtProperties.getTtl(), map);

            return ResponseEntity.ok(Result.success(new AdminLoginResult(
                    admin, jwt
            )));

        } else {
            return ResponseEntity.status(401).body(Result.error(AccountConstant.ACCOUNT_LOGIN_FAILURE));
        }
    }

    /**
     * 更新用户信息
     *
     * @param adminForUpdate 用户更新信息
     *                       <p>
     *                       AdminForUpdate {
     *                       id    必填
     *                       name
     *                       gender
     *                       age
     *                       profileUrl
     *                       address {
     *                       id
     *                       }
     *                       }
     * @return 更新成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/update"})
    public ResponseEntity<Result> update(@RequestBody AdminForUpdate adminForUpdate) {
        log.info("admin update: {}", adminForUpdate.getId());

        boolean isChanged = this.adminService.adminUpdate(adminForUpdate);

        return isChanged
                ? ResponseEntity.ok(Result.success(AccountConstant.ACCOUNT_CHANGE_SUCCESS))
                : ResponseEntity.badRequest().body(Result.error(AccountConstant.ACCOUNT_CHANGE_FAILURE));
    }


    /**
     * @param adminForUpdatePassword AdminForUpdatePassword {
     *                               id 必填
     *                               password 必填
     *                               email 必填
     *                               newPassword: String
     *                               captcha: String
     *                               }
     * @return 结果消息
     */
    @Api
    @PostMapping({"/change-password"})
    public ResponseEntity<Result> changePassword(@RequestBody AdminForUpdatePassword adminForUpdatePassword) {
        log.info("admin change password: {}", adminForUpdatePassword.getId());

        boolean isUpdated = this.adminService.updatePassword(adminForUpdatePassword);

        return isUpdated
                ? ResponseEntity.ok(Result.success(AccountConstant.ACCOUNT_CHANGE_SUCCESS))
                : ResponseEntity.badRequest().body(Result.error(AccountConstant.ACCOUNT_CHANGE_FAILURE));
    }

    /**
     * 绑定用户信息
     *
     * @param adminForBinding 用户绑定信息

     *                        id  必填
     *                        phone  必填  -- 选一个
     *                        email  必填  --
     *                        captcha: String  必填
     * @return 绑定成功返回200和成功消息，失败返回400和错误消息
     */
    @Api
    @PostMapping({"/bind"})
    public ResponseEntity<Result> bind(@RequestBody  AdminForBinding adminForBinding) {

        log.info("admin: {} binding", adminForBinding.getId());

        boolean bindSuccess = this.adminService.bind(adminForBinding);

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
        log.info("admin: {} logout", id);
        return ResponseEntity.ok().body(Result.success(AccountConstant.ACCOUNT_LOGOUT_SUCCESS));
    }

    /**
     * 验证用户信息
     *
     * @param adminForVerify 用户验证信息
     *
     *                       phone and email 需有一个非空
     *
     * @return 验证成功返回200和成功消息
     */
    @Api
    @PostMapping({"/verify"})
    public ResponseEntity<Result> verify(@RequestBody AdminForVerify adminForVerify) {
        log.info("admin verify: {}", adminForVerify);

        if (adminForVerify.getEmail() == null &&
        adminForVerify.getPhone() == null) {
            throw new InputInvalidException();
        }

        this.adminService.adminVerify(adminForVerify);
        return ResponseEntity.ok().body(Result.success(VerifyConstant.VERIFY_CODE_SENT));
    }


}
