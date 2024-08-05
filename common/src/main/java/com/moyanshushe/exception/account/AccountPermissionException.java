package com.moyanshushe.exception.account;

import com.moyanshushe.constant.AccountConstant;
import com.moyanshushe.exception.BaseException;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class AccountPermissionException extends BaseException {
    public AccountPermissionException()
    {
        super(AccountConstant.INSUFFICIENT_AUTHORITY);
    }

    public AccountPermissionException(String message)
    {
        super(message);
    }
}
