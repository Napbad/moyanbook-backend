package com.moyanshushe.service;

/*
 * Author: Jiansong Shen
 * Version: 1.0
 */

import com.moyanshushe.model.dto.address_part1.AddressPart1Input;
import com.moyanshushe.model.dto.address_part1.AddressPart1ForDelete;
import com.moyanshushe.model.dto.address_part1.AddressPart1Specification;
import com.moyanshushe.model.entity.AddressPart1;
import org.babyfish.jimmer.Page;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class AddressPart1ServiceImplTest {

    @Autowired
    private AddressPart1Service addressPart1Service;

    @Test
    @Order(1)
    void add() {
        AddressPart1Input input = new AddressPart1Input();

        input.setName("test");
        input.setParentAddress(1);

        addressPart1Service.add(input);
    }

    @Test
    @Order(2)
    void query() {
        AddressPart1Specification specification = new AddressPart1Specification();
        specification.setName("test");

        Page<AddressPart1> page = addressPart1Service.query(specification);

        assertNotNull(page);
        assertNotNull(page.getRows().getFirst());
    }

    @Test
    @Order(3)
    void update() {
        AddressPart1Specification specification = new AddressPart1Specification();
        specification.setName("test");

        Page<AddressPart1> page = addressPart1Service.query(specification);

        assertNotNull(page.getRows().getFirst());

        AddressPart1Input input = new AddressPart1Input();
        input.setId(page.getRows().getFirst().id());
        input.setName("test2");
        input.setParentAddress(1);

        addressPart1Service.update(input);

        specification.setName("test2");
        Page<AddressPart1> page1 = addressPart1Service.query(specification);

        assertEquals(1, page1.getTotalRowCount());
        assertEquals(page.getRows().getFirst().id(), page1.getRows().getFirst().id());
        assertEquals(1, page1.getRows().getFirst().parentAddress());
    }

    @Test
    @Order(4)
    void delete() {
        AddressPart1ForDelete delete = new AddressPart1ForDelete();

        Page<AddressPart1> page = addressPart1Service.query(new AddressPart1Specification());

        delete.setIds(
                page.getRows().stream().map(AddressPart1::id).filter(id -> id > 5).toList()
        );

        addressPart1Service.delete(delete);

        assertEquals(5, addressPart1Service.query(new AddressPart1Specification()).getTotalRowCount());
    }


}
