package com.moyanshushe.exception.label;

import com.moyanshushe.constant.LabelConstant;
import com.moyanshushe.exception.BaseException;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class LabelExistsException extends BaseException {

    public LabelExistsException() {
        super(LabelConstant.LABEL_EXISTS);
    }

    public LabelExistsException(String msg) {
        super(msg);
    }
}
