package com.sentinelnew.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SentinelAspectConfiguration {
//    @Bean
    public SentinelAspectConfiguration sentinelAspectConfiguration(){
        return new SentinelAspectConfiguration();
    }
}
