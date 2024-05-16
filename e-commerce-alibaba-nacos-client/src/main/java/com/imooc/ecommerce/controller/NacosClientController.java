package com.imooc.ecommerce.controller;

import com.imooc.ecommerce.service.NacosClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
* nacos client controller
* */
@Slf4j
@RestController
@RequestMapping("/nacos-client")
public class NacosClientController {
    private final NacosClientService nacosClientService;

    public NacosClientController(NacosClientService nacosClientService) {
        this.nacosClientService = nacosClientService;
    }

    /*
    * 根据service id 获取服务所有的实例信息
    * */
    @GetMapping("/service-instance")
    public List<ServiceInstance> logNacosClientInfo(
            @RequestParam(defaultValue = "e-commerce-nacos-client") String serviceId){

        log.info("comming in log nacos client info:[{}]",serviceId);
        return nacosClientService.getNacosClientInfo(serviceId);
    }

}
