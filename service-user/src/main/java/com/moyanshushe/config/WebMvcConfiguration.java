package com.moyanshushe.config;

import com.moyanshushe.interceptor.UserContextInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final UserContextInterceptor jwtTokenUserInterceptor;

    public WebMvcConfiguration(UserContextInterceptor jwtTokenUserInterceptor) {
        this.jwtTokenUserInterceptor = jwtTokenUserInterceptor;
    }

    /**
     * 注册自定义拦截器到Spring MVC框架中，以便在处理请求时执行拦截器定义的逻辑。
     * 这个方法主要用于添加一个针对用户相关请求的拦截器，以实现诸如认证、授权等拦截逻辑。
     *
     * @param registry  InterceptorRegistry类型的参数，用于注册拦截器并配置其拦截规则。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加自定义的JWT Token拦截器，并配置拦截规则
        // 拦截所有路径下的请求
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/verify")
                .excludePathPatterns("/user/register");
    }

}
