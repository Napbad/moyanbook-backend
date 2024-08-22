package com.moyanshushe.interceptor;

import com.moyanshushe.constant.AuthorityConstant;
import com.moyanshushe.properties.AuthExcludePathProperties;
import com.moyanshushe.properties.JwtProperties;
import com.moyanshushe.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;


/**
 * jwt令牌校验的拦截器
 */
@Slf4j
@Component
public class UserContextInterceptor implements HandlerInterceptor {

    private final AuthExcludePathProperties authExcludePathProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public UserContextInterceptor( AuthExcludePathProperties authExcludePathProperties) {
        this.authExcludePathProperties = authExcludePathProperties;
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

        log.info("header: {}  value {} , request path: {}",
                AuthorityConstant.USER_AUTHENTICATION_ID, header, request.getServletPath());

        if (isExcluded(request.getServletPath())) {
            UserContext.setUserId(0);

            return true;
        }

        if (!StringUtils.hasLength(header)) {
            return false;
        }

        UserContext.setUserId(Integer.valueOf(header));

        return true;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request,
                                @NotNull HttpServletResponse response,
                                @NotNull Object handler, Exception ex) throws Exception {
        UserContext.remove();
    }

    @NotNull
    private Boolean isExcluded(String path) {
        return authExcludePathProperties
                .getExcludePaths()
                .stream()
                .anyMatch(excludePath -> antPathMatcher.match(excludePath, path));
    }
}