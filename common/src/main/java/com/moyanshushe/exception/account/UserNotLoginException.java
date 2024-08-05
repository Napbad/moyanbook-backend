package com.moyanshushe.exception.account;

import com.moyanshushe.constant.AccountConstant;
import com.moyanshushe.exception.BaseException;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class UserNotLoginException extends BaseException {

    public UserNotLoginException() {
        super(AccountConstant.ACCOUNT_NOT_LOGIN);
    }

    public UserNotLoginException(String msg) {
        super(msg);
    }

}
