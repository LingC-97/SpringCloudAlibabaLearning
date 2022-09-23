package com.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class orderController {
    @Autowired
    public RestTemplate restTemplate;

    @RequestMapping("/add")
    public String add() {
        System.out.println("下单成功");
        String msg = restTemplate.getForObject("http://stock-service/stock/reduct", String.class);
        return "hello world" + msg;
    }
    //修改的是这句话，原来是restTemplate.getForObject("http://localhost:8011/stock/reduct", String.class);
    //现在直接把服务名拿过来使用stock-service，替换调localhost:8011

   @RequestMapping("/header")
    public String header(@RequestHeader("X‐Request‐color") String color){
       System.out.println("从gateway中获取请求头");
       return color;
    }
}
