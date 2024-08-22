package com.moyanshushe.mapper;

import com.moyanshushe.model.dto.order.*;
import com.moyanshushe.model.entity.Fetchers;
import com.moyanshushe.model.entity.Order;
import com.moyanshushe.model.entity.OrderTable;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.DeleteResult;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * Author: Napbad
 * Version: 1.0
 */
@Component
public class OrderMapper {

    private final JSqlClient jsqlClient;
    private final OrderTable table;

    public OrderMapper(JSqlClient jsqlClient) {
        this.jsqlClient = jsqlClient;
        this.table = OrderTable.$;
    }

    public SimpleSaveResult<Order> add(OrderForAdd order) {
        return jsqlClient.save(order);
    }

    public Page<Order> query(OrderSpecification specification) {
        return jsqlClient.createQuery(table)
                .where(specification)
                .orderBy(
                        table.createTime().asc()
                )
                .select(
                        table.fetch(
                                Fetchers.ORDER_FETCHER
                                        .items(
                                                Fetchers.ITEM_FETCHER
                                                        .images(
                                                                Fetchers.ITEM_IMAGE_FETCHER
                                                                        .imageUrl()
                                                        )
                                                        .category(
                                                                Fetchers.CATEGORY_FETCHER
                                                                        .name()
                                                        )
                                                        .name()
                                                        .description()
                                                        .user(
                                                                Fetchers.USER_FETCHER
                                                                        .name()
                                                                        .profileUrl()
                                                        )
                                                        .price()
                                                        .status()

                                        )
                                        .status()
                        )
                )
                .fetchPage(
                        specification.getPage() == null ? 0 : specification.getPage(),
                        specification.getPageSize() == null ? 10 : specification.getPageSize()
                );
    }

    public SimpleSaveResult<Order> update(OrderForUpdate orderForUpdate) {
        return jsqlClient.save(orderForUpdate.toEntity());
    }

    public DeleteResult delete(OrderForDelete orderForDelete) {
        return jsqlClient.deleteByIds(Order.class, orderForDelete.getIds());
    }

    public List<OrderSubstance> getOrderByUserId(int userId) {
        return jsqlClient.createQuery(table)
                .where(table.userId().eq(userId))
                .select(
                        table.fetch(OrderSubstance.class)
                ).execute();
    }
}
