package com.moyanshushe.service.impl;

import com.moyanshushe.model.dto.address_part2.*;
import com.moyanshushe.model.entity.AddressPart2;
import com.moyanshushe.model.entity.AddressPart2Table;
import com.moyanshushe.service.AddressPart2Service;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/*
 * Author: Napbad
 * Version: 1.0
 */

@Service
public class AddressPart2ServiceImpl implements AddressPart2Service {

    private final JSqlClient jsqlClient;
    private final AddressPart2Table table;

    public AddressPart2ServiceImpl(JSqlClient jsqlClient) {
        this.jsqlClient = jsqlClient;
        this.table = AddressPart2Table.$;
    }

    @Override
    public @NotNull Integer add(AddressPart2CreateInput addressPart2Input) {
        SimpleSaveResult<AddressPart2> result = jsqlClient.insert(addressPart2Input);

        return result.getModifiedEntity().id();
    }

    @Override
    public void update(AddressPart2UpdateInput addressPart2Input) {
        jsqlClient.update(addressPart2Input);
    }

    @Override
    public void delete(AddressPart2ForDelete forDelete) {
        jsqlClient.deleteByIds(AddressPart2.class, forDelete.getIds());
    }

    @Override
    public Page<AddressPart2View> query(AddressPart2Specification specification) {

        return jsqlClient.createQuery(table)
                .where(specification)
                .select(table.fetch(AddressPart2View.class))
                .fetchPage(
                        specification.getPage() == null ? 0 : specification.getPage(),
                        specification.getPageSize() == null ? 10 : specification.getPageSize()
                );
    }
}
