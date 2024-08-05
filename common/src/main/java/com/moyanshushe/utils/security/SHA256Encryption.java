package com.moyanshushe.utils.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * Author: Napbad
 * Version: 1.0
 */

public class SHA256Encryption {
    private static StringBuffer sb = new StringBuffer("HACOJ");

    public static String getSHA256(String input) {
        try {
            // 获取MessageDigest实例，并指定SHA-256算法
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 计算哈希值
            byte[] hash = digest.digest((input + sb.toString()).getBytes(StandardCharsets.UTF_8));

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}