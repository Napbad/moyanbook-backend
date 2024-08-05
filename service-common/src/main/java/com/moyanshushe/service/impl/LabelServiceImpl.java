package com.moyanshushe.service.impl;

import com.moyanshushe.constant.CommonConstant;
import com.moyanshushe.exception.common.DBException;
import com.moyanshushe.exception.common.InputInvalidException;
import com.moyanshushe.exception.UnexpectedException;
import com.moyanshushe.exception.label.LabelExistsException;
import com.moyanshushe.mapper.LabelMapper;
import com.moyanshushe.model.dto.label.LabelForDelete;
import com.moyanshushe.model.dto.label.LabelSpecification;
import com.moyanshushe.model.dto.label.LabelInput;
import com.moyanshushe.model.dto.label.LabelSubstance;
import com.moyanshushe.model.entity.Label;
import com.moyanshushe.service.LabelService;
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
public class LabelServiceImpl implements LabelService {

    private static final Logger log = LoggerFactory.getLogger(LabelServiceImpl.class);
    private final LabelMapper labelMapper;

    public LabelServiceImpl(LabelMapper labelMapper) {
        this.labelMapper = labelMapper;
    }

    @Override
    public Boolean add(LabelInput label) {
        List<Integer> fetchResult = labelMapper.fetchLabelIds(label);

        if (fetchResult == null) {
            log.warn("id should not be null");
            throw new UnexpectedException();

        } else if (fetchResult.isEmpty()) {
            SimpleSaveResult<Label> result = labelMapper.addLabel(label);
            return result.getTotalAffectedRowCount() == 1;

        } else {
            throw new LabelExistsException();
        }
    }

    @Override
    public Page<LabelSubstance> query(LabelSpecification labelSpecification) {
        return labelMapper.queryLabels(labelSpecification);
    }

    @Override
    public Boolean update(LabelInput label) {
        if (label.getId() == null) {
            log.warn("id should not be null in update label");
            throw new InputInvalidException();
        }

        SimpleSaveResult<Label> result = labelMapper.updateLabel(label);

        return result.getTotalAffectedRowCount() == 1;
    }

    @Override
    public Boolean delete(LabelForDelete label) {
        DeleteResult result = labelMapper.deleteLabel(label);

        if (result.getTotalAffectedRowCount() != label.getIds().size()) {
            log.warn("delete label failed");
            throw new DBException(CommonConstant.DB_WRITE_EXCEPTION);
        } else {
            return true;
        }
    }
}
