package com.moyanshushe.model.cnoverter;

import com.moyanshushe.constant.AccountConstant;
import org.babyfish.jimmer.jackson.Converter;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class UserEmailConverter implements Converter<String, String> {

    @Override
    public String output(String value) {
        return AccountConstant.ACCOUNT_EMAIL_ENCODE + value.substring(value.lastIndexOf("@"));
    }


}
