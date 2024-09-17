package com.moyanshushe.model.entity;

import org.babyfish.jimmer.sql.*;

import java.util.List;

/**
 * 区
 */
@Entity
public interface AddressPart2 {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id();

    /**
     * 区名
     */
    String name();

    /**
     * 隶属于
     */
    Integer parentAddressId();

    @IdView(value = "childAddress")
    List<Integer> childAddressId();

    @OneToMany(mappedBy = "parentAddress")
    List<AddressPart1> childAddress();

    @OneToMany(mappedBy = "addressPart2")
    List<Address> address();
}

