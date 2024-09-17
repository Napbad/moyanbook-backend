package com.moyanshushe.interceptor;



/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/11/24
    @Description: 

*/

import com.moyanshushe.model.entity.BaseEntity;
import com.moyanshushe.model.entity.BaseEntityDraft;
import com.moyanshushe.model.entity.BaseEntityProps;
import com.moyanshushe.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.ImmutableObjects;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class BaseEntityDraftInterceptor
        implements DraftInterceptor<BaseEntity, BaseEntityDraft> {

    @Override
    public void beforeSave(@NotNull BaseEntityDraft draft, @Nullable BaseEntity original) {
        draft.setUpdateTime(LocalDateTime.now());
        draft.applyUpdatePerson(user -> {
            user.setId(UserContext.getUserId());
        });
        draft.setUpdatePersonId(UserContext.getUserId());
        if (original == null) {
            if (!ImmutableObjects.isLoaded(draft, BaseEntityProps.CREATE_TIME)) {
                draft.setCreateTime(LocalDateTime.now());
            }
            if (!ImmutableObjects.isLoaded(draft, BaseEntityProps.CREATE_PERSON)) {
                draft.applyCreatePerson(user -> {
                    user.setId(UserContext.getUserId());
                });
            }
            if (!ImmutableObjects.isLoaded(draft, BaseEntityProps.CREATE_PERSON_ID)) {
                draft.setCreatePersonId(UserContext.getUserId());
            }
        }

        log.info(draft.toString());
    }
}