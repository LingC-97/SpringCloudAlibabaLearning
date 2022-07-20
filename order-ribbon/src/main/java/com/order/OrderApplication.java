package com.order;

import com.ribbon.RibbonRandomRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

//@EnableDiscoveryClient
@SpringBootApplication
//通过配置类的方式修改默认的负载均衡策略
//@RibbonClients(value = {
//        @RibbonClient(name ="stock-service" ,configuration = RibbonRandomRuleConfig.class)
//})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);

    }

    @Bean
    @LoadBalanced  //有了负载均衡的一个调用机制，解析服务名称（就是你在配置文件中的servername=srock-service），对应调用的地址
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        return restTemplate;

    }
    //负载均衡默认采用轮询的机制

}
