package com.moyanshushe.service;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.model.dto.address.AddressSubstance;
import com.moyanshushe.model.dto.item.ItemForAdd;
import com.moyanshushe.model.dto.item.ItemForDelete;
import com.moyanshushe.model.dto.item.ItemForUpdate;
import com.moyanshushe.model.dto.item.ItemSpecification;
import com.moyanshushe.model.entity.Item;
import com.moyanshushe.utils.JsonToObjectUtil;
import org.babyfish.jimmer.Page;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemServiceImplTest {

    @Autowired
    private ItemService itemService;

    @Test
    @Order(1)
    void testAdd() {
        ItemForAdd itemForAdd = new ItemForAdd();
        ItemForAdd.TargetOf_labels targetOfCategories = new ItemForAdd.TargetOf_labels();
        ItemForAdd.TargetOf_images targetOfImages = new ItemForAdd.TargetOf_images();
        targetOfCategories.setId(1);
        targetOfImages.setImageUrl("http://test.moyanshushe.napbad.com");


        itemForAdd.setName("test item1");
        itemForAdd.setCategories(List.of(targetOfCategories));
        itemForAdd.setUserId(1);
        itemForAdd.setPrice(100.00);
        itemForAdd.setDescription("description");
        itemForAdd.setImages(List.of(targetOfImages));
        itemForAdd.setStatus(Item.Status.IN_USER);

        Boolean added = itemService.add(itemForAdd);

        itemForAdd.setName("test item2");
        Boolean added2 = itemService.add(itemForAdd);

        assertTrue(added);
        assertTrue(added2);
    }

    @Test
    @Order(2)
    void testQuery() {
        ItemSpecification specification = new ItemSpecification();

        Page<Item> page = itemService.query(specification);

        assertEquals(7, page.getTotalRowCount());

        specification.setName("test item");

        Page<Item> page1 = itemService.query(specification);

        assertEquals(2, page1.getTotalRowCount());
    }

    @Test
    @Order(3)
    void testUpdate() {
        ItemForUpdate itemForUpdate = JsonToObjectUtil.jsonFileToObject("ItemForUpdate", ItemForUpdate.class);

        ItemForUpdate.TargetOf_updatePerson targetOfUpdatePerson = new ItemForUpdate.TargetOf_updatePerson();
        targetOfUpdatePerson.setId(1);

        itemForUpdate.setUpdatePerson(targetOfUpdatePerson);

        Boolean update = itemService.update(itemForUpdate);
        assertTrue(update);
    }

    @Test
    @Order(4)
    void testDelete() {
        ItemSpecification itemSpecification = new ItemSpecification();

        Page<Item> page = itemService.query(itemSpecification);

        assertTrue(page.getTotalRowCount() > 1);

        List<Integer> list = page.getRows()
                .stream()
                .map(Item::id)
                .filter(id -> id > 5).toList();

        ItemForDelete itemForDelete = new ItemForDelete();
        itemForDelete.setIds(list);

        Boolean deleted = itemService.delete(itemForDelete);

        assertTrue(deleted);
    }


}
