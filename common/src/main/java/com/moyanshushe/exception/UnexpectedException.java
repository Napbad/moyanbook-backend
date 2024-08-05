package com.moyanshushe.exception;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class UnexpectedException extends RuntimeException {
    static final String msg = "Unexpected Exception";

    public UnexpectedException() {
        super(msg);
    }

    public UnexpectedException(String msg){
        super(msg);
    }
}
