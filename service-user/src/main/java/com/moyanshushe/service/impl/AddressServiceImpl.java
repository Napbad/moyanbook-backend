package com.moyanshushe.service.impl;

/*
 * AddressService的实现类，负责处理与地址相关的业务逻辑。
 *
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.exception.AddressExistsException;
import com.moyanshushe.exception.common.InputInvalidException;
import com.moyanshushe.mapper.AddressMapper;
import com.moyanshushe.model.dto.address.*;
import com.moyanshushe.model.entity.Address;
import com.moyanshushe.service.AddressService;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class AddressServiceImpl implements AddressService {

    // 注入AddressMapper，用于执行与地址相关的数据库操作
    private final AddressMapper mapper;

    // 构造函数，初始化mapper字段
    public AddressServiceImpl(AddressMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 添加地址信息。
     *
     * @param addressSubstance 包含地址详细信息的实体。
     * @return 添加操作是否成功的标志，成功返回true，失败返回false。
     */
    @Override
    public @NotNull Address add(AddressCreateInput addressSubstance) {

        if (addressSubstance == null) {
            throw new InputInvalidException();
        }

        if (mapper.queryOneByAddress(addressSubstance).isPresent()) {
            throw new AddressExistsException();
        }

        SimpleSaveResult<Address> add = mapper.add(addressSubstance.toEntity());

        return add.getModifiedEntity();
    }

    /**
     * 更新地址信息。
     *
     * @param addressSubstance 包含更新后地址详细信息的实体。
     * @return 更新操作是否成功的标志，成功返回true，失败返回false。
     */
    @Override
    public Boolean update(AddressUpdateInput addressSubstance) {

        SimpleSaveResult<Address> update = mapper.update(addressSubstance.toEntity());

        // 判断受影响的行数是否为1，确定更新操作是否成功
        return 1 == update.getAffectedRowCount(Address.class);
    }

    /**
     * 查询地址信息。
     *
     * @param addressSpecification 包含查询条件的实体。
     * @return 符合条件的地址实体集合。
     */
    @Override
    public Page<AddressView> query(AddressSpecification addressSpecification) {
        return mapper.query(addressSpecification);
    }

    /**
     * 删除地址信息。
     *
     * @param addressForDelete 包含待删除地址ID的实体。
     * @return 删除操作是否成功的标志，成功返回true，失败返回false。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(AddressForDelete addressForDelete) {

        mapper.delete(addressForDelete);

        // 判断受影响的行数是否与待删除ID的数量相符，确定删除操作是否成功
        return true;
    }
}
