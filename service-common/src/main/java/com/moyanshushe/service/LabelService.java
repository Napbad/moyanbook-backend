package com.moyanshushe.service;

import com.moyanshushe.model.dto.label.LabelForDelete;
import com.moyanshushe.model.dto.label.LabelSpecification;
import com.moyanshushe.model.dto.label.LabelInput;
import com.moyanshushe.model.dto.label.LabelSubstance;
import org.babyfish.jimmer.Page;

/*
 * Author: Napbad
 * Version: 1.0
 */
public interface LabelService {
    Boolean add(LabelInput label);

    Page<LabelSubstance> query(LabelSpecification labelSpecification);

    Boolean update(LabelInput label);

    Boolean delete(LabelForDelete label);
}
