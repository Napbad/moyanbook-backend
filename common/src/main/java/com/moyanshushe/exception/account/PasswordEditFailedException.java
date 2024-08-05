package com.moyanshushe.exception.account;

import com.moyanshushe.exception.BaseException;

/*
 * Author: Napbad
 * Version: 1.0
 * 密码修改失败异常
 */
public class PasswordEditFailedException extends BaseException {

    public PasswordEditFailedException(String msg){
        super(msg);
    }

}
