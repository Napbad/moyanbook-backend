package com.moyanshushe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 地址表
 */
@Entity
public interface Address extends BaseEntity {

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

    @Nullable
    @OneToOne(mappedBy = "address")
    User user();

    @Default("0")
    @LogicalDeleted(
            "1"
    )
    @Column(
            name = "is_deleted"
    )
    int deleted();

}

