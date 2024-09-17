package com.moyanshushe.model.entity;

import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 园栋
 */
@Entity
public interface AddressPart1 {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id();

    /**
     * 地址名
     */
    String name();

    /**
     * 隶属于的地址
     */
    @IdView
    Integer parentAddressId();

    @Nullable
    @ManyToOne
    AddressPart2 parentAddress();

    @OneToMany(mappedBy = "addressPart1")
    List<Address> address();
}

