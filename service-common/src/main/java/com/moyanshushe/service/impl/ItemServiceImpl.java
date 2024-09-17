package com.moyanshushe.service.impl;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.mapper.ItemMapper;
import com.moyanshushe.model.dto.item.*;
import com.moyanshushe.model.entity.Item;
import com.moyanshushe.model.entity.Fetchers;
import com.moyanshushe.model.entity.ItemDraft;
import com.moyanshushe.model.entity.ItemTable;
import com.moyanshushe.service.ItemService;
import com.moyanshushe.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemMapper mapper;
    private final ItemTable table;
    private final JSqlClient jSqlClient;

    public ItemServiceImpl(ItemMapper mapper, JSqlClient jSqlClient) {
        this.mapper = mapper;
        this.jSqlClient = jSqlClient;
        table = ItemTable.$;
    }

    @Override
    @NotNull
    @Transactional(rollbackFor = Exception.class)
    public Item add(ItemForAdd itemForAdd) {
        log.info("添加物品: {}", itemForAdd);

        SimpleSaveResult<Item> result = jSqlClient.insert(
                ItemDraft.$.produce(itemForAdd.toEntity(), draft -> {
                    draft.setUserId(UserContext.getUserId());
                })
        );

        Item modifiedEntity = result.getModifiedEntity();

        log.info("添加物品成功");
        return modifiedEntity;
    }

    @Override
    @NotNull
    public Boolean delete(ItemForDelete itemForDelete) {
        log.info("删除物品: {}", itemForDelete.getIds());

        Integer delete = jSqlClient.createDelete(table)
                .where(table.id().in(itemForDelete.getIds()))
                .where(table.userId().eq(itemForDelete.getOperatorId()))
                .execute();

        log.info("删除 {} 个物品成功: {}", delete, itemForDelete.getIds());
        return true;
    }

    @Override
    @NotNull
    public Item update(ItemForUpdate itemForUpdate) {
        log.info("更新物品: {}", itemForUpdate.getId());

        SimpleSaveResult<Item> result = jSqlClient.update(
                ItemDraft.$.produce(
                        itemForUpdate.toEntity(), draft -> {
                            draft.setUserId(UserContext.getUserId());
                        }
                )
        );

        log.info("更新物品成功");
        log.info("from {} \n to \n {}", result.getOriginalEntity(), result.getModifiedEntity());
        return result.getModifiedEntity();
    }

    @Override
    public @NotNull Page<ItemView> query(ItemSpecification specification) {
        log.info("查询物品(personal): {}", specification);

        return jSqlClient.createQuery(table)
                .where(specification)
                .where(table.userId().eq(UserContext.getUserId()))
                .select(table.fetch(
                        ItemView.class
                )).fetchPage(
                        specification.getPage() != null ? specification.getPage() : 0,
                        specification.getPageSize() != null ? specification.getPageSize() : 10
                );
    }

    @Override
    public Page<Item>   queryPublic(ItemSpecification specification) {
        log.info("查询物品: {}", specification);
        return mapper.fetch(specification,
                Fetchers.ITEM_FETCHER
                        .userId()
                        .images()
                        .category()
                        .description()
                        .name()
                        .status()
                        .price()
                        .user(Fetchers.USER_FETCHER
                                .name()
                                .type()
                                .profileUrl()
                        )
        );
    }
}
