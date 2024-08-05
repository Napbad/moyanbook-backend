package com.moyanshushe.config;

import org.babyfish.jimmer.sql.runtime.ScalarProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

/*
 * Author: Napbad
 * Version: 1.0
 */

@Component
public class UUIDScalarProvider implements ScalarProvider<UUID, String> {

    @Override
    public UUID toScalar(@NotNull String sqlValue) {
        return UUID.fromString(sqlValue);
    }

    @Override
    public String toSql(UUID scalarValue) {
        return scalarValue.toString();
    }
}