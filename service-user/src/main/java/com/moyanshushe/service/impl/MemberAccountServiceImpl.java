package com.moyanshushe.service.impl;

import com.moyanshushe.constant.AccountConstant;
import com.moyanshushe.constant.RedisConstant;
import com.moyanshushe.exception.account.AccountExistsException;
import com.moyanshushe.exception.account.AccountNameErrorException;
import com.moyanshushe.exception.account.AccountPasswordErrorException;
import com.moyanshushe.model.dto.member.*;
import com.moyanshushe.model.entity.Fetchers;
import com.moyanshushe.model.entity.Member;
import com.moyanshushe.model.entity.MemberTable;
import com.moyanshushe.service.MemberAccountService;
import com.moyanshushe.utils.MailUtil;
import com.moyanshushe.utils.security.AccountUtil;
import com.moyanshushe.utils.security.SHA256Encryption;
import com.moyanshushe.utils.verify.CaptchaGenerator;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;


/*
 * Author: Napbad
 * Version: 1.0
 */

@Slf4j
@Service
public class MemberAccountServiceImpl implements MemberAccountService {

    private final MailUtil mailUtil;
    private final StringRedisTemplate stringRedisTemplate;
    private final JSqlClient jsqlClient;
    private final MemberTable table;

    public MemberAccountServiceImpl(MailUtil mailUtil, StringRedisTemplate stringRedisTemplate, JSqlClient jsqlClient) {
        this.mailUtil = mailUtil;
        this.stringRedisTemplate = stringRedisTemplate;
        this.jsqlClient = jsqlClient;
        this.table = MemberTable.$;
    }

    @NotNull
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean memberRegister(MemberForRegister member) {
        String captchaGet = member.getCaptcha();

        String captcha = stringRedisTemplate.opsForValue().
                get(RedisConstant.MEMBER_CAPTCHA + member.getEmail());

        if (captcha == null || !captcha.equals(captchaGet)) {
            return false;
        }

        // 用户名和密码校验
        if (!AccountUtil.checkName(member.getName())) {
            log.error("用户名格式错误");
            throw new AccountNameErrorException();
        } else if (!AccountUtil.checkPassword(member.getPassword())) {
            log.info("密码格式错误");
            throw new AccountPasswordErrorException();
        }

        // 检验是否存在已有注册过的用户名，手机，邮箱
        if (!jsqlClient.createQuery(table)
                .where(
                        table.name().eq(member.getName())
                ).select(
                        table.name()
                ).execute().isEmpty()) {
            log.info("用户名已存在");
            throw new AccountExistsException(AccountConstant.ACCOUNT_NAME_EXISTS);
        }

        if (!jsqlClient.createQuery(table)
                .where(
                        table.email().eq(member.getEmail())
                ).select(table).execute().isEmpty()) {
            log.info("邮箱已存在");
            throw new AccountExistsException(AccountConstant.ACCOUNT_EMAIL_EXISTS);
        }

        if (!jsqlClient.createQuery(table)
                .where(
                        table.phone().eq(member.getPhone())
                ).select(table).execute().isEmpty()) {
            log.info("手机号已存在");
            throw new AccountExistsException(AccountConstant.ACCOUNT_PHONE_EXISTS);
        }

        member.setPassword(SHA256Encryption.getSHA256(member.getPassword()));

        SimpleSaveResult<Member> result = jsqlClient.save(member);
        log.info("member registered: {}", result.getModifiedEntity().id());

        return true;
    }

    @Override
    public Member memberLogin(MemberForLogin member) {

        // 密码格式校验
        if (!AccountUtil.checkPassword(member.getPassword())) {
            log.info("密码格式错误");
            throw new AccountPasswordErrorException();
        }

        if (!AccountUtil.checkEmail(member.getEmail())) {
            log.info("邮箱格式错误");
            throw new AccountNameErrorException();
        }
        List<Member> memberList = jsqlClient.createQuery(table)
                .where(
                        table.email().eq(member.getEmail()),
                        table.password().eq(SHA256Encryption.getSHA256(member.getPassword()))
                ).select(table.fetch(
                        Fetchers.MEMBER_FETCHER
                                .email(true)
                                .phone(true)
                                .address(true)
//                                .responsibilityArea(true)
                                .name(true)
                                .gender(true)
                                .status(true)
                                .profileUrl(true)
                )).execute();
        if (memberList.isEmpty()) {
            log.info("用户名或密码错误");
            throw new AccountPasswordErrorException();
        }

        return memberList.getFirst();
    }

