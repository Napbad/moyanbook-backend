package com.moyanshushe.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.babyfish.jimmer.jackson.JsonConverter;
import org.babyfish.jimmer.sql.*;
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.*;
import java.time.LocalDateTime;

/**
 * Entity for table "order"
 */
@Entity
@Table(name = "order_detail")
public interface Order extends BaseEntityWithOnlyUpdate {

    /**
     * 订单id
     */
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    UUID orderId();

    /**
     * 订单所属用户id
     */
    // TODO 逻辑
    @IdView
    Integer userId();

    /**
     * 订单所属用户
     */
    @OneToOne
    @JoinColumn(
            name = "user_id"
    )
    @Nullable
    User user();

    /**
     * 订单状态:  0：正常  1：等待支付 2：支付中  3：订单完成
     */
    Short status();

    /**
     * 订单商品
     */
    @OneToMany(mappedBy = "order")
    List<Item> items();

    /**
     * 逻辑删除字段
     */
    @Default("0")
    @LogicalDeleted(
            value = "1"
    )
    @Column(
            name = "is_deleted"
    )
    int deleted();

    LocalDateTime createTime();

    class Status {
        public static final short NORMAL = 10;
        public static final short WAIT_FOR_PAYING = 20;
        public static final short FINISHED = 30;

        private Status() {}
    }
}

