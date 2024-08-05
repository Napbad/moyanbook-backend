package com.moyanshushe.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;

/*
 * Author: Napbad
 * Version: 1.0
 */

@Slf4j
@Component
public class PrintInfoGlobalFilter implements GlobalFilter, Ordered {

    private static final int MAX_PRINTABLE_HEADER_LENGTH = 100; // 限制打印的Header值长度
    private static final int MAX_PATH_VARIABLES_PRINT = 5; // 最多打印的路径变量数量
    private static final int MAX_QUERY_PARAMS_PRINT = 10; // 最多打印的查询参数数量

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info(convertExchangeToString(exchange));

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private String getRealClientIp(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String ip = request.getHeaders().getFirst("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        } else { // 代理情况下，X-Forwarded-For可能是逗号分隔的多个IP，取第一个为客户端IP
            String[] ips = ip.split(",");
            ip = ips.length > 0 ? ips[0] : "";
        }
        return ip;
    }
    private String convertExchangeToString(ServerWebExchange exchange) {
        StringBuilder stringBuilder = new StringBuilder();
        ServerHttpRequest request = exchange.getRequest();

        stringBuilder.append("Request: \n");

        stringBuilder.append("remote-address: ")
                .append(getRealClientIp(exchange));

        // 请求方法和URL
        stringBuilder.append("\nMethod: ").append(request.getMethod()).append(", URI: ").append(request.getURI()).append("\n");

        // 请求头
        HttpHeaders headers = request.getHeaders();
        headers.entrySet().stream()
                .limit(MAX_PRINTABLE_HEADER_LENGTH)
                .forEach(entry -> {
                    String headerName = entry.getKey();
                    List<String> headerValues = entry.getValue();
                    stringBuilder.append(headerName).append(": ")
                            .append(StringUtils.collectionToCommaDelimitedString(headerValues)).append("\n");
                });

        // 路径变量
        Map<String, String> pathVariables = exchange.getAttributeOrDefault(ServerWebExchangeUtils.URI_TEMPLATE_VARIABLES_ATTRIBUTE, Collections.emptyMap());
        if (!pathVariables.isEmpty()) {
            stringBuilder.append("Path Variables:\n");
            pathVariables.entrySet().stream()
                    .limit(MAX_PATH_VARIABLES_PRINT)
                    .forEach(entry -> stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("\n"));
        }

        // 查询参数
        request.getQueryParams().entrySet().stream()
                .limit(MAX_QUERY_PARAMS_PRINT)
                .forEach(entry -> {
                    String paramName = entry.getKey();
                    List<String> paramValues = entry.getValue();
                    stringBuilder.append(paramName).append(": ")
                            .append(StringUtils.collectionToCommaDelimitedString(paramValues)).append("\n");
                });

        // 注意：未包括请求体和其他可能非常大的数据，以避免性能问题和隐私泄露

        return stringBuilder.toString();
    }
}
