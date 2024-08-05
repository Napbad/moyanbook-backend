package com.moyanshushe.exception.account;

import com.moyanshushe.constant.AccountConstant;
import com.moyanshushe.exception.BaseException;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class AccountNameErrorException extends BaseException {
    public AccountNameErrorException()
    {
        super(AccountConstant.ACCOUNT_NAME_ERROR);
    }

    public AccountNameErrorException(String msg)
    {
        super(msg);
    }
}
