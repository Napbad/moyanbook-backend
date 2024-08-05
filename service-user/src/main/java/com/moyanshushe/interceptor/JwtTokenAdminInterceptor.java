package com.moyanshushe.interceptor;

import com.moyanshushe.constant.JwtClaimsConstant;
import com.moyanshushe.properties.JwtProperties;
import com.moyanshushe.utils.JwtUtil;
import com.moyanshushe.utils.UserContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;



/**
 * jwt令牌校验的拦截器
 */
@Slf4j
@Component
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;

    public JwtTokenAdminInterceptor(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 校验jwt
     *
     * @param request  请求对象，用于从请求头中获取JWT令牌
     * @param response 响应对象，用于在令牌校验失败时设置HTTP状态码
     * @param handler  当前拦截的处理器对象，用于判断是否为Controller方法
     * @return 是否允许继续执行后续处理器（即Controller方法）
     * @throws Exception 如果在处理过程中出现异常
     */
    @Override
    public boolean preHandle(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull Object handler) throws Exception {
        // 判断当前拦截到的是Controller的方法还是其他资源（如静态资源）
        if (!(handler instanceof HandlerMethod)) {
            // 当前拦截到的不是动态方法（如Controller方法），直接放行
            return true;
        }

        if (jwtProperties != null) {
            // 1、从请求头中获取令牌
            String token = request.getHeader(jwtProperties.getAdminTokenName());

            // 2、校验令牌
            try {
                log.info("校验token:{}", token);
                Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
                String s = claims.get(JwtClaimsConstant.MEMBER_ID).toString();
                int id = Integer.parseInt(s);
                UserContext.setUserId(id);
                log.info("登录用户:{}", id);
                // 3、令牌校验通过，放行请求，继续执行后续处理器（Controller方法）
                return true;
            } catch (Exception ex) {
                // 4、令牌校验不通过，响应401 Unauthorized状态码，并阻止执行后续处理器
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 设置401状态码
                return false;
            }
        } else {
            log.info("jwtProperties is null");
        }
        // 若jwtProperties为空或处理过程出现异常，不允许继续执行后续处理器
        return false;
    }
}