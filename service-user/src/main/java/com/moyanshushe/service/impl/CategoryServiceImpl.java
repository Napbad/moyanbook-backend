package com.moyanshushe.service.impl;

import com.moyanshushe.constant.CommonConstant;
import com.moyanshushe.exception.common.DBException;
import com.moyanshushe.exception.common.InputInvalidException;
import com.moyanshushe.exception.UnexpectedException;
import com.moyanshushe.exception.category.CategoryExistsException;
import com.moyanshushe.mapper.CategoryMapper;
import com.moyanshushe.model.dto.category.*;
import com.moyanshushe.model.entity.Category;
import com.moyanshushe.service.CategoryService;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.ast.mutation.DeleteResult;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Author: Napbad
 * Version: 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryMapper labelMapper;

    public CategoryServiceImpl(CategoryMapper labelMapper) {
        this.labelMapper = labelMapper;
    }

    @Override
    public void add(CategoryCreateInput category) {
        List<Integer> fetchResult = labelMapper.fetchCategoryIds(category);

        if (fetchResult == null) {
            log.warn("id should not be null");
            throw new UnexpectedException();

        } else if (fetchResult.isEmpty()) {
            SimpleSaveResult<Category> result = labelMapper.addCategory(category);

        } else {
            throw new CategoryExistsException();
        }
    }

    @Override
    public Page<CategorySubstance> query(CategorySpecification labelSpecification) {
        return labelMapper.queryCategories(labelSpecification);
    }

    @Override
    public void update(CategoryUpdateInput category) {
        if (category.getId() == null) {
            log.warn("id should not be null in update category");
            throw new InputInvalidException();
        }

        labelMapper.updateCategory(category);
    }

    @Override
    public void delete(CategoryForDelete category) {
        DeleteResult result = labelMapper.deleteCategory(category);

        if (result.getTotalAffectedRowCount() != category.getIds().size()) {
            log.warn("delete category failed");
            throw new DBException(CommonConstant.DB_WRITE_EXCEPTION);
        }
    }
}
