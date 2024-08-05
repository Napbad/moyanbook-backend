package com.moyanshushe.exception.common;

import com.moyanshushe.constant.CommonConstant;
import com.moyanshushe.exception.BaseException;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class DBException extends BaseException {
    public DBException() {
        super(CommonConstant.DB_EXCEPTION);
    }

    public DBException(String msg) {
        super(msg);
    }
}
