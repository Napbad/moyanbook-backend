package com.moyanshushe.exception.category;

import com.moyanshushe.constant.CategoryConstant;
import com.moyanshushe.exception.BaseException;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class CategoryExistsException extends BaseException {

    public CategoryExistsException() {
        super(CategoryConstant.CATEGORY_EXISTS);
    }

    public CategoryExistsException(String msg) {
        super(msg);
    }
}
