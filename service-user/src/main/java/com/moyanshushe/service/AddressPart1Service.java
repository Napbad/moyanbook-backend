package com.moyanshushe.service;

import com.moyanshushe.model.dto.address_part1.*;
import org.babyfish.jimmer.Page;

/*
 * Author: Napbad
 * Version: 1.0
 */
public interface AddressPart1Service {
    Integer add(AddressPart1CreateInput addressPart1Input);

    void update(AddressPart1UpdateInput addressPart1Input);

    void delete(AddressPart1ForDelete forDelete);

    Page<AddressPart1View> query(AddressPart1Specification specification);
}
