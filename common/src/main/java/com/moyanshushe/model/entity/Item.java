package com.moyanshushe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.Nullable;
import org.babyfish.jimmer.sql.*;


import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity for table "item"
 */
@Entity
//        (microServiceName = "common-service")
public interface Item extends BaseEntityWithOnlyUpdate {

    /**
     * 书籍id，主键
     */
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    int id();

    /**
     * 书籍名
     */
    String name();

    /**
     * 书籍价格
     */
    @Nullable
    Double price();

    /**
     * 书籍描述
     */
    @Nullable
    String description();

    /**
     * 书籍状态  0: 正常，1：下架
     */
    Short status();

    // TODO 逻辑处理
    @IdView
    Integer userId();

    /**
     * 书籍所属用户
     */
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    @Nullable
    User user();

    @ManyToMany()
    @JoinTable(
            name = "item_label_mapping",
            joinColumnName = "item_id",
            inverseJoinColumnName = "label_id"
    )
    List<Label> labels();

    @ManyToOne
    Category category();

    @OneToMany(mappedBy = "item")
    List<ItemImage> images();

    @ManyToOne
    @JoinTable(name = "order_item_mapping",
            joinColumnName = "item_id",
            inverseJoinColumnName = "order_id")
    @Nullable
    Order order();

    @Nullable
    Integer amount();

    @OneToOne(mappedBy = "item")
    @Nullable
    ItemCode itemCode();

    @ManyToOne
    @JoinTable(
            name = "member_confirm_item_mapping",
            joinColumnName = "item_id",
            inverseJoinColumnName = "confirm_code"
    )
    @Nullable
    MemberConfirm memberConfirm();

    @Nullable
    LocalDateTime createTime();

    @Default("0")
    @LogicalDeleted(
            "1"
    )
    @Column(
            name = "is_deleted"
    )
    int deleted();
}

