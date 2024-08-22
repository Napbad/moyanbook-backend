package com.moyanshushe.utils;

/*
 * Author: Napbad
 * Version: 1.0
 */


import com.moyanshushe.model.dto.item.ItemSubstance;
import com.moyanshushe.model.dto.item_code.ItemCodeSubstance;
import com.moyanshushe.model.entity.Item;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ItemCodeGeneratorTest {
    @Test
    public void test() {
        ItemCodeGenerator.init(100000);

//        UserForVerify user = new UserForVerify();
//        user.setId(1);

        ItemSubstance itemSubstance = new ItemSubstance();
        itemSubstance.setId(1);
        itemSubstance.setName("test");
        itemSubstance.setUser(new ItemSubstance.TargetOf_user());

        itemSubstance.setPrice(1.0);
        assert itemSubstance.getUser() != null;
        itemSubstance.getUser().setId(1);
        itemSubstance.getUser().setName("test");
        itemSubstance.getUser().setAddress(new ItemSubstance.TargetOf_user.TargetOf_address());
        assert itemSubstance.getUser().getAddress() != null;
        itemSubstance.getUser().getAddress().setAddress("Http");
        itemSubstance.getUser().getAddress().setId(10);
        itemSubstance.setCreateTime(LocalDateTime.now());
        ItemSubstance.TargetOf_updatePerson targetOfUpdatePerson = new ItemSubstance.TargetOf_updatePerson();
        targetOfUpdatePerson.setId(1);
        itemSubstance.setUpdatePerson(targetOfUpdatePerson);
        itemSubstance.setUpdateTime(LocalDateTime.now());

        ItemCodeSubstance itemCode =  new ItemCodeSubstance();
        Item entity = itemSubstance.toEntity();
        ItemCodeGenerator.generateItemCode(entity, itemCode);

        System.out.println(itemCode);

    }
}
