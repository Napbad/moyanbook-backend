package com.moyanshushe.interceptor;



/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/11/24
    @Description: 

*/

import com.moyanshushe.constant.AuthorityConstant;
import com.moyanshushe.utils.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // 添加 Header
        template.header(AuthorityConstant.USER_AUTHENTICATION_ID, String.valueOf(UserContext.getUserId()));
    }
}
