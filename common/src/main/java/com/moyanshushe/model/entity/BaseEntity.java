package com.moyanshushe.model.entity;

/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/7/24 2:53 PM
    @Description: 

*/

import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@MappedSuperclass
public interface BaseEntity {

    LocalDateTime createTime();

    LocalDateTime updateTime();

    @Nullable
    @IdView
    Integer createPersonId();

    @Nullable
    @IdView
    Integer updatePersonId();

    @Nullable
    @ManyToOne
    @OnDissociate(DissociateAction.SET_NULL)
    User createPerson();

    @Nullable
    @ManyToOne
    @OnDissociate(DissociateAction.SET_NULL)
    User updatePerson();
}