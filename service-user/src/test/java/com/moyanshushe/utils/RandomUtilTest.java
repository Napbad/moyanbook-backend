package com.moyanshushe.utils;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class RandomUtilTest {
    @Test
    void testRandomPathUrl() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String string = RandomUtil.randomUrlPath(1, LocalDate.now());

        System.out.println(string);
    }

    @Test
    void testRandomString() {
        String string = RandomUtil.randomString();
        System.out.println(string);
    }
}
