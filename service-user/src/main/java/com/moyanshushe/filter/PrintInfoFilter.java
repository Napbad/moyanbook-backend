package com.moyanshushe.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Author: Napbad
 * Version: 1.0
 */

@Component
public class PrintInfoFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(PrintInfoFilter.class);
    private static final int MAX_PRINTABLE_HEADER_LENGTH = 100; // 限制打印的Header值长度  
    private static final int MAX_QUERY_PARAMS_PRINT = 10; // 最多打印的查询参数数量

    @Override
    public void init(FilterConfig filterConfig) {
        // 初始化逻辑（如果需要）  
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            log.info(convertRequestToString(httpRequest));
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 清理逻辑（如果需要）  
    }

    private String getRealClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else { // 代理情况下，X-Forwarded-For可能是逗号分隔的多个IP，取第一个为客户端IP  
            String[] ips = ip.split(",");
            ip = ips.length > 0 ? ips[0] : "";
        }
        return ip;
    }

    private String convertRequestToString(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Request: \n");

        stringBuilder.append("remote-address: ").append(getRealClientIp(request));

        // 请求方法和URL  
        stringBuilder.append("\nMethod: ").append(request.getMethod()).append(", URI: ").append(request.getRequestURI()).append("\n");

        // 请求头  
        Enumeration<String> headerNames = request.getHeaderNames();
        int headerCount = 0;
        while (headerNames.hasMoreElements() && headerCount < MAX_PRINTABLE_HEADER_LENGTH) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headerValues = request.getHeaders(headerName);
            StringBuilder headerValuesStr = new StringBuilder();
            while (headerValues.hasMoreElements()) {
                if (!headerValuesStr.isEmpty()) {
                    headerValuesStr.append(", ");
                }
                headerValuesStr.append(headerValues.nextElement());
            }
            stringBuilder.append(headerName).append(": ").append(headerValuesStr).append("\n");
            headerCount++;
        }

        // 查询参数（注意：路径变量在标准的Servlet过滤器中通常不可直接获取）  
        Map<String, List<String>> queryParams = new HashMap<>();
        String queryString = request.getQueryString();
        if (queryString != null) {
            String[] paramPairs = queryString.split("&");
            for (String paramPair : paramPairs) {
                int idx = paramPair.indexOf("=");
                String paramName = idx > 0 ? paramPair.substring(0, idx) : paramPair;
                String paramValue = idx > 0 && paramPair.length() > idx + 1 ? paramPair.substring(idx + 1) : null;
                queryParams.computeIfAbsent(paramName, k -> new java.util.ArrayList<>()).add(paramValue);
            }

            int paramCount = 0;
            for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
                if (paramCount >= MAX_QUERY_PARAMS_PRINT) {
                    break;
                }
                String paramName = entry.getKey();
                List<String> paramValues = entry.getValue();
                StringBuilder paramValuesStr = new StringBuilder();
                for (String value : paramValues) {
                    if (paramValuesStr.length() > 0) {
                        paramValuesStr.append(", ");
                    }
                    paramValuesStr.append(value);
                }
                stringBuilder.append(paramName).append(": ").append(paramValuesStr).append("\n");
                paramCount++;
            }
        }

        // 注意：未包括请求体和其他可能非常大的数据，以避免性能问题和隐私泄露  

        return stringBuilder.toString();
    }
}