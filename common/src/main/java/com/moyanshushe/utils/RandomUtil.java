package com.moyanshushe.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class RandomUtil {
    public static String randomUrlPath(int id, LocalDate localDate) {
        // 生成一个MD5加密计算摘要
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {
        }
        // 计算md5函数
        StringBuilder builder = new StringBuilder();
        assert md != null;
        md.update(builder.append(id).append(localDate.toString()).toString().getBytes(StandardCharsets.UTF_8));
        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
//        return new BigInteger(1, md.digest()).toString(16);
        return Arrays.toString(md.digest());
    }

    public static String randomString() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString().replace("-", "");
    }
}
