package com.moyanshushe.exception.account;

import com.moyanshushe.exception.BaseException;

/*
 * Author: Napbad
 * Version: 1.0
 * 登录失败
 */
public class LoginFailedException extends BaseException {
    public LoginFailedException(String msg){
        super(msg);
    }
}
