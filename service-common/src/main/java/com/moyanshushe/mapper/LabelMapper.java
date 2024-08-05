package com.moyanshushe.mapper;

import com.moyanshushe.model.dto.label.LabelForDelete;
import com.moyanshushe.model.dto.label.LabelSpecification;
import com.moyanshushe.model.dto.label.LabelInput;
import com.moyanshushe.model.dto.label.LabelSubstance;
import com.moyanshushe.model.entity.Label;
import com.moyanshushe.model.entity.LabelTable;
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
public class LabelMapper {

    private final JSqlClient jSqlClient;
    private final LabelTable table;

    public LabelMapper(JSqlClient jSqlClient) {
        this.table = LabelTable.$;
        this.jSqlClient = jSqlClient;
    }

    public SimpleSaveResult<Label> addLabel(LabelInput label) {

        return jSqlClient.save(label.toEntity());
    }

    public List<Integer> fetchLabelIds(LabelInput label) {
        return jSqlClient.createQuery(table)
                .where(
                        table.name().eq(label.getName())
                )
                .select(
                        table.id()
                ).execute();
    }

    public @NotNull Page<LabelSubstance> queryLabels(LabelSpecification label) {
        return jSqlClient.createQuery(
                        table
                )
                .whereIf(
                        label.getName() != null,
                        () -> table.name().like(label.getName()))
                .whereIf(
                        label.getIds() != null && !label.getIds().isEmpty(),
                        () -> table.id().in(label.getIds())
                )
                .select(
                        table.fetch(LabelSubstance.class)
                ).fetchPage(label.getPage() != null ? label.getPage() : 0,
                        label.getPageSize() != null ? label.getPageSize() : 10);

    }

    public SimpleSaveResult<Label> updateLabel(LabelInput label) {
        return jSqlClient.save(label);
    }

    public DeleteResult deleteLabel(LabelForDelete label) {
        return jSqlClient.deleteByIds(Label.class, label.getIds());
    }
}
