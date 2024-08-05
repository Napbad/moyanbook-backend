package com.moyanshushe.service;

import com.moyanshushe.model.dto.address.AddressForDelete;
import com.moyanshushe.model.dto.address.AddressSpecification;
import com.moyanshushe.model.dto.address.AddressSubstance;
import com.moyanshushe.model.entity.Address;
import org.babyfish.jimmer.Page;

/*
 * Author: Napbad
 * Version: 1.0
 */
/**
 * 地址服务接口，定义了对地址信息进行增、删、改、查的操作。
 */
public interface AddressService {

    /**
     * 添加地址信息。
     *
     * @param addressSubstance 包含完整地址信息的实体类。
     * @return 添加操作的成功与否。
     */
    Boolean add(AddressSubstance addressSubstance);

    /**
     * 更新地址信息。
     *
     * @param addressSubstance 包含待更新地址信息的实体类。
     * @return 更新操作的成功与否。
     */
    Boolean update(AddressSubstance addressSubstance);

    /**
     * 查询地址信息。
     *
     * @param addressSpecification 包含查询条件的实体类。
     * @return 包含查询结果的分页对象。
     */
    Page<Address> query(AddressSpecification addressSpecification);

    /**
     * 删除地址信息。
     *
     * @param addressForDelete 包含待删除地址标识的实体类。
     * @return 删除操作的成功与否。
     */
    Boolean delete(AddressForDelete addressForDelete);
}
