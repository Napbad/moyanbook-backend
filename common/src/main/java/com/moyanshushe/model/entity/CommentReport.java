package com.moyanshushe.model.entity;

import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.NotNull;


import java.time.LocalDateTime;

/**
 * Entity for table "comment_reports"
 */
@Entity
public interface CommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id();

    @IdView
    int commentId();

    @ManyToOne
    Comment comment();

    @IdView
    int reporterId();

    @NotNull
    @ManyToOne
    User reporter();

    @NotNull
    LocalDateTime reportTime();

    @NotNull
    String reason();

    int status();

    @Default("0")
    @LogicalDeleted(
            value = "1"
    )
    @Column(
            name = "is_deleted"
    )
    int deleted();

    class Status {
        public static final short PENDING = 0;
        public static final short RESOLVING = 1;
        public static final short RESOLVED = 2;

        private Status() {}
    }
}

