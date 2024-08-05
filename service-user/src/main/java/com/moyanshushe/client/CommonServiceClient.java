package com.moyanshushe.client;

/*
 * Author: Napbad
 * Version: 1.0
 */

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
import org.babyfish.jimmer.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@FeignClient(value = "service-common")
public interface CommonServiceClient {

    @PostMapping("/item/fetch")
    ResponseEntity<Result> fetchItems(@RequestBody ItemSpecification specification);

    @PostMapping("/item/add")
    ResponseEntity<Result> addItem(@RequestBody ItemForAdd itemForAdd);

    @PostMapping("/item/update")
    ResponseEntity<Result> updateItem(@RequestBody ItemForUpdate itemForUpdate);

    @PostMapping("/item/delete")
    ResponseEntity<Result> deleteItems(@RequestBody ItemForDelete itemForDelete);

    @PostMapping("/item/fetch-public")
    ResponseEntity<Result> fetchPublicItems(@RequestBody ItemSpecification specification);

    @PostMapping("address/add")
    ResponseEntity<Result> add(@RequestBody AddressSubstance addressSubstance);

    @PostMapping("/address/get")
    ResponseEntity<Result> getAddress (@RequestBody AddressSpecification addressForQuery);

    @PostMapping("/address/update")
    ResponseEntity<Result> updateAddress(@RequestBody AddressSubstance addressSubstance);

    @PostMapping("/address/delete")
    ResponseEntity<Result> deleteAddress(@RequestBody AddressForDelete addressForDelete);

    @PostMapping("/file/upload/image")
    CompletableFuture<Result> uploadImage(@RequestParam("file") MultipartFile file);

    @PostMapping("/label/get")
    ResponseEntity<Result> queryLabels(@RequestBody LabelSpecification labelSpecification);

    @PostMapping("label/add")
    ResponseEntity<Result> addLabel(@RequestBody LabelInput labelInput);

    @PostMapping("label/update")
    ResponseEntity<Result> updateLabel(@RequestBody LabelInput labelInput);

    @PostMapping("label/delete")
    ResponseEntity<Result> deleteLabel(@RequestBody LabelForDelete labelForDelete);

    @PostMapping("/order/fetch")
    ResponseEntity<Result> getOrder(@RequestBody OrderSpecification specification);

    @PostMapping("/order/add")
    ResponseEntity<Result> addOrder(@RequestBody OrderForAdd orderForAdd);

    @PostMapping("/order/update")
    ResponseEntity<Result> updateOrder(@RequestBody OrderForUpdate orderForUpdate);

    @PostMapping("/order/delete")
    ResponseEntity<Result> deleteOrder(@RequestBody OrderForDelete orderForDelete);

    @PostMapping("/coupon/get")
    Page<CouponSubstance> getCoupon(@RequestBody CouponSpecification couponSpecification);

    @PostMapping("/coupon/add")
    ResponseEntity<Result> addCoupon(@RequestBody CouponSubstance couponSubstance);

    @PostMapping("/coupon/update")
    ResponseEntity<Result> updateCoupon(@RequestBody CouponSubstance couponSubstance);

    @PostMapping("/coupon/delete")
    ResponseEntity<Result> deleteCoupon(@RequestBody CouponForDelete couponForDelete);

    @PostMapping("address-part1/get")
    ResponseEntity<Result> readAddressPart1(@RequestBody AddressPart1Specification specification);

    @PostMapping("address-part1/add")
    ResponseEntity<Result> addAddressPart1(@RequestBody AddressPart1Input addressPart1Input);

    @PostMapping("address-part1/update")
    ResponseEntity<Result> updateAddressPart1(@RequestBody AddressPart1Input addressPart1Input);

    @PostMapping("address-part1/delete")
    ResponseEntity<Result> deleteAddressPart1(@RequestBody AddressPart1ForDelete addressPart1ForDelete);

    @PostMapping("address-part2/get")
    ResponseEntity<Result> readAddressPart2(@RequestBody AddressPart2Specification specification);

    @PostMapping("address-part2/add")
    ResponseEntity<Result> addAddressPart2(@RequestBody AddressPart2Input addressPart2Input);

    @PostMapping("address-part2/update")
    ResponseEntity<Result> updateAddressPart2(@RequestBody AddressPart2Input addressPart2Input);

    @PostMapping("address-part2/delete")
    ResponseEntity<Result> deleteAddressPart2(@RequestBody AddressPart2ForDelete addressPart2ForDelete);
}
