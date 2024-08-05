package com.moyanshushe.service.impl;

import com.moyanshushe.constant.AccountConstant;
import com.moyanshushe.constant.RedisConstant;
import com.moyanshushe.constant.VerifyConstant;
import com.moyanshushe.exception.UnexpectedException;
import com.moyanshushe.exception.account.*;
import com.moyanshushe.exception.common.CaptchaErrorException;
import com.moyanshushe.exception.common.DBException;
import com.moyanshushe.exception.common.InputInvalidException;
import com.moyanshushe.mapper.AdminMapper;
import com.moyanshushe.model.dto.admin.*;
import com.moyanshushe.model.entity.Admin;
import com.moyanshushe.model.entity.AdminFetcher;
import com.moyanshushe.model.entity.Fetchers;
import com.moyanshushe.service.AdminService;
import com.moyanshushe.utils.MailUtil;
import com.moyanshushe.utils.UserContext;
import com.moyanshushe.utils.security.AccountUtil;
import com.moyanshushe.utils.security.SHA256Encryption;
import com.moyanshushe.utils.verify.CaptchaGenerator;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 用户服务实现类
 */
@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final AdminMapper adminMapper;
    private final MailUtil mailUtil;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 构造函数
     *
     * @param adminMapper 用户mapper接口
     * @param mailUtil 邮件工具类
     * @param stringRedisTemplate Redis字符串模板
     */
    public AdminServiceImpl(AdminMapper adminMapper, MailUtil mailUtil, StringRedisTemplate stringRedisTemplate) {
        this.adminMapper = adminMapper;
        this.mailUtil = mailUtil;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 用户注册
     *
     * @param admin 用户注册信息
     * @return 注册成功返回用户ID，失败返回-1
     */

    // TODO 手机注册
    @Transactional(rollbackFor = {Exception.class})
    public Boolean adminRegister(AdminForRegister admin) {
        // 验证码校验
        String captcha = this.stringRedisTemplate.opsForValue().get(RedisConstant.ADMIN_CAPTCHA + admin.getEmail());

        if (captcha == null || !captcha.equals(admin.getCaptcha())) {
            throw new CaptchaErrorException();
        }

        // 用户名和密码校验
        if (!AccountUtil.checkName(admin.getName())) {
            log.warn("用户名格式错误");
            throw new AccountNameErrorException();
        } else if (!AccountUtil.checkPassword(admin.getPassword())) {
            log.warn("密码格式错误");
            throw new AccountPasswordErrorException();
        }

        // 判断用户名是否已存在
        long count = adminMapper.findByName(admin.getName(), Fetchers.ADMIN_FETCHER).size();

        if (count > 0L) {
            throw new AccountExistsException();
        }

        if (!adminMapper.findByEmail(admin.getEmail(), Fetchers.ADMIN_FETCHER).isEmpty()) {
            throw new AccountExistsException(AccountConstant.ACCOUNT_EMAIL_EXISTS);
        }

        if (admin.getAddress() == null) {
            admin.setStatus((short) 0);
        }

        // 密码加密
        admin.setPassword(SHA256Encryption.getSHA256(admin.getPassword()));
        // 用户注册
        SimpleSaveResult<Admin> result = this.adminMapper.addAdmin(admin);
        if (result.getAffectedRowCount(Admin.class) != 1) {
            throw new UnexpectedException();
        } else {

            // 注册成功，清理验证码，记录日志
            long id = result.getModifiedEntity().id();
            this.stringRedisTemplate.delete(RedisConstant.ADMIN_CAPTCHA + admin.getEmail());

            log.info("admin registered: {}", id);

            return true;
        }
    }

    /**
     * 用户登录
     *
     * @param adminForLogin 用户登录信息
     * @return 登录成功返回用户ID，失败返回-1
     */
    public Admin adminLogin(AdminForLogin adminForLogin) {

        // 密码格式校验
        if (!AccountUtil.checkPassword(adminForLogin.getPassword())) {
            throw new AccountPasswordErrorException();
        }

        // 通过用户名、手机号或邮箱进行登录验证
        Optional<Admin> adminOptional;
        AdminFetcher fetcher = Fetchers.ADMIN_FETCHER
                .name(true)
                .age(true)
                .gender(true)
                .email(true)
                .phone(true)
                .status(true)
                .profileUrl(true)
                .address(true)
                .password(true);

        if (AccountUtil.checkName(adminForLogin.getName())) {

            adminOptional = this.adminMapper.findByName(
                    adminForLogin.getName(), fetcher).stream().findFirst();

        } else if (adminForLogin.getPhone() != null && AccountUtil.checkPhone(adminForLogin.getPhone())) {

            adminOptional = this.adminMapper.findByPhone(
                    adminForLogin.getPhone(), fetcher).stream().findFirst();

        } else if (adminForLogin.getEmail() != null && AccountUtil.checkEmail(adminForLogin.getEmail())) {

            adminOptional = this.adminMapper.findByEmail(
                    adminForLogin.getEmail(), fetcher).stream().findFirst();
        } else {
            throw new InputInvalidException();
        }

        if (adminOptional.isEmpty()) {
            throw new AccountNotFoundException();

        } else {

            // 密码验证
            String password = adminForLogin.getPassword();
            String passwordDigested = SHA256Encryption.getSHA256(password);

            if (passwordDigested.equals(adminOptional.get().password())) {

                // 登录成功，更新登录时间，记录日志
                this.adminMapper.updateLoginTime(adminOptional.get().id(), LocalDate.now());

                log.info("admin login: {}", adminOptional.get().id());

                return adminOptional.get();
            } else {
                return null;
            }
        }

    }

    /**
     * 用户信息更新
     *
     * @param adminForUpdate 用户更新信息
     * @return 更新成功返回true，失败返回false
     */
    @Transactional(rollbackFor = {Exception.class})
    public boolean adminUpdate(AdminForUpdate adminForUpdate) {

        // 校验用户名
        if (!AccountUtil.checkName(adminForUpdate.getName())) {
            return false;
        } else if (adminForUpdate.getId() == 0L) {
            return false;
        } else {

            Integer adminId = UserContext.getUserId();

            if (adminId.equals(adminForUpdate.getId())) {

                // 当前登录用户ID与要更新的用户ID匹配校验
                SimpleSaveResult<Admin> result = this.adminMapper.update(adminForUpdate);
                // 日志记录
                log.info("update admin: {}", adminId);
                log.info("details: {} -> {}", result.getOriginalEntity(), result.getModifiedEntity());

                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 邮箱验证码发送与验证
     *
     * @param adminForVerify 验证用户信息
     */
    public void adminVerify(AdminForVerify adminForVerify) {

        // 生成验证码并设置过期时间
        String captcha = CaptchaGenerator.generateCaptcha();
        this.stringRedisTemplate.opsForValue().set(RedisConstant.ADMIN_CAPTCHA + adminForVerify.getEmail(), captcha, 20L, TimeUnit.MINUTES);
        // 发送验证码到邮箱
        this.mailUtil.sendCaptcha(captcha, adminForVerify.getEmail());

        // 记录日志
        log.info("send captcha to: {}, captcha: {}", adminForVerify.getEmail(), captcha);
    }

    /**
     * 绑定邮箱或手机号
     *
     * @param adminForBinding 绑定用户信息
     * @return 绑定成功返回true，失败返回false
     */
    public boolean bind(AdminForBinding adminForBinding) {

        // 验证码校验
        String captcha = this.stringRedisTemplate.opsForValue().get(RedisConstant.ADMIN_CAPTCHA + adminForBinding.getEmail());
        if (captcha != null && !captcha.isEmpty() && captcha.equals(adminForBinding.getCaptcha())) {
            // 绑定邮箱
            if (adminForBinding.getEmail() != null && AccountUtil.checkEmail(adminForBinding.getEmail())) {
                this.adminMapper.update(adminForBinding.toEntity());
                // 记录日志
                log.info("admin: {} bind email: {}", adminForBinding.getId(), adminForBinding.getEmail());
                this.stringRedisTemplate.delete(RedisConstant.ADMIN_CAPTCHA + adminForBinding.getEmail());
            }
            // 绑定手机号
            if (adminForBinding.getPhone() != null && AccountUtil.checkPhone(adminForBinding.getPhone())) {
                this.adminMapper.update(adminForBinding.toEntity());
                // 记录日志
                log.info("admin: {} bind phone: {}", adminForBinding.getId(), adminForBinding.getPhone());
                this.stringRedisTemplate.delete(RedisConstant.ADMIN_CAPTCHA + adminForBinding.getPhone());
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updatePassword(AdminForUpdatePassword adminForUpdatePassword) {
        Integer id = UserContext.getUserId();
        if (!id.equals(adminForUpdatePassword.getId())) {
            throw new UserNotLoginException();
        }


        String captcha = stringRedisTemplate.opsForValue().get(RedisConstant.ADMIN_CAPTCHA + adminForUpdatePassword.getEmail());
        if (captcha == null){
            throw new CaptchaErrorException(VerifyConstant.VERIFY_CODE_EXPIRED);
        } else if (!captcha.equals(adminForUpdatePassword.getCaptcha())) {
            throw new CaptchaErrorException(VerifyConstant.CAPTCHA_ERROR);
        }

        if (!AccountUtil.checkPassword(adminForUpdatePassword.getPassword())) {
            throw new AccountPasswordErrorException();
        }
        Collection<Admin> admins = adminMapper.findById(adminForUpdatePassword.getId(), Fetchers.ADMIN_FETCHER.password());
        if (admins != null && admins.isEmpty()) {
            throw new AccountNotFoundException();
        } else if (admins != null && admins.size() > 1){
            throw new DBException();
        }



        if (AccountUtil.checkPassword(adminForUpdatePassword.getPassword())) {
            // 密码加密
            adminForUpdatePassword.setPassword(SHA256Encryption.getSHA256(adminForUpdatePassword.getPassword()));
            // 更新密码
            this.adminMapper.update(adminForUpdatePassword.toEntity());
            // 记录日志
            log.info("admin: {} update password", adminForUpdatePassword.getId());
            return true;
        }

        return false;
    }
}
