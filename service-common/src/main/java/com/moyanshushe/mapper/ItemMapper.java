package com.moyanshushe.mapper;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.exception.NoAuthorityException;
import com.moyanshushe.model.OrderRule;
import com.moyanshushe.model.dto.item.*;
import com.moyanshushe.model.entity.Item;
import com.moyanshushe.model.entity.ItemFetcher;
import com.moyanshushe.model.entity.ItemTable;
import com.moyanshushe.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ItemMapper {

    private final ItemTable table;
    private final JSqlClient jsqlClient;

    public ItemMapper(JSqlClient jsqlClient) {
        this.table = ItemTable.$;
        this.jsqlClient = jsqlClient;
    }

    public SimpleSaveResult<Item> add(ItemForAdd itemForAdd) {
        return jsqlClient.insert(itemForAdd);
    }

    public Integer delete(ItemForDelete itemForDelete) {
        return jsqlClient.deleteByIds(Item.class, itemForDelete.getIds()).getAffectedRowCount(Item.class);
    }

    public SimpleSaveResult<Item> update(ItemForUpdate itemForUpdate) {
        List<Long> num = jsqlClient.createQuery(table)
                .where(table.id().eq(itemForUpdate.getId()))
                .where(table.userId().eq(UserContext.getUserId()))
                .select(table.count())
                .execute();
        if (num.isEmpty()) {
            throw new NoAuthorityException();
        }
        return jsqlClient.update(itemForUpdate.toEntity());
    }

    public @NotNull Page<Item> fetch(ItemSpecification specification, ItemFetcher fetcher) {
        return jsqlClient.createQuery(table)
                .where(specification)
                .orderByIf(
                        specification.getOrderByCreateTime() != null,
                        specification.getOrderByCreateTime() == OrderRule.ASC ?
                                table.createTime().asc() :
                                table.createTime().desc()
                )
                .orderByIf(
                        specification.getOrderByUpdateTime() != null,
                        specification.getOrderByUpdateTime() == OrderRule.ASC ?
                                table.updateTime().asc() :
                                table.updateTime().desc()
                ).orderByIf(
                        specification.getOrderByPrice() != null,
                        specification.getOrderByPrice() == OrderRule.ASC ?
                                table.price().asc() :
                                table.price().desc()
                )
                .select(
                        table.fetch(
                                fetcher
                        )
                )
                .fetchPage(
                        specification.getPage() != null ? specification.getPage() : 0,
                        specification.getPageSize() != null ? specification.getPageSize() : 10
                );
    }
}
