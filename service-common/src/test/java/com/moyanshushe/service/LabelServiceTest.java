package com.moyanshushe.service;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.model.dto.label.LabelForDelete;
import com.moyanshushe.model.dto.label.LabelInput;
import com.moyanshushe.model.dto.label.LabelSpecification;
import com.moyanshushe.model.dto.label.LabelSubstance;
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
class LabelServiceTest {

    @Autowired
    LabelService labelService;

    @Test
    @Order(1)
    void addTest() {
        LabelInput labelInput = new LabelInput();
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
        LabelSpecification labelSpecification = new LabelSpecification();
        labelSpecification.setName("test label");

        Page<LabelSubstance> labelSubstances = labelService.query(labelSpecification);

        assertEquals(2, labelSubstances.getTotalRowCount());

        labelSpecification.setName("test label1");

        labelSubstances = labelService.query(labelSpecification);

        assertEquals(1, labelSubstances.getTotalRowCount());
    }

    @Test
    @Order(3)
    void updateTest() {
        LabelInput labelInput = new LabelInput();

        LabelSpecification labelSpecification = new LabelSpecification();
        labelSpecification.setName("test label1");

        Page<LabelSubstance> labelSubstances = labelService.query(labelSpecification);

        assertEquals(1, labelSubstances.getTotalRowCount());

        LabelSubstance substance = labelSubstances.getRows().getFirst();

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
        LabelForDelete delete = new LabelForDelete();

        LabelSpecification labelSpecification = new LabelSpecification();

        Page<LabelSubstance> page = labelService.query(labelSpecification);

        delete.setIds(page.getRows().stream()
                .map(LabelSubstance::getId)
                .filter(id -> id > 5)
                .toList());

        Boolean deleted = labelService.delete(delete);
        assertEquals(5, labelService.query(labelSpecification).getTotalRowCount());
    }
}
