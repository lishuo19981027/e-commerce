package com.imooc.ecommerce.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/*网关需要注入到容器中的bean*/
@Configuration
public class GatewayBeanConf {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
