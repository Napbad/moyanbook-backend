package com.moyanshushe.model.funcentity;

/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/5/24 3:55 PM
    @Description: 

*/

import lombok.Data;

@Data
public class AlipayEntity {


    private String out_trade_no;

    private String subject;

    private String total_amount;

    private String body;

    private String timeout_express= "10m";

    private String product_code= "FAST_INSTANT_TRADE_PAY";

}


