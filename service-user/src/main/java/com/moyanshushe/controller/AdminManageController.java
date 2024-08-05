package com.moyanshushe.controller;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/9 上午22:34
    @Description: 

*/

import com.moyanshushe.client.CommonServiceClient;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.address.AddressForDelete;
import com.moyanshushe.model.dto.address.AddressSpecification;
import com.moyanshushe.model.dto.address.AddressSubstance;
import com.moyanshushe.model.dto.address_part1.AddressPart1ForDelete;
import com.moyanshushe.model.dto.address_part1.AddressPart1Input;
import com.moyanshushe.model.dto.address_part1.AddressPart1Specification;
import com.moyanshushe.model.dto.address_part2.AddressPart2ForDelete;
import com.moyanshushe.model.dto.address_part2.AddressPart2Input;
import com.moyanshushe.model.dto.address_part2.AddressPart2Specification;
import com.moyanshushe.model.dto.coupon.CouponForDelete;
import com.moyanshushe.model.dto.coupon.CouponSpecification;
import com.moyanshushe.model.dto.coupon.CouponSubstance;
import com.moyanshushe.model.dto.item.ItemForAdd;
import com.moyanshushe.model.dto.item.ItemForDelete;
import com.moyanshushe.model.dto.item.ItemForUpdate;
import com.moyanshushe.model.dto.item.ItemSpecification;
import com.moyanshushe.model.dto.label.LabelForDelete;
import com.moyanshushe.model.dto.label.LabelInput;
import com.moyanshushe.model.dto.label.LabelSpecification;
import com.moyanshushe.model.dto.order.OrderForAdd;
import com.moyanshushe.model.dto.order.OrderForDelete;
import com.moyanshushe.model.dto.order.OrderForUpdate;
import com.moyanshushe.model.dto.order.OrderSpecification;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@Api
@CrossOrigin
@RestController
@RequestMapping({"/admin-manage"})
public class AdminManageController {
    private final CommonServiceClient commonServiceClient;

    @Autowired
    public AdminManageController(CommonServiceClient commonServiceClient) {
        this.commonServiceClient = commonServiceClient;
    }

    // Item Operations
    @PostMapping("/items/fetch")
    public ResponseEntity<Result> fetchItems(@RequestBody ItemSpecification specification) {
        return commonServiceClient.fetchItems(specification);
    }

    @PostMapping("/items/add")
    public ResponseEntity<Result> addItem(@RequestBody ItemForAdd itemForAdd) {
        return commonServiceClient.addItem(itemForAdd);
    }

    @PostMapping("/items/update")
    public ResponseEntity<Result> updateItem(@RequestBody ItemForUpdate itemForUpdate) {
        return commonServiceClient.updateItem(itemForUpdate);
    }

    @PostMapping("/items/delete")
    public ResponseEntity<Result> deleteItems(@RequestBody ItemForDelete itemForDelete) {
        return commonServiceClient.deleteItems(itemForDelete);
    }

    // Address Operations
    @PostMapping("/addresses/add")
    public ResponseEntity<Result> addAddress(@RequestBody AddressSubstance addressSubstance) {
        return commonServiceClient.add(addressSubstance);
    }

    @PostMapping("/addresses/get")
    public ResponseEntity<Result> getAddress(@RequestBody AddressSpecification addressForQuery) {
        return commonServiceClient.getAddress(addressForQuery);
    }

    @PostMapping("/addresses/update")
    public ResponseEntity<Result> updateAddress(@RequestBody AddressSubstance addressSubstance) {
        return commonServiceClient.updateAddress(addressSubstance);
    }

    @PostMapping("/addresses/delete")
    public ResponseEntity<Result> deleteAddress(@RequestBody AddressForDelete addressForDelete) {
        return commonServiceClient.deleteAddress(addressForDelete);
    }

    // Image Upload Operation
    @PostMapping("/images/upload")
    public CompletableFuture<Result> uploadImage(@RequestParam("file") MultipartFile file) {
        return commonServiceClient.uploadImage(file);
    }

    // Label Operations
    @PostMapping("/labels/get")
    public ResponseEntity<Result> queryLabels(@RequestBody LabelSpecification labelSpecification) {
        return commonServiceClient.queryLabels(labelSpecification);
    }

    @PostMapping("/labels/add")
    public ResponseEntity<Result> addLabel(@RequestBody LabelInput labelInput) {
        return commonServiceClient.addLabel(labelInput);
    }

