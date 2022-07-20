package com.order.config;

import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//全局配置 ：当使用@Configuration ，会将配置作用所有的服务提供方
//局部配置： 如果只想针对某一个服务进行配置，就不要加@Configuration
//@Configuration
public class FeignConfig {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
////超时时间配置
//    @Bean
//    public Request.Options options(){
//        return new Request.Options(5000,1000);
//    }
//    public FeignAuthRequestInterceptor feignAuthRequestInterceptor(){
//        return new FeignAuthRequestInterceptor();
//    }


}

