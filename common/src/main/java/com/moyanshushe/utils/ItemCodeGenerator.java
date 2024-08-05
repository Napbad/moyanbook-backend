package com.moyanshushe.utils;

import com.moyanshushe.model.entity.Item;
import com.moyanshushe.model.dto.item_code.*;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class ItemCodeGenerator {
    private static int code;

    public static void init(int code_init) {
        code = code_init;
    }

    public static String generateItemCode(Item item) {
        StringBuilder sb = new StringBuilder();

        sb.append(item.user().address().id())
                .append("-")
                .append(code);

        return sb.toString();
    }

    public static void generateItemCode(Item item, ItemCodeSubstance itemCode) {
        StringBuilder sb = new StringBuilder();

        itemCode.setCodePart1(item.user().address().id());
        itemCode.setCodePart2(code);
    }
}
