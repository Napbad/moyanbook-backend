package com.moyanshushe.service;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.model.dto.address.AddressForDelete;
import com.moyanshushe.model.dto.address.AddressSpecification;
import com.moyanshushe.model.dto.address.AddressSubstance;
import com.moyanshushe.model.entity.Address;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class AddressServiceImplTest {

    @Autowired
    private JSqlClient jSqlClient;

    @Autowired
    private AddressService addressService;

    @Test
    @Order(1)
    void testAdd() {
        AddressSubstance substance = new AddressSubstance();
        substance.setAddress("test address");
        AddressSubstance.TargetOf_addressPart1 ofAddressPart1 = new AddressSubstance.TargetOf_addressPart1();
        AddressSubstance.TargetOf_addressPart2 ofAddressPart2 = new AddressSubstance.TargetOf_addressPart2();
        ofAddressPart1.setId(1);
        ofAddressPart2.setId(1);
        substance.setAddressPart1(ofAddressPart1);
        substance.setAddressPart2(ofAddressPart2);

        AddressSubstance.TargetOf_createPerson targetOfCreatePerson = new AddressSubstance.TargetOf_createPerson();
        targetOfCreatePerson.setId(1);
        AddressSubstance.TargetOf_updatePerson targetOfUpdatePerson = new AddressSubstance.TargetOf_updatePerson();
        targetOfUpdatePerson.setId(1);

        substance.setCreatePerson(targetOfCreatePerson);
        substance.setUpdatePerson(targetOfUpdatePerson);
        Boolean added = addressService.add(substance);
        Boolean added1 = addressService.add(substance);

        substance.setAddress("test addr");

        Boolean added2 = addressService.add(substance);

        assertFalse(added1);
        assertTrue(added);
        assertTrue(added2);
    }

    @Test
    @Order(2)
    void testQuery() {
        AddressSpecification addressSpecification = new AddressSpecification();
        addressSpecification.setAddress("test address");

        Page<Address> page = addressService.query(addressSpecification);
        addressSpecification.setAddress(null);
        Page<Address> page2 = addressService.query(addressSpecification);

        assertEquals(1, page.getTotalRowCount());
        assertEquals(7, page2.getTotalRowCount());
    }

    @Test
    @Order(3)
    void testUpdate() {
        AddressSubstance substance = new AddressSubstance();
        AddressSubstance.TargetOf_addressPart1 targetOfAddressPart1 = new AddressSubstance.TargetOf_addressPart1();
        AddressSubstance.TargetOf_addressPart2 targetOfAddressPart2 = new AddressSubstance.TargetOf_addressPart2();

        targetOfAddressPart1.setId(3);
        targetOfAddressPart2.setId(2);

        substance.setAddress("test address2");
        substance.setAddressPart1(targetOfAddressPart1);
        substance.setAddressPart2(targetOfAddressPart2);

        AddressSubstance.TargetOf_createPerson targetOfCreatePerson = new AddressSubstance.TargetOf_createPerson();
        targetOfCreatePerson.setId(1);
        AddressSubstance.TargetOf_updatePerson targetOfUpdatePerson = new AddressSubstance.TargetOf_updatePerson();
        targetOfUpdatePerson.setId(1);

        substance.setCreatePerson(targetOfCreatePerson);
        substance.setUpdatePerson(targetOfUpdatePerson);

        Boolean added = addressService.add(substance);
        assertTrue(added);

        AddressSpecification addressSpecification = new AddressSpecification();
        addressSpecification.setAddress(substance.getAddress());

        substance.setId(addressService.query(addressSpecification).getRows().getFirst().id());
        substance.setAddress("test address1");

        Boolean updated = addressService.update(substance);
        assertTrue(updated);

        substance.setAddress("测试地址");
        updated = addressService.update(substance);
        assertTrue(updated);
    }

    @Test
    @Order(4)
    void testDelete() {
        AddressForDelete addressForDelete = new AddressForDelete();
        AddressSpecification specification = new AddressSpecification();
        specification.setPageSize(20);
        Page<Address> page;
        List<Integer> list;

        page = addressService.query(specification);

        list = page.getRows().stream().filter(
                addressSubstance -> addressSubstance.id() > 5
        ).map(
                addressSubstance -> Math.toIntExact(addressSubstance.id())
        ).toList();


        addressForDelete.setIds(list);

        assertTrue(addressService.delete(addressForDelete));
        assertEquals(5, addressService.query(new AddressSpecification()).getTotalRowCount());
    }
}
