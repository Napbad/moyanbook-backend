package com.moyanshushe.model.cnoverter;

import org.babyfish.jimmer.jackson.Converter;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class UserPhoneConverter implements Converter<String, String> {
    @Override
    public String output(String value) {
        return value.substring(0, 3) + "****" + value.substring(7);
    }
}
