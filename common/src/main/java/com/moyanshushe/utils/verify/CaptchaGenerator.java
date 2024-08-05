package com.moyanshushe.utils.verify;

/*
 * Author: Napbad
 * Version: 1.0
 */
import java.util.Random;

public class CaptchaGenerator {

    private static final int LENGTH = 6;
    private static final char[] CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final Random RANDOM = new Random();

    /**
     * 生成一个六位数字的验证码。
     * @return 生成的六位数字验证码字符串。
     */
    public static String generateCaptcha() {
        return generateCaptcha(LENGTH);
    }

    /**
     * 生成一个指定位位数字的验证码。
     * @return 生成的指定位位数字验证码字符串。
     */
    public static String generateCaptcha(int length) {
        char[] captcha = new char[length];
        for (int i = 0; i < length; i++) {
            captcha[i] = CHARACTERS[RANDOM.nextInt(CHARACTERS.length)];
        }
        return new String(captcha);
    }

    private CaptchaGenerator() {}
}
