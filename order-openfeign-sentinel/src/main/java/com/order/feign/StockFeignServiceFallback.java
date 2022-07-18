package com.order.feign;

import org.springframework.stereotype.Service;

@Service
public class StockFeignServiceFallback implements StockFeignService {
    @Override
    public String reduct() {
        return "降级了！";
    }
}
