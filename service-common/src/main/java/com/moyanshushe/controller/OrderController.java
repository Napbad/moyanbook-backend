package com.moyanshushe.controller;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.constant.OrderConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.order.OrderForAdd;
import com.moyanshushe.model.dto.order.OrderForDelete;
import com.moyanshushe.model.dto.order.OrderForUpdate;
import com.moyanshushe.model.dto.order.OrderSpecification;
import com.moyanshushe.model.entity.Order;
import com.moyanshushe.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @Api
    @PostMapping("/add")
    public ResponseEntity<Result> add(
            @RequestBody OrderForAdd order) {

        Boolean result = service.add(order);
        return ResponseEntity.ok(
                Boolean.TRUE.equals(result) ? Result.success(OrderConstant.ORDER_ADD_SUCCESS)
                        : Result.error(OrderConstant.ORDER_ADD_FAIL));
    }

    @Api
    @PostMapping("/query")
    public ResponseEntity<Result> queryOrder(
            @RequestBody OrderSpecification specification) {
        Page<Order> result = service.query(specification);

        return ResponseEntity.ok(Result.success(result));
    }

    @Api
    @PostMapping("/update")
    public ResponseEntity<Result> updateOrder(
            @RequestBody OrderForUpdate orderForUpdate) {

        Boolean result = service.update(orderForUpdate);

        return ResponseEntity.ok(
                Boolean.TRUE.equals(result) ? Result.success(OrderConstant.ORDER_UPDATE_SUCCESS)
                        : Result.error(OrderConstant.ORDER_UPDATE_FAIL));
    }

    @Api
    @PostMapping("/delete")
    public ResponseEntity<Result> deleteOrder(
            @RequestBody OrderForDelete orderForDelete) {

        Boolean result = service.delete(orderForDelete);

        return ResponseEntity.ok(
                Boolean.TRUE.equals(result) ? Result.success(OrderConstant.ORDER_DELETE_SUCCESS)
                        : Result.error(OrderConstant.ORDER_DELETE_FAIL));
    }
}
