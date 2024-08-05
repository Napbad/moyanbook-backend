package com.moyanshushe.exception.work;

import com.moyanshushe.constant.WorkConstant;
import com.moyanshushe.exception.BaseException;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class ConfirmCodeInvaildException extends BaseException {

    public ConfirmCodeInvaildException() {
        super(WorkConstant.MEMBER_CONFIRM_CODE_INVALID);
    }
    public ConfirmCodeInvaildException(String message) {
        super(message);
    }
}
