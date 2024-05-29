package com.imooc.ecommerce.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*HTTP 请求头部携带Token验证过滤器*/
public class HeaderTokenGatewayFilter implements GatewayFilter , Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //从HTTP Header中寻找key为token，value为imooc的键值对
        String name = exchange.getRequest().getHeaders().getFirst("token");
        if("imooc".equals(name)){
            return chain.filter(exchange);
        }

        //没有权限
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 2;
    }
}
