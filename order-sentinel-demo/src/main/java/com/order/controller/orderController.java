package com.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
public class orderController {


    @RequestMapping("/flow")
//    @SentinelResource(value = "flow",blockHandler = "flowBlockHandler")
    public String flow() {
        return "正常访问";
    }
    public String flowBlockHandler(BlockException e){
        return  "流控！！";

    }
    @RequestMapping("/flowThread")
    @SentinelResource(value = "flowThread",blockHandler = "flowBlockHandler")
    public String flowThread() throws InterruptedException{
        TimeUnit.SECONDS.sleep(5);
        return "正常访问!";
    }

    @RequestMapping("/add")
    public String add() {
        System.out.println("下单成功");
        return "hello";
    }
    //生成订单来触发查询订单的限流
    //1秒钟之内访问超过两次的生成订单的接口，查询订单就会限流
//    资源名：/order/get
//    关联资源：/order/add

    @RequestMapping("/get")
    public String get() throws InterruptedException{
        return "查询订单";
    }




}