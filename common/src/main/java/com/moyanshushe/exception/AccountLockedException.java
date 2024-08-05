package com.moyanshushe.exception;

/*
 * Author: Napbad
 * Version: 1.0
 * 账号被锁定异常
 */
public class AccountLockedException extends BaseException {

    public AccountLockedException() {
    }

    public AccountLockedException(String msg) {
        super(msg);
    }

}
