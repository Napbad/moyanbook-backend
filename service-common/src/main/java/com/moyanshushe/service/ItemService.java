package com.moyanshushe.service;

import com.moyanshushe.model.dto.item.ItemForAdd;
import com.moyanshushe.model.dto.item.ItemForDelete;
import com.moyanshushe.model.dto.item.ItemForUpdate;
import com.moyanshushe.model.dto.item.ItemSpecification;
import com.moyanshushe.model.entity.Item;
import org.babyfish.jimmer.Page;

/*
 * Author: Napbad
 * Version: 1.0
 */
public interface ItemService {
    Boolean add(ItemForAdd itemForAdd);

    Boolean delete(ItemForDelete itemForDelete);

    Boolean update(ItemForUpdate itemForUpdate);

    Page<Item> query(ItemSpecification specification);

    Page<Item> queryPublic(ItemSpecification specification);
}
