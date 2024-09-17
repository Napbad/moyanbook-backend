package com.moyanshushe.service;

/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/5/24 3:32 PM
    @Description: 

*/

import com.moyanshushe.model.dto.order.OrderForPay;

public interface PayService {
    void payOrder(OrderForPay order);
}
