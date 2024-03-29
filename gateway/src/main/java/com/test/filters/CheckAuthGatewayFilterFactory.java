package com.test.filters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;


@Component
public class CheckAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<CheckAuthGatewayFilterFactory.Config> {



    public CheckAuthGatewayFilterFactory() {
        super(CheckAuthGatewayFilterFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("value");
    }

    @Override
    public GatewayFilter apply(CheckAuthGatewayFilterFactory.Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                String name=exchange.getRequest().getQueryParams().getFirst("name");
                if(StringUtils.isNotBlank(name)){
                    if(config.getValue().equals(name)){
                        return chain.filter(exchange);
                    }
                }
                else {
                    exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
                    System.out.println("11");
                    return  exchange.getResponse().setComplete();
                }
                //正常请求
//                chain.filter(exchange);
                //1.获取name参数
//                2.如果！=value就失败
//                3.否则正常访问
                return chain.filter(exchange);
            }
        };
    }



    public static class Config {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
