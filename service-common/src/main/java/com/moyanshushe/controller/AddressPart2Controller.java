package com.moyanshushe.controller;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.constant.AddressConstant;
import com.moyanshushe.constant.WebIOConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.address_part2.*;
import com.moyanshushe.model.entity.AddressPart2;
import com.moyanshushe.service.AddressPart2Service;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Api
@Slf4j
@RestController
@RequestMapping("/address-part2")
public class AddressPart2Controller {

    private final AddressPart2Service addressPart2Service;

    public AddressPart2Controller(AddressPart2Service addressPart2Service) {
        this.addressPart2Service = addressPart2Service;
    }

    // 创建地址部分1
    @PostMapping("/add")
    public ResponseEntity<Result> createAddressPart2(@RequestBody AddressPart2CreateInput addressPart2) {
        try {
            int createdId = addressPart2Service.add(addressPart2);
            return ResponseEntity.ok(Result.success(createdId));
        } catch (Exception e) {
            log.error("Error creating address part 1", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error(""));
        }
    }

    // 读取地址部分1
    @PostMapping("/query")
    public ResponseEntity<Result> queryAddressPart2(@RequestBody AddressPart2Specification specification) {
        Page<AddressPart2View> page = addressPart2Service.query(specification);

        return ResponseEntity.ok(Result.success(page));
    }

    // 更新地址部分1
    @PostMapping("/update")
    public ResponseEntity<Result> updateAddressPart2(@RequestBody AddressPart2UpdateInput addressPart2Input) {
        addressPart2Service.update(addressPart2Input);

        if (addressPart2Input.getId() == null || addressPart2Input.getParentAddressId() == null) {
            return ResponseEntity.badRequest().body(Result.error(WebIOConstant.INPUT_INVALID));
        }

        return ResponseEntity.ok().body(Result.success(AddressConstant.ADDRESS_UPDATE_SUCCESS));
    }

    // 删除地址部分1
    @PostMapping("/delete")
    public ResponseEntity<Result> deleteAddressPart2(@RequestBody AddressPart2ForDelete forDelete) {

        addressPart2Service.delete(forDelete);

        return ResponseEntity.ok(Result.success(AddressConstant.ADDRESS_DELETE_SUCCESS));
    }
}
