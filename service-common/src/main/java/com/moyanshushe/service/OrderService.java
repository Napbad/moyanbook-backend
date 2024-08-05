package com.moyanshushe.service;

import com.moyanshushe.model.dto.order.OrderForAdd;
import com.moyanshushe.model.dto.order.OrderForDelete;
import com.moyanshushe.model.dto.order.OrderForUpdate;
import com.moyanshushe.model.dto.order.OrderSpecification;
import com.moyanshushe.model.entity.Order;
import org.babyfish.jimmer.Page;
import org.jetbrains.annotations.NotNull;


/*
 * Author: Napbad
 * Version: 1.0
 */
public interface OrderService {
    @NotNull
    Boolean add(OrderForAdd order);

    @NotNull
    Page<Order> query(OrderSpecification specification);

    @NotNull
    Boolean update(OrderForUpdate orderForUpdate);

    @NotNull
    Boolean delete(OrderForDelete orderForDelete);
}
