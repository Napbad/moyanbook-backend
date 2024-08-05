package com.moyanshushe.service;

import com.moyanshushe.model.dto.address_part1.AddressPart1ForDelete;
import com.moyanshushe.model.dto.address_part1.AddressPart1Input;
import com.moyanshushe.model.dto.address_part1.AddressPart1Specification;
import com.moyanshushe.model.entity.AddressPart1;
import org.babyfish.jimmer.Page;

/*
 * Author: Napbad
 * Version: 1.0
 */
public interface AddressPart1Service {
    int add(AddressPart1Input addressPart1Input);

    void update(AddressPart1Input addressPart1Input);

    void delete(AddressPart1ForDelete forDelete);

    Page<AddressPart1> query(AddressPart1Specification specification);
}
