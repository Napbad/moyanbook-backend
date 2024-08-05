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
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



/*
 * Author: Napbad
 * Version: 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final JwtProperties jwtProperties;
    private final AuthExcludePathProperties authExcludePathProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * Process the Web request and (optionally) delegate to the next {@code GatewayFilter}
     * through the given {@link GatewayFilterChain}.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

//        return chain.filter(exchange);
        ServerHttpRequest request = exchange.getRequest();

        String path = request.getPath().toString();

        if (isExcluded(path)) {
            ServerWebExchange serverWebExchange = exchange.mutate()
                    .request(builder -> builder.header(AuthorityConstant.USER_AUTHENTICATION_ID, "12").build())
                    .build();
            return chain.filter(serverWebExchange);
        }

        String token = (String) exchange.getAttributes().get(AuthorityConstant.AUTHORIZATION_CONSTANT);

        // 2、校验令牌
        try {
            log.info("校验token:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            String s = claims.get(JwtClaimsConstant.ID).toString();
            long empId = Long.parseLong(s);
            log.info("登录用户:{}", empId);
            // 3、令牌校验通过，放行请求，继续执行后续处理器（Controller方法）

            ServerWebExchange serverWebExchange = exchange.mutate()
                    .request(builder -> builder.header(AuthorityConstant.USER_AUTHENTICATION_ID, s).build())
                    .build();
            return chain.filter(serverWebExchange);
        } catch (Exception ex) {
            // 4、令牌校验不通过，响应401 Unauthorized状态码，并阻止执行后续处理器
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return 0;
    }

    @NotNull
    private Boolean isExcluded(String path) {
        return authExcludePathProperties
                .getExcludePaths()
                .stream()
                .anyMatch(excludePath -> antPathMatcher.match(excludePath, path));
    }
}
