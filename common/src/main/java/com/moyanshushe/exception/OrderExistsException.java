package com.moyanshushe.exception;

/*
 * Author: Napbad
 * Version: 1.0
 * 账号被锁定异常
 */
public class OrderExistsException extends BaseException {

    public OrderExistsException() {
        super();
    }

    public OrderExistsException(String msg) {
        super(msg);
    }

}
