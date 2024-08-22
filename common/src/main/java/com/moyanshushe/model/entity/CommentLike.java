package com.moyanshushe.model.entity;

import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.NotNull;


import java.time.LocalDateTime;

/**
 * Entity for table "comment_likes"
 */
@Entity
public interface CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id();

    @IdView
    int userId();

    @ManyToOne
    User user();

    @IdView
    int commentId();

    @ManyToOne
    Comment comment();

    LocalDateTime likeDate();
}

