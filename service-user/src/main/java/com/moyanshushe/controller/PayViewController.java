package com.moyanshushe.controller;

/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/5/24 4:15 PM
    @Description: 

*/

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PayViewController {

    /**
     * @return 暂无法实现，故返回success
     */
    @GetMapping("/pay-view")
    public String payView() {
        return "pay";
    }
}
