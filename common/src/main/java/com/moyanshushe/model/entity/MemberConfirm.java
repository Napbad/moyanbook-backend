package com.moyanshushe.model.entity;

import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Entity for table "member_confirm"
 */
@Entity
public interface MemberConfirm {

    /**
     * 主键
     */
    @Id
    int id();

    /**
     * 确定码
     */
    String confirmCode();

    /**
     * 状态
     */
    Integer status();

    /**
     * 操作，接收或者送出
     */
    Integer operation();

    @OneToOne
    @JoinColumn(
            name = "member_id"
    )
    @Nullable
    Member member();

    @OneToMany(mappedBy = "memberConfirm")
    List<Item> items();

    @Default("0")
    @LogicalDeleted(
            value = "1"
    )
    @Column(
            name = "is_deleted"
    )
    int deleted();

    class Status {
        public static final int WAIT_FOR_CONFIRM = 1;
        public static final int CONFIRMED = 2;
    }
}

