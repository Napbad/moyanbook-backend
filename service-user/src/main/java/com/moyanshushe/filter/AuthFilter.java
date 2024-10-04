package com.moyanshushe.filter;

import com.moyanshushe.constant.AuthorityConstant;
import com.moyanshushe.constant.JwtClaimsConstant;
import com.moyanshushe.properties.AuthExcludePathProperties;
import com.moyanshushe.properties.JwtProperties;
import com.moyanshushe.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AuthFilter implements Filter, Ordered {

    private final JwtProperties jwtProperties;
    private final AuthExcludePathProperties authExcludePathProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) {
        // 初始化操作（如果需要）
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse)) {
            throw new ServletException("Only HTTP requests are handled by this filter");
        }

        String path = httpRequest.getRequestURI();
        String token = httpRequest.getHeader(AuthorityConstant.AUTHORIZATION_CONSTANT);

        if (isExcluded(path)) {
            // 如果路径被排除，则添加一个标识头（或者不做任何处理）
            httpRequest.setAttribute(AuthorityConstant.USER_AUTHENTICATION_ID, "0");
            chain.doFilter(request, response);
            return;
        }

        // 校验令牌
        try {
            log.info("校验token:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            String userId = claims.get(JwtClaimsConstant.ID).toString();
            log.info("登录用户:{}", userId);

            // 令牌校验通过，添加用户ID到请求属性中
            httpRequest.setAttribute(AuthorityConstant.USER_AUTHENTICATION_ID, userId);
            chain.doFilter(request, response);
        } catch (Exception ex) {
            // 令牌校验不通过，响应401 Unauthorized状态码
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            if (ex.getMessage().contains("JWT expired at")) {
                // 如果令牌过期，直接返回
                return;
            }

            // 其他异常也可以处理或记录日志
            throw new ServletException("JWT validation failed", ex);
        }
    }

    @Override
    public void destroy() {
        // 清理资源（如果需要）
    }

    @NotNull
    private Boolean isExcluded(String path) {
        return authExcludePathProperties
                .getExcludePaths()
                .stream()
                .anyMatch(excludePath -> antPathMatcher.match(excludePath, path));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE; // 或者根据需要设置其他顺序值
    }
}