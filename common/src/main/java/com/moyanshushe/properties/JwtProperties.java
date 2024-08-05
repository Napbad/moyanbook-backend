package com.moyanshushe.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 * Author: Napbad
 * Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "moyanshushe.jwt")
public class JwtProperties {

    /**
     * 管理端生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * 用户端生成jwt令牌相关配置
     */
    private String secretKey;
    private long ttl;
    private String tokenName;

    private Boolean userCheck;
    private Boolean memberCheck;
}
