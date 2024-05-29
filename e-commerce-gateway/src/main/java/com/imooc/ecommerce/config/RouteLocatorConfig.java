package com.imooc.ecommerce.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*配置登录请求转发规则*/
@Configuration
public class RouteLocatorConfig {

    /*使用代码定义路由规则，在网关层面拦截下登录和注册接口*/
    @Bean
    public RouteLocator loginRouteLocator(RouteLocatorBuilder builder){

        /*手动定义路由规则*/
        return builder.routes()
                .route(
                        "e_commerce_authority",
                        r -> r.path(
                                "/imooc/e-commerce/login",
                                "/imooc/e-commerce/register"
                        ).uri("http://localhost:9002")
                ).build();
    }

}
