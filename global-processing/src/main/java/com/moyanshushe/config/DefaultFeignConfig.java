package com.moyanshushe.config;

import com.moyanshushe.constant.AuthorityConstant;
import com.moyanshushe.utils.UserContext;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class DefaultFeignConfig {
    @Bean
    public System.Logger.Level feignLoggerLevel() {
        return System.Logger.Level.INFO;
    }

    @Bean
    public RequestInterceptor userInfoInterceptor() {
        return template ->
                template.header(
                        AuthorityConstant.USER_AUTHENTICATION_ID,
                        String.valueOf(UserContext.getUserId()));
    }
}
