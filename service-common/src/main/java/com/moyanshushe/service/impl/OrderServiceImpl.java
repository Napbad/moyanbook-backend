package com.moyanshushe.service.impl;

import com.moyanshushe.constant.RedisConstant;
import com.moyanshushe.exception.OrderExistsException;
import com.moyanshushe.mapper.ItemMapper;
import com.moyanshushe.mapper.OrderMapper;
import com.moyanshushe.model.dto.item.ItemSpecification;
import com.moyanshushe.model.dto.order.*;
import com.moyanshushe.model.entity.Item;
import com.moyanshushe.model.entity.ItemFetcher;
import com.moyanshushe.model.entity.Order;
import com.moyanshushe.service.OrderService;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.ast.mutation.DeleteResult;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * Author: Napbad
 * Version: 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;
    private final ItemMapper itemMapper;

    public OrderServiceImpl(OrderMapper mapper, ItemMapper itemMapper) {
        this.mapper = mapper;
        this.itemMapper = itemMapper;
    }

    @NotNull
    @Override
    @Transactional
    public Order add(OrderForAdd order) {
        int itemPageNumber = 0;
        ItemSpecification itemSpecification = new ItemSpecification();
        itemSpecification.setPage(itemPageNumber);
        itemSpecification.setIds(
                order.getItems()
                        .stream()
                        .map(
                                OrderForAdd.TargetOf_items::getId
                        ).toList());
        Page<Item> itemPage;
        // distributed lock
        synchronized (RedisConstant.ONE) {
            do {

                itemPage = itemMapper.fetch(itemSpecification,
                        ItemFetcher.$.amount());
                itemPage.getRows().forEach(
                        item -> {

                            if (item != null && item.amount() != null && item.amount() < 1) {
                                throw new OrderExistsException("商品库存不足:" + item.id());
                            }
                        }
                );

            } while (itemPage.getRows().size() < 10);

            OrderSpecification orderSpecification = new OrderSpecification();
            orderSpecification.setUserId(order.getUserId());
            Page<OrderView> query = mapper.query(orderSpecification);

            if (query.getTotalRowCount() != 0) {
                throw new OrderExistsException("该用户已经创建订单");
            }
        }


        SimpleSaveResult<Order> result = mapper.add(order);

        return result.getModifiedEntity();
    }

    @NotNull
    @Override
    public Page<OrderView> query(OrderSpecification specification) {
        return mapper.query(specification);
    }

    @NotNull
    @Override
    public Boolean update(OrderForUpdate orderForUpdate) {
        SimpleSaveResult<Order> result = mapper.update(orderForUpdate);

        return result.getAffectedRowCount(Order.class) == 1;
    }

    @Override
    public void delete(OrderForDelete orderForDelete) {
        DeleteResult result = mapper.delete(orderForDelete);

    }
}
