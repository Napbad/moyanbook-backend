package com.moyanshushe.exception;

import com.moyanshushe.constant.AuthorityConstant;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class NoAuthorityException extends BaseException{
    public NoAuthorityException() {
        super(AuthorityConstant.NO_AUTHORIZATION);
    }

    public NoAuthorityException(String message) {
        super( message);
    }
}
