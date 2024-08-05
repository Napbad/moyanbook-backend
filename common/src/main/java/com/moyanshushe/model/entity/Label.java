package com.moyanshushe.model.entity;

import org.babyfish.jimmer.sql.*;

import java.util.List;

/**
 * Entity for table "label"
 */
@Entity
//        (microServiceName = "common-service")
public interface Label {

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

    @ManyToMany(mappedBy = "labels")
    List<Item> items();
}

