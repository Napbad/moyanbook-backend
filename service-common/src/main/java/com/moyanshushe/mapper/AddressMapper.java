package com.moyanshushe.mapper;

import com.moyanshushe.model.dto.address.AddressForDelete;
import com.moyanshushe.model.dto.address.AddressSpecification;
import com.moyanshushe.model.dto.address.AddressSubstance;
import com.moyanshushe.model.entity.*;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.DissociateAction;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.babyfish.jimmer.sql.ast.tuple.Tuple2;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.*;

/*
 * Author: Napbad
 * Version: 1.0
 */

@Component
public class AddressMapper {

    private final JSqlClient jsqlClient;
    private final AddressTable table;
    private final MemberTable memberTable;

    public AddressMapper(JSqlClient jsqlClient) {
        this.jsqlClient = jsqlClient;
        this.table = AddressTable.$;
        this.memberTable = MemberTable.$;
    }


    public SimpleSaveResult<Address> add(Address entity) {
        return jsqlClient.getEntities().save(entity);
    }

    public Optional<AddressSubstance> queryOneByAddress(String address) {
        return jsqlClient.createQuery(table).where(
                table.address().eq(address)
        ).select(
                table.fetch(AddressSubstance.class)
        ).execute().stream().findFirst();
    }

    public SimpleSaveResult<Address> update(Address entity) {
        return jsqlClient.save(entity);
    }

    public @NotNull Page<Address> get(AddressSpecification addressSpecification) {
        return jsqlClient.createQuery(table)
                .where(addressSpecification)
                .select(table)
                .fetchPage(
                        addressSpecification.getPage() == null ? 0 : addressSpecification.getPage(),
                        addressSpecification.getPageSize() == null ? 10 : addressSpecification.getPageSize()
                );
    }

    public void delete(AddressForDelete addressForDelete) {
        Collection<Address> execute = jsqlClient.createQuery(table)
                .where(table.id().in(addressForDelete.getIds()))
                .select(table.fetch(
                                Fetchers.ADDRESS_FETCHER
                                        .member(
                                                Fetchers.MEMBER_FETCHER
                                                        .responsibilityArea()
                                        )
                        )
                ).execute();

        ArrayList<Tuple2<?, ?>> toDelete = new ArrayList<>();

        execute.forEach(address -> {
            if (!address.member().isEmpty()) {
                address.member().forEach(member -> {
                    toDelete.add(new Tuple2<>(address.id(), member.id()));
                });

            }
        });



        jsqlClient.getAssociations(AddressProps.MEMBER)
                .deleteAll(toDelete);

        jsqlClient.deleteByIds(Address.class, addressForDelete.getIds());
    }
}