    @NotNull
    @Override
    public Boolean memberUpdate(MemberForUpdate member) {

        if (!AccountUtil.checkName(member.getName())) {
            log.info("用户名格式错误");
            throw new AccountNameErrorException();
        }

//        if (member.getId() != THREAD_LOCAL_MEMBER_ID.get()) {
//            log.info("修改权限不足");
//            throw new AccountPermissionException();
//        }

        jsqlClient.update(member);

        return true;
    }


    /**
     * 邮箱验证码发送与验证
     *
     * @param memberForVerify 验证用户信息
     */
    // TODO 手机验证
    public void memberVerify(MemberForVerify memberForVerify) {

        // 生成验证码并设置过期时间
        String captcha = CaptchaGenerator.generateCaptcha();
        this.stringRedisTemplate.opsForValue().set(RedisConstant.USER_CAPTCHA + memberForVerify.getEmail(), captcha, 20L, TimeUnit.MINUTES);
        // 发送验证码到邮箱
        this.mailUtil.sendCaptcha(captcha, memberForVerify.getEmail());

        // 记录日志
        log.info("send captcha to: {}, captcha: {}", memberForVerify.getEmail(), captcha);
    }

    @Override
    public boolean updatePassword(MemberForUpdatePassword memberForUpdatePassword) {

        // 获取当前用户
        Member currentMember = jsqlClient.createQuery(table)
                .where(
                        table.id().eq(memberForUpdatePassword.getId())
                )
                .select(table.fetch(
                        Fetchers.MEMBER_FETCHER
                                .password(true)
                ))
                .execute()
                .getFirst();

        if (currentMember == null) {
            log.error("User not found with id: {}", memberForUpdatePassword.getId());
            throw new AccountExistsException(AccountConstant.ACCOUNT_NOT_FOUND);
        }

        // 验证旧密码
        if (!SHA256Encryption.getSHA256(memberForUpdatePassword.getPassword()).equals(currentMember.password())) {
            log.info("Incorrect old password.");
            throw new AccountPasswordErrorException();
        }

        // 验证新密码
        if (!AccountUtil.checkPassword(memberForUpdatePassword.getNewPassword())) {
            log.info("New password format error.");
            throw new AccountPasswordErrorException();
        }

        // 如果新旧密码相同，则不允许更新
        if (SHA256Encryption.getSHA256(memberForUpdatePassword.getNewPassword()).equals(currentMember.password())) {
            log.info("New password is the same as the old one.");
            throw new AccountPasswordErrorException();
        }

        // 加密新密码
        String encryptedPassword = SHA256Encryption.getSHA256(memberForUpdatePassword.getNewPassword());
        memberForUpdatePassword.setPassword(encryptedPassword);

        // 更新密码
        SimpleSaveResult<MemberForUpdatePassword> result =
                jsqlClient.update(memberForUpdatePassword);

        log.info("Password updated: {}", result.getModifiedEntity().getId());

        return true;
    }

    @Override
    public boolean bind(MemberForBinding memberForBinding) {

        String captcha = memberForBinding.getCaptcha();
        if (captcha.equals(
                stringRedisTemplate.opsForValue()
                        .get(RedisConstant.USER_CAPTCHA + memberForBinding.getEmail()))) {
            return false;
        }

        Integer execute = jsqlClient.createUpdate(table)
                .where(table.id().eq(memberForBinding.getId()))
                .set(table.email(), memberForBinding.getEmail())
                .execute();

        return execute > 0;
    }
}