    @PostMapping("/labels/update")
    public ResponseEntity<Result> updateLabel(@RequestBody LabelInput labelInput) {
        return commonServiceClient.updateLabel(labelInput);
    }

    @PostMapping("/labels/delete")
    public ResponseEntity<Result> deleteLabel(@RequestBody LabelForDelete labelForDelete) {
        return commonServiceClient.deleteLabel(labelForDelete);
    }

    // Order Operations
    @PostMapping("/orders/fetch")
    public ResponseEntity<Result> getOrder(@RequestBody OrderSpecification specification) {
        return commonServiceClient.getOrder(specification);
    } // Note: This method returns a Page<Item> but should be adjusted to return the correct type.

    @PostMapping("/orders/add")
    public ResponseEntity<Result> addOrder(@RequestBody OrderForAdd orderForAdd) {
        return commonServiceClient.addOrder(orderForAdd);
    }

    @PostMapping("/orders/update")
    public ResponseEntity<Result> updateOrder(@RequestBody OrderForUpdate orderForUpdate) {
        return commonServiceClient.updateOrder(orderForUpdate);
    }

    @PostMapping("/orders/delete")
    public ResponseEntity<Result> deleteOrder(@RequestBody OrderForDelete orderForDelete) {
        return commonServiceClient.deleteOrder(orderForDelete);
    }

    // Coupon Operations
    @PostMapping("/coupons/get")
    public ResponseEntity<Result> getCoupon(@RequestBody CouponSpecification couponSpecification) {
        return ResponseEntity.ok().body(Result.success(commonServiceClient.getCoupon(couponSpecification)));
    } // Note: This method returns a Page<CouponSubstance> but should be adjusted to return the correct type.

    @PostMapping("/coupons/add")
    public ResponseEntity<Result> addCoupon(@RequestBody CouponSubstance couponSubstance) {
        return commonServiceClient.addCoupon(couponSubstance);
    }

    @PostMapping("/coupons/update")
    public ResponseEntity<Result> updateCoupon(@RequestBody CouponSubstance couponSubstance) {
        return commonServiceClient.updateCoupon(couponSubstance);
    }

    @PostMapping("/coupons/delete")
    public ResponseEntity<Result> deleteCoupon(@RequestBody CouponForDelete couponForDelete) {
        return commonServiceClient.deleteCoupon(couponForDelete);
    }

    // Address Part 1 Operations
    @PostMapping("/address-parts1/get")
    public ResponseEntity<Result> readAddressPart1(@RequestBody AddressPart1Specification specification) {
        return commonServiceClient.readAddressPart1(specification);
    }

    @PostMapping("/address-parts1/add")
    public ResponseEntity<Result> addAddressPart1(@RequestBody AddressPart1Input addressPart1Input) {
        return commonServiceClient.addAddressPart1(addressPart1Input);
    }

    @PostMapping("/address-parts1/update")
    public ResponseEntity<Result> updateAddressPart1(@RequestBody AddressPart1Input addressPart1Input) {
        return commonServiceClient.updateAddressPart1(addressPart1Input);
    }

    @PostMapping("/address-parts1/delete")
    public ResponseEntity<Result> deleteAddressPart1(@RequestBody AddressPart1ForDelete addressPart1ForDelete) {
        return commonServiceClient.deleteAddressPart1(addressPart1ForDelete);
    }

    // Address Part 2 Operations
    @PostMapping("/address-parts2/get")
    public ResponseEntity<Result> readAddressPart2(@RequestBody AddressPart2Specification specification) {
        return commonServiceClient.readAddressPart2(specification);
    }

    @PostMapping("/address-parts2/add")
    public ResponseEntity<Result> addAddressPart2(@RequestBody AddressPart2Input addressPart2Input) {
        return commonServiceClient.addAddressPart2(addressPart2Input);
    }

    @PostMapping("/address-parts2/update")
    public ResponseEntity<Result> updateAddressPart2(@RequestBody AddressPart2Input addressPart2Input) {
        return commonServiceClient.updateAddressPart2(addressPart2Input);
    }

    @PostMapping("/address-parts2/delete")
    public ResponseEntity<Result> deleteAddressPart2(@RequestBody AddressPart2ForDelete addressPart2ForDelete) {
        return commonServiceClient.deleteAddressPart2(addressPart2ForDelete);
    }
}
