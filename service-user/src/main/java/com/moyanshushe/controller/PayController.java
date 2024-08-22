package com.moyanshushe.controller;

import com.moyanshushe.constant.AuthorityConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.order.OrderForPay;
import com.moyanshushe.properties.AlipayProperty;
import com.moyanshushe.service.PayService;
import com.moyanshushe.utils.UserContext;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/5/24 3:14 PM
    @Description: 

*/


@Api

@RestController
@RequestMapping({"/pay"})
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;
    private final AlipayProperty aliPayConfig;

    /**
     * @param order OrderForPay {
     *              orderId！
     *              userId！
     *              }
     * @return 暂无法实现，故返回success
     */
    @Api
    @PostMapping("order")
    public ResponseEntity<Result> payOrder(@RequestBody OrderForPay order) {
        // check
        if (!Objects.equals(order.getUserId(), UserContext.getUserId())) {
            return ResponseEntity.ok(Result.error(AuthorityConstant.USER_INFO_WRONG));
        }

//        payService.payOrder(order);

        return ResponseEntity.ok(Result.success());
    }

    /**
     * @param request httpRequest
     * @return 暂无法实现，故返回success
     * @throws Exception 空
     */
    @Api
    @PostMapping("/notify")
    public String payNotify(HttpServletRequest request) throws Exception {
        return "success";
    }
}
