package com.moyanshushe.controller;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.constant.AddressConstant;
import com.moyanshushe.constant.WebIOConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.address_part1.AddressPart1ForDelete;
import com.moyanshushe.model.dto.address_part1.AddressPart1Input;
import com.moyanshushe.model.dto.address_part1.AddressPart1Specification;
import com.moyanshushe.model.entity.AddressPart1;
import com.moyanshushe.service.AddressPart1Service;
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
@RequestMapping("/address-part1")
public class AddressPart1Controller {

    private final AddressPart1Service addressPart1Service;

    public AddressPart1Controller(AddressPart1Service addressPart1Service) {
        this.addressPart1Service = addressPart1Service;
    }

    // 创建地址部分1
    @PostMapping("/add")
    public ResponseEntity<Result> createAddressPart1(@RequestBody AddressPart1Input addressPart1Input) {
        try {
            int createdId = addressPart1Service.add(addressPart1Input);
            return ResponseEntity.ok(Result.success(createdId));
        } catch (Exception e) {
            log.error("Error creating address part 1", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error(""));
        }
    }

    // 读取地址部分1
    @PostMapping("/get")
    public ResponseEntity<Result> readAddressPart1(@RequestBody AddressPart1Specification specification) {
        Page<AddressPart1> page = addressPart1Service.query(specification);

        return ResponseEntity.ok(Result.success(page));
    }

    // 更新地址部分1
    @PostMapping("/update")
    public ResponseEntity<Result> updateAddressPart1(@RequestBody AddressPart1Input addressPart1Input) {
        addressPart1Service.update(addressPart1Input);

        if (addressPart1Input.getId() == null || addressPart1Input.getParentAddress() == null) {
            return ResponseEntity.badRequest().body(Result.error(WebIOConstant.INPUT_INVALID));
        }

        return ResponseEntity.ok().body(Result.success(AddressConstant.ADDRESS_UPDATE_SUCCESS));
    }

    // 删除地址部分1
    @PostMapping("/delete")
    public ResponseEntity<Result> deleteAddressPart1(@RequestBody AddressPart1ForDelete forDelete) {

        addressPart1Service.delete(forDelete);

        return ResponseEntity.ok(Result.success(AddressConstant.ADDRESS_DELETE_SUCCESS));
    }
}
