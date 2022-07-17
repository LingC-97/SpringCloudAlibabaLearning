package com.order.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

@Service
public class OrderSericeImpl implements  IOrderService {
    @Override
//    不仅能对接口进行流控，还能对业务方法进行流控
//    用了sentinel就不会使用统一异常处理了，所以会报500的错误，需要手写blockHandler
    @SentinelResource(value = "getUser",blockHandler = "blockHandlerGetUser")
    public String getUser() {
        return "查询用户";
    }
    public String blockHandlerGetUser(BlockException e){
        return "流控用户";

    }

}
