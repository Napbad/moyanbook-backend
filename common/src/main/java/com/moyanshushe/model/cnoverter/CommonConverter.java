package com.moyanshushe.model.cnoverter;

import com.moyanshushe.constant.AccountConstant;
import org.babyfish.jimmer.jackson.Converter;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class CommonConverter  implements Converter<String, String> {
    @Override
    public String output(String value) {
        return AccountConstant.ACCOUNT_PASSWORD_ENCODE;
    }
}
