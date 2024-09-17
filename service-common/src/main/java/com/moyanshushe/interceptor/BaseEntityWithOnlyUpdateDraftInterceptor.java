package com.moyanshushe.interceptor;
/*
    @Author: Napbad
    @Version: 0.1
    @Date: 8/11/24
    @Description:

*/

import com.moyanshushe.model.entity.*;
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
public class BaseEntityWithOnlyUpdateDraftInterceptor
        implements DraftInterceptor<BaseEntityWithOnlyUpdate, BaseEntityWithOnlyUpdateDraft> {

    @Override
    public void beforeSave(@NotNull BaseEntityWithOnlyUpdateDraft draft, @Nullable BaseEntityWithOnlyUpdate original) {
        log.info(String.valueOf(UserContext.getUserId()));

        draft.setUpdateTime(LocalDateTime.now());
        draft.applyUpdatePerson(user -> {
            user.setId(UserContext.getUserId());
        });
        draft.setUpdatePersonId(UserContext.getUserId());

        log.info(draft.toString());
    }
}