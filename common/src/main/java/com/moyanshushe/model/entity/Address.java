package com.moyanshushe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.babyfish.jimmer.sql.*;

import java.util.List;

/**
 * 地址表
 */
@Entity
public interface Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id();

    /**
     * 详细地址
     */
    String address();


    @ManyToMany(
            mappedBy = "responsibilityArea"
    )
    List<Member> member();

    @ManyToOne
    @JoinColumn(name = "address_part1")
    AddressPart1 addressPart1();

    @ManyToOne
    @JoinColumn(name = "address_part2")
    AddressPart2 addressPart2();


    @Default("0")
    @LogicalDeleted(
            "1"
    )
    @Column(
            name = "is_deleted"
    )
    int deleted();

    @JsonIgnore
    int createPersonId();

    @JsonIgnore
    int updatePersonId();
}

