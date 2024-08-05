package com.moyanshushe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moyanshushe.model.cnoverter.AddressPasswordConverter;
import org.babyfish.jimmer.jackson.JsonConverter;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;

/**
 * 管理员实体接口
 * 代表数据库中的管理员表，用于存储管理员的信息和权限
 */
@Entity
public interface Member {

    /**
     * 管理员id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id();

    /**
     * 获取管理员名称
     * 用于标识和区分不同的管理员
     * @return 管理员名称
     */
    String name();

    /**
     * 性别
     */
    Short gender();

    /**
     * 获取管理员状态
     * 用于标识管理员的当前状态，如正常、冻结、删除等
     * @return 管理员状态
     */
    Short status();

    /**
     * 获取管理员头像的URL
     * 用于显示管理员的头像
     * @return 头像URL
     */
    String profileUrl();

    /**
     * 获取管理员的电子邮件地址
     * 用于联系和验证管理员身份
     * @return 电子邮件地址
     */
    String email();

    /**
     * 获取管理员的电话号码
     * 用于联系和验证管理员身份
     * @return 电话号码
     */
    String phone();

    /**
     * 地址信息
     */
    @IdView
    Integer addressId();

    /**
     * 获取管理员的地址信息
     * 通过多对一的关系，关联到Address实体
     * @return 管理员的地址信息
     */
    @ManyToOne
    @Nullable
    @OnDissociate(DissociateAction.SET_NULL)
    Address address();

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

    /**
     * 密码
     */
    @JsonConverter(AddressPasswordConverter.class)
    String password();

    /**
     * 负责区域
     */
    @ManyToMany()
    @JoinTable(
            name = "member_address_responsibility_mapping",
            joinColumnName = "member_id",
            inverseJoinColumnName = "address_id"
    )
    List<Address> responsibilityArea();

    @OneToOne(mappedBy = "member")
    @Nullable
    MemberConfirm memberConfirm();

    @JsonIgnore
    int createPersonId();

    @JsonIgnore
    int updatePersonId();

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
     * 管理员状态常量类
     * 提供管理员状态的常量值，如正常、不安全、冻结、删除
     */
    class Status {
        /**
         * 正常状态
         */
        public static final short NORMAL = 1;
        /**
         * 不安全状态
         */
        public static final short UNSAFE = 2;
        /**
         * 冻结状态
         */
        public static final short FREEZE = 3;
        /**
         * 删除状态
         */
        public static final short DELETED = 0;

        private Status() {}
    }
}
