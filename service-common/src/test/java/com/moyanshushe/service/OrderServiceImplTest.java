package com.moyanshushe.service;

/*
 * Author: Napbad
 * Version: 1.0
 */


import com.moyanshushe.model.dto.item.ItemForAdd;
import com.moyanshushe.model.dto.order.OrderForAdd;
import com.moyanshushe.model.dto.order.OrderForDelete;
import com.moyanshushe.model.dto.order.OrderForUpdate;
import com.moyanshushe.model.dto.order.OrderSpecification;
import com.moyanshushe.model.entity.Item;
import com.moyanshushe.model.entity.Objects;
import com.moyanshushe.model.entity.OrderDraft;
import com.moyanshushe.utils.JsonToObjectUtil;
import org.babyfish.jimmer.Page;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @Test
    @Order(1)
    void testAdd() {
        List<OrderForAdd> orderForAdds = JsonToObjectUtil.jsonFileToObject(
                "OrderForAdd",
                JsonToObjectUtil.JSON_TYPE_FACTORY.constructCollectionType(List.class, OrderForAdd.class)
        );

        orderForAdds.forEach(
                orderForAdd -> {
                    orderForAdd.setOrderId(UUID.randomUUID());
                }
        );


        orderForAdds.forEach(orderForAdd -> {
            orderService.add(orderForAdd);
        });

        Page<com.moyanshushe.model.entity.Order> page = orderService.query(new OrderSpecification());
        assertEquals(12, page.getTotalRowCount());
    }

    @Test
    @Order(2)
    void testQuery() {
        OrderSpecification specification = new OrderSpecification();
        specification.setStatus(com.moyanshushe.model.entity.Order.Status.NORMAL);
        Page<com.moyanshushe.model.entity.Order> page = orderService.query(specification);

        assertEquals(6, page.getTotalRowCount());

        specification.setUserId(1);
        page = orderService.query(specification);

        assertEquals(2, page.getTotalRowCount());
    }

    @Test
    @Order(3)
    void testUpdate() {
        OrderSpecification orderSpecification = new OrderSpecification();
        orderSpecification.setPage(1);
        Page<com.moyanshushe.model.entity.Order> orderPage = orderService.query(orderSpecification);

        orderPage.getRows().stream()
                .map(
                        order -> {
                            OrderForUpdate orderForUpdate = new OrderForUpdate();
                            BeanUtils.copyProperties(order, orderForUpdate);
                            orderForUpdate.setUserId(2);
                            OrderForUpdate.TargetOf_updatePerson targetOfUpdatePerson = new OrderForUpdate.TargetOf_updatePerson();
                            targetOfUpdatePerson.setId(2);
                            orderForUpdate.setUpdatePerson(
                                    targetOfUpdatePerson
                            );
                            return orderForUpdate;
                        }
                )
                .toList()
                .forEach(orderForUpdate -> {
                    orderService.update(orderForUpdate);
                });


        Page<com.moyanshushe.model.entity.Order> page = orderService.query(new OrderSpecification());

        assertEquals(12, page.getTotalRowCount());

        OrderSpecification specification = new OrderSpecification();
        specification.setPageSize(10);
        specification.setPage(1);

        page = orderService.query(specification);

        page.getRows().forEach(
                order -> {
                    assertNotNull(order.userId());
                    assertEquals(2, order.userId());
                    assertEquals(2, order.updatePerson().id());
                }
        );
    }

    @Test
    @Order(4)
    void testDelete() {

        OrderSpecification orderSpecification = new OrderSpecification();

        orderSpecification.setPage(1);
        orderSpecification.setPageSize(10);

        Page<com.moyanshushe.model.entity.Order> page = orderService.query(orderSpecification);

        List<UUID> list = page.getRows().stream().map(com.moyanshushe.model.entity.Order::orderId).toList();

//        for (int i = 0; i < pageCount; i++) {
//            orderSpecification.setPage(0);
//            page = orderService.query(orderSpecification);
//
//            List<UUID> list = page.getRows().stream().map(com.moyanshushe.model.entity.Order::orderId)
//                    .toList();
//
//            OrderForDelete orderForDelete = new OrderForDelete();
//            orderForDelete.setIds(
//                    page.getRows()
//                            .stream()
//                            .map(
//                                    com.moyanshushe.model.entity.Order::id
//                            )
//                            .filter(id -> id > 10).toList());
//
//            orderService.delete(orderForDelete);
//        }

        OrderForDelete orderForDelete = new OrderForDelete();
        orderForDelete.setIds(list);
        orderService.delete(orderForDelete);

        assertEquals(10, orderService.query(new OrderSpecification()).getTotalRowCount());
    }
}
