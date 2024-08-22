package com.moyanshushe.service;

import com.moyanshushe.model.dto.address_part1.AddressPart1CreateInput;
import com.moyanshushe.model.dto.address_part1.AddressPart1ForDelete;
import com.moyanshushe.model.dto.address_part1.AddressPart1Specification;
import com.moyanshushe.model.dto.address_part1.AddressPart1UpdateInput;
import com.moyanshushe.model.entity.AddressPart1;
import org.babyfish.jimmer.Page;

/*
 * Author: Napbad
 * Version: 1.0
 */
public interface AddressPart1Service {
    Integer add(AddressPart1CreateInput addressPart1Input);

    void update(AddressPart1UpdateInput addressPart1Input);

    void delete(AddressPart1ForDelete forDelete);

    Page<AddressPart1> query(AddressPart1Specification specification);
}
