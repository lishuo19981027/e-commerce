package com.imooc.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

    /*
    * Nacos Client 工程启动入口
    * */
@EnableDiscoveryClient
@SpringBootApplication
public class NacosClientApplication {
        public static void main(String[] args) {
            SpringApplication.run(NacosClientApplication.class,args);
        }
}
