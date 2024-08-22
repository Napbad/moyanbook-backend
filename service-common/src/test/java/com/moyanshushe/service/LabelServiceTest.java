package com.moyanshushe.service;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.model.dto.category.CategoryForDelete;
import com.moyanshushe.model.dto.category.CategoryInput;
import com.moyanshushe.model.dto.category.CategorySpecification;
import com.moyanshushe.model.dto.category.CategorySubstance;
import org.babyfish.jimmer.Page;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryServiceTest {

    @Autowired
    CategoryService labelService;

    @Test
    @Order(1)
    void addTest() {
        CategoryInput labelInput = new CategoryInput();
        labelInput.setName("test label1");

        Boolean added = labelService.add(labelInput);
        assertTrue(added);

        try {
            labelService.add(labelInput);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        labelInput.setName("test label2");
        Boolean added2 = labelService.add(labelInput);

        assertTrue(added2);
    }

    @Test
    @Order(2)
    void getTest() {
        CategorySpecification labelSpecification = new CategorySpecification();
        labelSpecification.setName("test category");

        Page<CategorySubstance> labelSubstances = labelService.query(labelSpecification);

        assertEquals(2, labelSubstances.getTotalRowCount());

        labelSpecification.setName("test label1");

        labelSubstances = labelService.query(labelSpecification);

        assertEquals(1, labelSubstances.getTotalRowCount());
    }

    @Test
    @Order(3)
    void updateTest() {
        CategoryInput labelInput = new CategoryInput();

        CategorySpecification labelSpecification = new CategorySpecification();
        labelSpecification.setName("test label1");

        Page<CategorySubstance> labelSubstances = labelService.query(labelSpecification);

        assertEquals(1, labelSubstances.getTotalRowCount());

        CategorySubstance substance = labelSubstances.getRows().getFirst();

        labelInput.setName("test label1 - updated");
        labelInput.setId(substance.getId());

        Boolean updated = labelService.update(labelInput);

        assertTrue(updated);

        labelSubstances = labelService.query(labelSpecification);

        assertEquals(1, labelSubstances.getTotalRowCount());
        assertEquals("test label1 - updated", labelSubstances.getRows().getFirst().getName());
        assertEquals(substance.getId(), labelSubstances.getRows().getFirst().getId());
    }

    @Test
    @Order(4)
    void testDelete() {
        CategoryForDelete delete = new CategoryForDelete();

        CategorySpecification labelSpecification = new CategorySpecification();

        Page<CategorySubstance> page = labelService.query(labelSpecification);

        delete.setIds(page.getRows().stream()
                .map(CategorySubstance::getId)
                .filter(id -> id > 5)
                .toList());

        Boolean deleted = labelService.delete(delete);
        assertEquals(5, labelService.query(labelSpecification).getTotalRowCount());
    }
}
