package com.moyanshushe.exception;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class InitFailedException extends BaseException {
    public InitFailedException() {
        super("初始化失败");
    }

    public InitFailedException(String msg) {
        super(msg);
    }
}
