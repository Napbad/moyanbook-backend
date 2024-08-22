package com.moyanshushe.model.entity;

import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.time.LocalDateTime;

/**
 * Entity for table "comment_history"
 */
@Entity
public interface CommentHistory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    int id();

    @IdView
    int commentId();

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    Comment comment();

    String content();

    int modifiedBy();

    @Nullable
    LocalDateTime modifiedTime();

    @Default("0")
    @LogicalDeleted(
            value = "1"
    )
    @Column(
            name = "is_deleted"
    )
    int deleted();
}

