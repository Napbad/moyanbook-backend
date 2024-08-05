package com.moyanshushe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moyanshushe.model.cnoverter.PasswordConverter;
import jakarta.annotation.Nullable;
import org.babyfish.jimmer.jackson.JsonConverter;
import org.babyfish.jimmer.sql.*;


import java.time.LocalDate;
import java.util.List;

/**
 * 用户实体，对应数据库表"user"
 */
@Entity
public interface User {

    /**
     * 获取用户id，主键
     * @return 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id();

    /**
     * 获取用户名
     * @return 用户名
     */
    String name();

    /**
     * 获取用户年龄
     * @return 用户年龄，可能为null
     */
    @Nullable
    Integer age();

    /**
     * 获取用户性别
     * @return 用户性别，可能为null
     */
    @Nullable
    Short gender();

    /**
     * 获取用户邮箱
     * @return 用户邮箱，可能为null
     */
    @Nullable
//    @JsonConverter(UserEmailConverter.class)
    String email();

    /**
     * 获取用户手机号
     * @return 用户手机号，可能为null
     */
    @Nullable
//    @JsonConverter(UserPhoneConverter.class)
    String phone();

    /**
     * 获取用户密码
     * @return 用户密码
     */
    @JsonConverter(PasswordConverter.class)
    String password();

    /**
     * 获取用户拥有的物品列表
     * @return 物品列表
     */
    @OneToMany(mappedBy = "user")
    List<Item> items();

    /**
     * 获取用户的地址信息
     * @return 地址信息
     */
    @ManyToOne
    @OnDissociate(DissociateAction.SET_NULL)
    @Nullable
    Address address();

    /**
     * 获取用户拥有的优惠券列表
     * @return 优惠券列表
     */
    @OneToMany(mappedBy = "user")
    List<Coupon> coupons();

    /**
     * 获取用户的订单信息
     * @return 订单信息
     */
    @OneToOne(mappedBy = "user")
    @Nullable
    Order order();

    /**
     * 获取用户状态：0表示正常，1表示冻结，2表示过期
     * @return 用户状态，可能为null
     */
    @Nullable
    Short status();


    int type();

    /**
     * 获取用户创建时间
     * @return 用户创建时间
     */
    LocalDate createTime();

    /**
     * 获取用户更新时间
     * @return 用户更新时间
     */
    LocalDate updateTime();

    /**
     * 获取用户头像网址
     * @return 头像网址，可能为null
     */
    @Nullable
    String profileUrl();

    /**
     * 获取用户上一次登录时间
     * @return 上一次登录时间
     */
    LocalDate lastLoginTime();

    @IdView("comment")
    List<Integer> commentId();

    @OneToMany(mappedBy = "commenter")
    List<Comment> comment();

    @IdView("commentLike")
    List<Integer> commentLikeId();

    @OneToMany(mappedBy = "user")
    List<CommentLike> commentLike();

    @JsonIgnore
    int updatePersonId();

    @JsonIgnore
    int createPersonId();

    @Default("0")
    @LogicalDeleted(
            value = "1"
    )
    @Column(
            name = "is_deleted"
    )
    int deleted();

    class Status {
        public static final short NORMAL = 1;
        public static final short UNSAFE = 2;
        public static final short FREEZE = 3;

        private Status() {}
    }

    class Type {
        public static final short NORMAL_USER = 1;
        public static final short STORE_USER = 2;

        private Type() {}
    }
}
