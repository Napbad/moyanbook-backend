package com.moyanshushe.service.impl;

import com.moyanshushe.model.dto.address_part1.*;
import com.moyanshushe.model.entity.AddressPart1;
import com.moyanshushe.model.entity.AddressPart1Table;
import com.moyanshushe.service.AddressPart1Service;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.springframework.stereotype.Service;

/*
 * Author: Napbad
 * Version: 1.0
 */

@Service
public class AddressPart1ServiceImpl implements AddressPart1Service {

    private final JSqlClient jsqlClient;
    private final AddressPart1Table table;

    public AddressPart1ServiceImpl(JSqlClient jsqlClient) {
        this.jsqlClient = jsqlClient;
        this.table = AddressPart1Table.$;
    }

    @Override
    public Integer add(AddressPart1CreateInput addressPart1Input) {
        SimpleSaveResult<AddressPart1> result = jsqlClient.insert(addressPart1Input);

        return result.getModifiedEntity().id();
    }

    @Override
    public void update(AddressPart1UpdateInput addressPart1Input) {
        jsqlClient.update(addressPart1Input);
    }

    @Override
    public void delete(AddressPart1ForDelete forDelete) {
        jsqlClient.deleteByIds(AddressPart1.class, forDelete.getIds());
    }

    @Override
    public Page<AddressPart1View> query(AddressPart1Specification specification) {

        return jsqlClient.createQuery(table)
                .where(specification)
                .select(table.fetch(AddressPart1View.class))
                .fetchPage(
                        specification.getPage() == null ? 0 : specification.getPage(),
                        specification.getPageSize() == null ? 10 : specification.getPageSize()
                );
    }
}
