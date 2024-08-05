package com.moyanshushe.service;

import com.moyanshushe.model.dto.address_part2.AddressPart2ForDelete;
import com.moyanshushe.model.dto.address_part2.AddressPart2Input;
import com.moyanshushe.model.dto.address_part2.AddressPart2Specification;
import com.moyanshushe.model.entity.AddressPart2;
import org.babyfish.jimmer.Page;

/*
 * Author: Napbad
 * Version: 1.0
 */
public interface AddressPart2Service {
    int add(AddressPart2Input addressPart2Input);

    void update(AddressPart2Input addressPart2Input);

    void delete(AddressPart2ForDelete forDelete);

    Page<AddressPart2> query(AddressPart2Specification specification);
}
