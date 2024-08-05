package com.moyanshushe.exception.account;

import com.moyanshushe.constant.AccountConstant;
import com.moyanshushe.exception.BaseException;

/*
 * Author: Napbad
 * Version: 1.0
 * 密码错误异常
 */
public class PasswordErrorException extends BaseException {

    public PasswordErrorException() {
        super(AccountConstant.ACCOUNT_PASSWORD_WRONG);
    }

    public PasswordErrorException(String msg) {
        super(msg);
    }

}
