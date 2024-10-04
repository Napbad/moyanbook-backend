package com.moyanshushe.controller;

import com.moyanshushe.constant.AddressConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.address.*;
import com.moyanshushe.model.entity.Address;
import com.moyanshushe.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 地址管理控制器
 * 负责处理与用户地址相关的HTTP请求，包括查询、添加、更新和删除地址。
 */
@Api
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    /**
     * 查询地址列表
     * 根据提供的查询条件，获取用户的地址列表。
     *
     * @param addressSpecification 查询条件对象，包含ID等信息
     * @return 包含地址列表的结果对象
     */
    @Api
    @PostMapping("/query")
    public ResponseEntity<Result> query(
            @RequestBody AddressSpecification addressSpecification) {

                                        log.info("Received query request with specification: {}", addressSpecification);

                                        Page<AddressView> page = addressService.query(addressSpecification);

                                        log.info("Returning address list with page: {}", page);

                                        return ResponseEntity.ok(
                                                Result.success(page)
                                        );
    }

    /**
     * 添加地址
     * 接收一个新的地址对象，将其添加到数据库。
     *
     * @param address 待添加的地址详情对象
     * @return 添加结果的对象，成功或失败
     */
    // TODO the condition that part don't exist
    @Api
    @PostMapping("/add")
    public ResponseEntity<Result> add(
            @RequestBody AddressCreateInput address
    ) {

        log.info("Received add request with address: {}", address);

        Address result = addressService.add(address);

        log.info("Returning add result: {}", result);

        return ResponseEntity.ok(
                Result.success(AddressConstant.ADDRESS_ADD_SUCCESS, result)
        );
    }

    /**
     * 更新地址
     * 根据提供的地址对象更新数据库中的地址信息。
     *
     * @param address 待更新的地址详情对象，包含ID等标识信息
     * @return 更新结果的对象，成功或失败
     */
    @Api
    @PostMapping("/update")
    public ResponseEntity<Result> update(
            @RequestBody AddressUpdateInput address
    ) {

        log.info("Received update request with address: {}", address);

        addressService.update(address);

        return ResponseEntity.ok(Result.success(AddressConstant.ADDRESS_UPDATE_SUCCESS));
    }

    /**
     * 删除地址
     * 根据提供的删除条件，从数据库中删除相应的地址。
     *
     * @param addressForDelete 删除条件对象，包含待删除地址的ID等信息
     * @return 删除结果的对象，成功或失败
     */
    @Api
    @PostMapping("/delete")
    public ResponseEntity<Result> delete(
            @RequestBody AddressForDelete addressForDelete
    ) {

        log.info("Received delete request with address ID: {}", addressForDelete.getIds());

        addressService.delete(addressForDelete);

        return ResponseEntity.ok(
                Result.success(AddressConstant.ADDRESS_DELETE_SUCCESS)
        );
    }
}
