package com.moyanshushe.service;

import com.moyanshushe.model.dto.item.*;
import com.moyanshushe.model.entity.Item;
import org.babyfish.jimmer.Page;
import org.jetbrains.annotations.NotNull;

/*
 * Author: Napbad
 * Version: 1.0
 */
public interface ItemService {
    Item add(ItemForAdd itemForAdd);

    Boolean delete(ItemForDelete itemForDelete);

    Item update(ItemForUpdate itemForUpdate);

    @NotNull Page<ItemView> query(ItemSpecification specification);

    Page<ItemPublicView> queryPublic(PublicItemSpecification specification);
}
