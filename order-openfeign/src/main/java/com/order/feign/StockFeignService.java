package com.order.feign;

import com.order.config.FeignConfig;
import feign.Feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

//2.添加feign接口
//name 指定调用 rest接口 所对应的服务名
//path 指定调用 rest接口所在的stockController指定的RestquestMapping的路径
@FeignClient(name = "stock-service", path = "/stock", configuration = FeignConfig.class)
public interface StockFeignService {
    //声明需要调用的rest接口对应的方法
    @RequestMapping("/reduct")
    public String reduct();

}

/*
*
@RestController
@RequestMapping("/stock")
public class stockController {
    @Value("${server.port}")
    String port;

    @RequestMapping("/reduct")
    public String reduct(){
        System.out.println("扣减库存");
        return "扣减库存"+port;

    }
}

* */
