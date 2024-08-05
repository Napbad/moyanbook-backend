package com.moyanshushe.service;

/*
 * Author: Jiansong Shen
 * Version: 1.0
 */

import com.moyanshushe.model.dto.address_part2.AddressPart2ForDelete;
import com.moyanshushe.model.dto.address_part2.AddressPart2Input;
import com.moyanshushe.model.dto.address_part2.AddressPart2Specification;
import com.moyanshushe.model.entity.AddressPart2;
import org.babyfish.jimmer.Page;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class AddressPart2ServiceImplTest {

    @Autowired
    private AddressPart2Service addressPart2Service;

    @Test
    @Order(1)
    void testAdd() {
        System.err.println("add");

        AddressPart2Input input = new AddressPart2Input();

        input.setName("test");
        input.setParentAddress(0);

        addressPart2Service.add(input);
    }

    @Test
    @Order(2)
    void testQuery() {
        AddressPart2Specification specification = new AddressPart2Specification();
        specification.setName("test");

        Page<AddressPart2> page = addressPart2Service.query(specification);

        assertNotNull(page);
        assertNotNull(page.getRows().getFirst());
    }

    @Test
    @Order(3)
    void testUpdate() {
        System.err.println("update");
        AddressPart2Specification specification = new AddressPart2Specification();
        specification.setName("test");

        Page<AddressPart2> page = addressPart2Service.query(specification);

        System.err.println(page);

        assertNotNull(page.getRows().getFirst());

        AddressPart2Input input = new AddressPart2Input();
        input.setId(page.getRows().getFirst().id());
        input.setName("test2");
        input.setParentAddress(null);

        addressPart2Service.update(input);
    }

    @Test
    @Order(4)
    void testDelete() {
        System.err.println("delete");
        AddressPart2ForDelete delete = new AddressPart2ForDelete();

        Page<AddressPart2> page = addressPart2Service.query(new AddressPart2Specification());

        delete.setIds(
                page.getRows().stream().map(AddressPart2::id).filter(id -> id > 5).toList()
        );

        addressPart2Service.delete(delete);

        assertEquals(5, addressPart2Service.query(new AddressPart2Specification()).getTotalRowCount());
    }
}
