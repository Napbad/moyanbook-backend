package com.moyanshushe.model.entity;

import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;



import java.time.LocalDate;

/**
 * Entity for table "admin"
 */
@Entity
public interface Admin {

    /**
     * 管理员id，主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id();

    /**
     * 管理员名
     */
    String name();

    /**
     * 管理员年龄
     */
    @Nullable
    Integer age();

    /**
     * 管理员性别
     */
    @Nullable
    Short gender();

    /**
     * 管理员邮箱
     */
    @Nullable
    String email();

    /**
     * 管理员手机号
     */
    @Nullable
    String phone();

    /**
     * 管理员密码
     */
    String password();

    /**
     * 管理员状态  1: 正常，2：冻结
     */
    @Nullable
    Short status();

    /**
     * 管理员创建时间
     */
    LocalDate createTime();

    /**
     * 管理员更新时间
     */
    LocalDate updateTime();

    /**
     * 头像网址
     */
    @Nullable
    String profileUrl();

    /**
     * 上一次登录时间
     */
    LocalDate lastLoginTime();

    /**
     * 管理员地址对应的id
     */
    @Nullable
    @IdView
    Integer addressId();

    /**
     * 账户是否已经删除
     */
    @Nullable
    Object isDeleted();

    /**
     * 更新人id
     */
    Integer updatePersonId();

    /**
     * 管理员形式
     */
    Integer type();

    Integer createPersonId();

    /**
     * 获取地址信息
     * @return 地址信息
     */
    @ManyToOne
    @OnDissociate(DissociateAction.SET_NULL)
    @Nullable
    Address address();
}

