package com.moyanshushe.mapper;

import com.moyanshushe.model.dto.category.*;
import com.moyanshushe.model.entity.Category;
import com.moyanshushe.model.entity.CategoryTable;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.DeleteResult;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * Author: Napbad
 * Version: 1.0
 */

@Slf4j
@Component
public class CategoryMapper {

    private final JSqlClient jSqlClient;
    private final CategoryTable table;

    public CategoryMapper(JSqlClient jSqlClient) {
        this.table = CategoryTable.$;
        this.jSqlClient = jSqlClient;
    }

    public SimpleSaveResult<Category> addCategory(CategoryCreateInput category) {

        return jSqlClient.insert(category);
    }

    public List<Integer> fetchCategoryIds(CategoryCreateInput category) {
        return jSqlClient.createQuery(table)
                .where(
                        table.name().eq(category.getName())
                )
                .select(
                        table.id()
                ).execute();
    }

    public @NotNull Page<CategorySubstance> queryCategories(CategorySpecification category) {
        return jSqlClient.createQuery(
                        table
                )
                .whereIf(
                        category.getName() != null,
                        () -> table.name().like(category.getName()))
                .whereIf(
                        category.getIds() != null && !category.getIds().isEmpty(),
                        () -> table.id().in(category.getIds())
                )
                .select(
                        table.fetch(CategorySubstance.class)
                ).fetchPage(category.getPage() != null ? category.getPage() : 0,
                        category.getPageSize() != null ? category.getPageSize() : 10);

    }

    public void updateCategory(CategoryUpdateInput category) {
        jSqlClient.update(category);
    }

    public DeleteResult deleteCategory(CategoryForDelete category) {
        return jSqlClient.deleteByIds(Category.class, category.getIds());
    }
}
