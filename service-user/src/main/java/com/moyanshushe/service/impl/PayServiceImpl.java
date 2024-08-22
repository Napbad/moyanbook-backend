package com.moyanshushe.service.impl;

/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/5/24 3:32 PM
    @Description: 

*/

import com.moyanshushe.model.dto.order.OrderForPay;
import com.moyanshushe.properties.AlipayProperty;
import com.moyanshushe.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private final AlipayProperty alipayProperty;

    @Override
    public void payOrder(OrderForPay order) {

    }
}
