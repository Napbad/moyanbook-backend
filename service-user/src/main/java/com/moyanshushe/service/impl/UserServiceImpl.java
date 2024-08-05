package com.moyanshushe.service.impl;

import com.moyanshushe.constant.AccountConstant;
import com.moyanshushe.constant.RedisConstant;
import com.moyanshushe.constant.VerifyConstant;
import com.moyanshushe.exception.UnexpectedException;
import com.moyanshushe.exception.account.*;
import com.moyanshushe.exception.common.CaptchaErrorException;
import com.moyanshushe.exception.common.DBException;
import com.moyanshushe.exception.common.InputInvalidException;
import com.moyanshushe.mapper.UserMapper;
import com.moyanshushe.model.dto.user.*;
import com.moyanshushe.model.entity.Fetchers;
import com.moyanshushe.model.entity.User;
import com.moyanshushe.model.entity.UserFetcher;
import com.moyanshushe.service.UserService;
import com.moyanshushe.utils.UserContext;
import com.moyanshushe.utils.security.AccountUtil;
import com.moyanshushe.utils.security.SHA256Encryption;
import com.moyanshushe.utils.verify.CaptchaGenerator;
import com.moyanshushe.utils.MailUtil;
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
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;
    private final MailUtil mailUtil;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 构造函数
     *
     * @param userMapper 用户mapper接口
     * @param mailUtil 邮件工具类
     * @param stringRedisTemplate Redis字符串模板
     */
    public UserServiceImpl(UserMapper userMapper, MailUtil mailUtil, StringRedisTemplate stringRedisTemplate) {
        this.userMapper = userMapper;
        this.mailUtil = mailUtil;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 用户注册
     *
     * @param user 用户注册信息
     * @return 注册成功返回用户ID，失败返回-1
     */

    // TODO 手机注册
    @Transactional(rollbackFor = {Exception.class})
    public Boolean userRegister(UserForRegister user) {
        // 验证码校验
        String captcha = this.stringRedisTemplate.opsForValue().get(RedisConstant.USER_CAPTCHA + user.getEmail());

        if (captcha == null || !captcha.equals(user.getCaptcha())) {
            throw new CaptchaErrorException();
        }

        // 用户名和密码校验
        if (!AccountUtil.checkName(user.getName())) {
            log.warn("用户名格式错误");
            throw new AccountNameErrorException();
        } else if (!AccountUtil.checkPassword(user.getPassword())) {
            log.warn("密码格式错误");
            throw new AccountPasswordErrorException();
        }

        // 判断用户名是否已存在
        long count = userMapper.findByName(user.getName(), Fetchers.USER_FETCHER).size();

        if (count > 0L) {
            throw new AccountExistsException();
        }

        if (!userMapper.findByEmail(user.getEmail(), Fetchers.USER_FETCHER).isEmpty()) {
            throw new AccountExistsException(AccountConstant.ACCOUNT_EMAIL_EXISTS);
        }

        if (user.getAddress() == null) {
            user.setStatus((short) 0);
        }

        // 密码加密
        user.setPassword(SHA256Encryption.getSHA256(user.getPassword()));
        // 用户注册
        SimpleSaveResult<User> result = this.userMapper.addUser(user);
        if (result.getAffectedRowCount(User.class) != 1) {
            throw new UnexpectedException();
        } else {

            // 注册成功，清理验证码，记录日志
            long id = result.getModifiedEntity().id();
            this.stringRedisTemplate.delete(RedisConstant.USER_CAPTCHA + user.getEmail());

            log.info("user registered: {}", id);

            return true;
        }
    }

    /**
     * 用户登录
     *
     * @param userForLogin 用户登录信息
     * @return 登录成功返回用户ID，失败返回-1
     */
    public User userLogin(UserForLogin userForLogin) {

        // 密码格式校验
        if (!AccountUtil.checkPassword(userForLogin.getPassword())) {
            throw new AccountPasswordErrorException();
        }

        // 通过用户名、手机号或邮箱进行登录验证
        Optional<User> userOptional;
        userForLogin.getName();
        UserFetcher fetcher = Fetchers.USER_FETCHER
                .name(true)
                .age(true)
                .gender(true)
                .email(true)
                .phone(true)
                .status(true)
                .profileUrl(true)
                .address(true)
                .password(true);

        if (AccountUtil.checkName(userForLogin.getName())) {

            userOptional = this.userMapper.findByName(
                    userForLogin.getName(), fetcher).stream().findFirst();

        } else if (userForLogin.getPhone() != null && AccountUtil.checkPhone(userForLogin.getPhone())) {

            userOptional = this.userMapper.findByPhone(
                    userForLogin.getPhone(), fetcher).stream().findFirst();

        } else if (userForLogin.getEmail() != null && AccountUtil.checkEmail(userForLogin.getEmail())) {

            userOptional = this.userMapper.findByEmail(
                    userForLogin.getEmail(), fetcher).stream().findFirst();
        } else {
            throw new InputInvalidException();
        }

        if (userOptional.isEmpty()) {
            throw new AccountNotFoundException();

        } else {

            // 密码验证
            String password = userForLogin.getPassword();
            String passwordDigested = SHA256Encryption.getSHA256(password);

            if (passwordDigested.equals(userOptional.get().password())) {

                // 登录成功，更新登录时间，记录日志
                this.userMapper.updateLoginTime(userOptional.get().id(), LocalDate.now());

                log.info("user login: {}", userOptional.get().id());

                return userOptional.get();
            } else {
                return null;
            }
        }

    }

    /**
     * 用户信息更新
     *
     * @param userForUpdate 用户更新信息
     * @return 更新成功返回true，失败返回false
     */
    @Transactional(rollbackFor = {Exception.class})
    public boolean userUpdate(UserForUpdate userForUpdate) {

        // 校验用户名
        if (!AccountUtil.checkName(userForUpdate.getName())) {
            return false;
        } else if (userForUpdate.getId() == 0L) {
            return false;
        } else {

            Integer userId = UserContext.getUserId();

            if (userId == null) {
                throw new UserNotLoginException();
            }
            else if (userId.equals(userForUpdate.getId())) {

                // 当前登录用户ID与要更新的用户ID匹配校验
                SimpleSaveResult<User> result = this.userMapper.update(userForUpdate);
                // 日志记录
                log.info("update user: {}", userId);
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
     * @param userForVerify 验证用户信息
     */
    public void userVerify(UserForVerify userForVerify) {

        // 生成验证码并设置过期时间
        String captcha = CaptchaGenerator.generateCaptcha();
        this.stringRedisTemplate.opsForValue().set(RedisConstant.USER_CAPTCHA + userForVerify.getEmail(), captcha, 20L, TimeUnit.MINUTES);
        // 发送验证码到邮箱
        this.mailUtil.sendCaptcha(captcha, userForVerify.getEmail());

        // 记录日志
        log.info("send captcha to: {}, captcha: {}", userForVerify.getEmail(), captcha);
    }

    /**
     * 绑定邮箱或手机号
     *
     * @param userForBinding 绑定用户信息
     * @return 绑定成功返回true，失败返回false
     */
    public boolean bind(UserForBinding userForBinding) {

        // 验证码校验
        String captcha = this.stringRedisTemplate.opsForValue().get(RedisConstant.USER_CAPTCHA + userForBinding.getEmail());
        if (captcha != null && !captcha.isEmpty() && captcha.equals(userForBinding.getCaptcha())) {
            // 绑定邮箱
            if (userForBinding.getEmail() != null && AccountUtil.checkEmail(userForBinding.getEmail())) {
                this.userMapper.update(userForBinding.toEntity());
                // 记录日志
                log.info("user: {} bind email: {}", userForBinding.getId(), userForBinding.getEmail());
                this.stringRedisTemplate.delete(RedisConstant.USER_CAPTCHA + userForBinding.getEmail());
            }
            // 绑定手机号
            if (userForBinding.getPhone() != null && AccountUtil.checkPhone(userForBinding.getPhone())) {
                this.userMapper.update(userForBinding.toEntity());
                // 记录日志
                log.info("user: {} bind phone: {}", userForBinding.getId(), userForBinding.getPhone());
                this.stringRedisTemplate.delete(RedisConstant.USER_CAPTCHA + userForBinding.getPhone());
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updatePassword(UserForUpdatePassword userForUpdatePassword) {
        Integer id = UserContext.getUserId();
        if (id == null || !id.equals(userForUpdatePassword.getId())) {
            throw new UserNotLoginException();
        }


        String captcha = stringRedisTemplate.opsForValue().get(RedisConstant.USER_CAPTCHA + userForUpdatePassword.getEmail());
        if (captcha == null){
            throw new CaptchaErrorException(VerifyConstant.VERIFY_CODE_EXPIRED);
        } else if (!captcha.equals(userForUpdatePassword.getCaptcha())) {
            throw new CaptchaErrorException(VerifyConstant.CAPTCHA_ERROR);
        }

        if (!AccountUtil.checkPassword(userForUpdatePassword.getPassword())) {
            throw new AccountPasswordErrorException();
        }
        Collection<User> users = userMapper.findById(userForUpdatePassword.getId(), Fetchers.USER_FETCHER.password());
        if (users != null && users.isEmpty()) {
            throw new AccountNotFoundException();
        } else if (users.size() > 1){
            throw new DBException();
        }



        if (AccountUtil.checkPassword(userForUpdatePassword.getPassword())) {
            // 密码加密
            userForUpdatePassword.setPassword(SHA256Encryption.getSHA256(userForUpdatePassword.getPassword()));
            // 更新密码
            this.userMapper.update(userForUpdatePassword.toEntity());
            // 记录日志
            log.info("user: {} update password", userForUpdatePassword.getId());
            return true;
        }

        return false;
    }
}
