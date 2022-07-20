package com.tuling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ConfigApplication {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ConfigApplication.class, args);
        while (true) {
            String userName = applicationContext.getEnvironment().getProperty("user.name");
            String userAge = applicationContext.getEnvironment().getProperty("user.age");
            String config = applicationContext.getEnvironment().getProperty("user.config");

            System.err.println("user name :" + userName + "--- user age : " + userAge + "----user.config：" + config);
            TimeUnit.SECONDS.sleep(1); //可以根据单位进行睡眠
        }
        //nacos 客户端每10毫秒去注册中心进行判断，根据MD5，如果不一样，就会拉取

    }
}
