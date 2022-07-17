package com.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
public class OrderController {


    @RequestMapping("/flow")
//    @SentinelResource(value = "flow",blockHandler = "flowBlockHandler")
    public String flow() {
        return "正常访问";
    }
    public String flowBlockHandler(BlockException e){
        return  "流控！！";

    }
//    @RequestMapping("/flowThread")
//    @SentinelResource(value = "flowThread",blockHandler = "flowBlockHandler")
//    public String flowThread() throws InterruptedException{
//        TimeUnit.SECONDS.sleep(5);
//        return "正常访问!";
//    }

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

    @Autowired
    IOrderService orderService;
//关联流控 访问/add 触发/get
    @RequestMapping("/test1")
    public String test1() {
        return orderService.getUser();
    }
//    关联流控 访问/add 触发/get
    @RequestMapping("/test2")
    public String test2() throws  InterruptedException{
        return orderService.getUser();
    }

//    熔断降级--慢调用比例
    @RequestMapping("/flowThread")
    public String flowThread() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);  //睡了两秒来模拟慢调用
        System.out.println("正常访问");
        return "正常访问";
    }

//   熔断降级-- 异常比例
    @RequestMapping("/err")
    public String err() {
        int a = 1/0;
        return "hello";
    }








}
