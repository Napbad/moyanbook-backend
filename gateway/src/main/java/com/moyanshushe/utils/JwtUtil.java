package com.moyanshushe.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/*
 * Author: Napbad
 * Version: 1.0
 */

/**
 * JWT工具类，提供JWT的创建和解析功能。
 */
public class JwtUtil {

    /**
     * 创建JWT Token。
     *
     * @param secretKey JWT的秘钥，用于签名和验证。
     * @param ttlMillis JWT的有效期时间（毫秒）。
     * @param claims 要设置到JWT中的声明信息。
     * @return 生成的JWT Token字符串。
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 设置签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 计算JWT的过期时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // 构建JWT
        JwtBuilder builder = Jwts.builder()
                // 先设置自定义声明，避免覆盖标准声明
                .setClaims(claims)
                // 设置签名算法和秘钥
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                // 设置过期时间
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * 解析JWT Token。
     *
     * @param secretKey JWT的秘钥，用于签名验证。
     * @param token 要解析的JWT Token字符串。
     * @return 解析后的JWT声明信息。
     */
    public static Claims parseJWT(String secretKey, String token) {
        // 解析JWT
        return Jwts.parser()
                // 设置签名秘钥
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                // 解析JWT Token
                .parseClaimsJws(token).getBody();
    }

    private JwtUtil() {}
}
