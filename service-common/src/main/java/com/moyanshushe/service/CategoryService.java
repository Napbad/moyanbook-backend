package com.moyanshushe.service;

import com.moyanshushe.model.dto.category.*;
import org.babyfish.jimmer.Page;

/*
 * Author: Napbad
 * Version: 1.0
 */
public interface CategoryService {
    void add(CategoryCreateInput category);

    Page<CategorySubstance> query(CategorySpecification labelSpecification);

    void update(CategoryUpdateInput category);

    void delete(CategoryForDelete category);
}
