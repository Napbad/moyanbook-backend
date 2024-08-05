package com.moyanshushe.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity for table "comment"
 */
@Entity
public interface Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id();

    String content();

    @IdView
    Integer commenterId();

    @Nullable
    @ManyToOne
    User commenter();

    @IdView
    int itemId();

    @ManyToOne
    Item item();

    @IdView
    @Nullable
    Integer parentId();

    LocalDateTime commentTime();

    @IdView("commentLike")
    List<Integer> commentLikeId();

    @OneToMany(mappedBy = "comment")
    List<CommentLike> commentLike();

    @IdView("commentHistory")
    List<Integer> commentHistoryId();

    @OneToMany(mappedBy = "comment")
    List<CommentHistory> commentHistory();

    @IdView("commentReports")
    List<Integer> commentReportsId();

    @OneToMany(mappedBy = "comment")
    List<CommentReport> commentReports();

    @Nullable
    @ManyToOne
    Comment parent();

    @OneToMany(mappedBy = "parent")
    List<Comment> children();

    int likes();

    int dislikes();

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
        public static final int HIDDEN = 0;
        public static final int VISIBLE = 1;
        public static final int DELETED = 2;

        private Status() {}
    }
}

