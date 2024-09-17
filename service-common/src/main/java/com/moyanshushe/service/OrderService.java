package com.moyanshushe.service;

import com.moyanshushe.model.dto.order.*;
import com.moyanshushe.model.entity.Order;
import org.babyfish.jimmer.Page;
import org.jetbrains.annotations.NotNull;


/*
 * Author: Napbad
 * Version: 1.0
 */
public interface OrderService {
    @NotNull
    Order add(OrderForAdd order);

    @NotNull
    Page<OrderView> query(OrderSpecification specification);

    @NotNull
    Boolean update(OrderForUpdate orderForUpdate);

    void delete(OrderForDelete orderForDelete);
}
