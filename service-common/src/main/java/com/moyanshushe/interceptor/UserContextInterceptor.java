package com.moyanshushe.interceptor;

import com.moyanshushe.constant.AuthorityConstant;
import com.moyanshushe.properties.JwtProperties;
import com.moyanshushe.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * jwt令牌校验的拦截器
 */
@Slf4j
@Component
public class UserContextInterceptor implements HandlerInterceptor {

    public UserContextInterceptor(JwtProperties jwtProperties) {
    }

    /**
     * 校验jwt
     *
     * @param request  请求对象，用于从请求头中获取JWT令牌
     * @param response 响应对象，用于在令牌校验失败时设置HTTP状态码
     * @param handler  当前拦截的处理器对象，用于判断是否为Controller方法
     * @return 是否允许继续执行后续处理器（即Controller方法）
     */
    @Override
    public boolean preHandle(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull Object handler) {
        String header = request.getHeader(AuthorityConstant.USER_AUTHENTICATION_ID);

        log.info("header: {}  value {} ", AuthorityConstant.USER_AUTHENTICATION_ID, header);

        if (!StringUtils.hasLength(header)) {
            return false;
        }

        UserContext.setUserId(Integer.valueOf(header));

//        UserContext.setUserId(7);

        return true;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request,
                                @NotNull HttpServletResponse response,
                                @NotNull Object handler, Exception ex) throws Exception {
        UserContext.remove();
    }
}