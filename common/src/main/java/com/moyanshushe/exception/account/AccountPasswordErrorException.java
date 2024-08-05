package com.moyanshushe.exception.account;

import com.moyanshushe.constant.AccountConstant;
import com.moyanshushe.exception.BaseException;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class AccountPasswordErrorException extends BaseException {
    public AccountPasswordErrorException() {
        super(AccountConstant.ACCOUNT_PASSWORD_WRONG);
    }

    public AccountPasswordErrorException(String message) {
        super(message);
    }
}
