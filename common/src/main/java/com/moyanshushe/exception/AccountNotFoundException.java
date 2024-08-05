package com.moyanshushe.exception;

/*
 * Author: Napbad
 * Version: 1.0
 * 账号不存在异常
 */
public class AccountNotFoundException extends BaseException{

    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String msg) {
        super(msg);
    }
}
