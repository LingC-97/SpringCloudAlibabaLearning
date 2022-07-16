package com.order.feign;

import feign.Param;
import feign.RequestLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "product-service",path = "/product")
public interface ProductFeignService {
    @RequestLine("GET /{id}")   //  @RequestMapping ===@RequestLine     @athVariable === @Param
    public String get(@Param("id") Integer id);   //在feign中，这里pathVariable更严格，需要指定id

}

