package com.moyanshushe.model.entity;

import org.babyfish.jimmer.sql.*;

import java.util.List;

/**
 * Entity for table "category"
 */
@Entity
//        (microServiceName = "common-service")
public interface Category {

    /**
     * 标签id，主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id();

    /**
     * 标签名
     */
    String name();

    @OneToMany(mappedBy = "category")
    List<Item> items();
}

