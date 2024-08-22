package com.moyanshushe.service;

import com.moyanshushe.model.dto.address_part2.AddressPart2CreateInput;
import com.moyanshushe.model.dto.address_part2.AddressPart2ForDelete;
import com.moyanshushe.model.dto.address_part2.AddressPart2Specification;
import com.moyanshushe.model.dto.address_part2.AddressPart2UpdateInput;
import com.moyanshushe.model.entity.AddressPart2;
import org.babyfish.jimmer.Page;
import org.jetbrains.annotations.NotNull;

/*
 * Author: Napbad
 * Version: 1.0
 */
public interface AddressPart2Service {
    @NotNull Integer add(AddressPart2CreateInput addressPart2Input);

    void update(AddressPart2UpdateInput addressPart2Input);

    void delete(AddressPart2ForDelete forDelete);

    Page<AddressPart2> query(AddressPart2Specification specification);
}
