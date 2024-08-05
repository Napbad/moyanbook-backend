package com.moyanshushe.exception.account;

import com.moyanshushe.constant.AccountConstant;
import com.moyanshushe.exception.BaseException;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class AccountExistsException extends BaseException {
    public AccountExistsException()
    {
        super(AccountConstant.ACCOUNT_EXISTS);
    }

    public AccountExistsException(String message)
    {
        super(message);
    }
}
