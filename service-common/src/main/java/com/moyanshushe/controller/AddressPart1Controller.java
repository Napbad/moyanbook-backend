package com.moyanshushe.controller;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.constant.AddressConstant;
import com.moyanshushe.constant.WebIOConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.address_part1.AddressPart1CreateInput;
import com.moyanshushe.model.dto.address_part1.AddressPart1ForDelete;
import com.moyanshushe.model.dto.address_part1.AddressPart1Specification;
import com.moyanshushe.model.dto.address_part1.AddressPart1UpdateInput;
import com.moyanshushe.model.entity.AddressPart1;
import com.moyanshushe.service.AddressPart1Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/address-part1")
public class AddressPart1Controller {

    private final AddressPart1Service addressPart1Service;

    // 创建地址部分1
    @PostMapping("/add")
    public ResponseEntity<Result> createAddressPart1(
            @RequestBody AddressPart1CreateInput address
    ) {

        log.info("Received create request with address part 1: {}", address);

        int createdId = addressPart1Service.add(address);
        return ResponseEntity.ok(Result.success(createdId));
    }

    // 读取地址部分1
    @PostMapping("/query")
    public ResponseEntity<Result> readAddressPart1(@RequestBody AddressPart1Specification specification) {

        log.info("Received read request with specification: {}", specification);

        Page<AddressPart1> page = addressPart1Service.query(specification);

        return ResponseEntity.ok(Result.success(page));
    }

    // 更新地址部分1
    @PostMapping("/update")
    public ResponseEntity<Result> updateAddressPart1(@RequestBody AddressPart1UpdateInput addressPart1) {

        log.info("Received update request with address part 1: {}", addressPart1);

        addressPart1Service.update(addressPart1);

        if (addressPart1.getId() == null || addressPart1.getParentAddress() == null) {
            return ResponseEntity.badRequest().body(Result.error(WebIOConstant.INPUT_INVALID));
        }

        return ResponseEntity.ok().body(Result.success(AddressConstant.ADDRESS_UPDATE_SUCCESS));
    }

    // 删除地址部分1
    @PostMapping("/delete")
    public ResponseEntity<Result> deleteAddressPart1(@RequestBody AddressPart1ForDelete forDelete) {

        log.info("Received delete request with address part 1 ID: {}", forDelete.getIds());

        addressPart1Service.delete(forDelete);

        return ResponseEntity.ok(Result.success(AddressConstant.ADDRESS_DELETE_SUCCESS));
    }
}
