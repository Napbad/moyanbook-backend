package com.moyanshushe.client;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.address.AddressCreateInput;
import com.moyanshushe.model.dto.address.AddressForDelete;
import com.moyanshushe.model.dto.address.AddressSpecification;
import com.moyanshushe.model.dto.address.AddressUpdateInput;
import com.moyanshushe.model.dto.address_part1.AddressPart1CreateInput;
import com.moyanshushe.model.dto.address_part1.AddressPart1ForDelete;
import com.moyanshushe.model.dto.address_part1.AddressPart1Specification;
import com.moyanshushe.model.dto.address_part1.AddressPart1UpdateInput;
import com.moyanshushe.model.dto.address_part2.AddressPart2CreateInput;
import com.moyanshushe.model.dto.address_part2.AddressPart2ForDelete;
import com.moyanshushe.model.dto.address_part2.AddressPart2Specification;
import com.moyanshushe.model.dto.address_part2.AddressPart2UpdateInput;
import com.moyanshushe.model.dto.comment.CommentForAdd;
import com.moyanshushe.model.dto.comment.CommentForDelete;
import com.moyanshushe.model.dto.comment.CommentForUpdate;
import com.moyanshushe.model.dto.comment.CommentSpecification;
import com.moyanshushe.model.dto.comment_likes.CommentLikeForAdd;
import com.moyanshushe.model.dto.comment_likes.CommentLikeForDelete;
import com.moyanshushe.model.dto.comment_likes.CommentLikeSpecification;
import com.moyanshushe.model.dto.coupon.*;
import com.moyanshushe.model.dto.item.ItemForAdd;
import com.moyanshushe.model.dto.item.ItemForDelete;
import com.moyanshushe.model.dto.item.ItemForUpdate;
import com.moyanshushe.model.dto.item.ItemSpecification;
import com.moyanshushe.model.dto.category.CategoryCreateInput;
import com.moyanshushe.model.dto.category.CategoryForDelete;
import com.moyanshushe.model.dto.category.CategorySpecification;
import com.moyanshushe.model.dto.category.CategoryUpdateInput;
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

    @PostMapping("/item/query")
    ResponseEntity<Result> fetchItems(@RequestBody ItemSpecification specification);

    @PostMapping("/item/add")
    ResponseEntity<Result> addItem(@RequestBody ItemForAdd item);

    @PostMapping("/item/update")
    ResponseEntity<Result> updateItem(@RequestBody ItemForUpdate item);

    @PostMapping("/item/delete")
    ResponseEntity<Result> deleteItems(@RequestBody ItemForDelete item);

    @PostMapping("/item/query-public")
    ResponseEntity<Result> fetchPublicItems(@RequestBody ItemSpecification specification);

    @PostMapping("address/add")
    ResponseEntity<Result> add(@RequestBody AddressCreateInput addressSubstance);

    @PostMapping("/address/query")
    ResponseEntity<Result> queryAddress(@RequestBody AddressSpecification addressForQuery);

    @PostMapping("/address/update")
    ResponseEntity<Result> updateAddress(@RequestBody AddressUpdateInput addressSubstance);

    @PostMapping("/address/delete")
    ResponseEntity<Result> deleteAddress(@RequestBody AddressForDelete address);

    @PostMapping("/file/upload/image")
    CompletableFuture<Result> uploadImage(@RequestParam("file") MultipartFile file);

    @PostMapping("/category/query")
    ResponseEntity<Result> queryCategories(@RequestBody CategorySpecification labelSpecification);

    @PostMapping("category/add")
    ResponseEntity<Result> addCategory(@RequestBody CategoryCreateInput labelInput);

    @PostMapping("category/update")
    ResponseEntity<Result> updateCategory(@RequestBody CategoryUpdateInput labelInput);

    @PostMapping("category/delete")
    ResponseEntity<Result> deleteCategory(@RequestBody CategoryForDelete category);

    @PostMapping("/order/fetch")
    ResponseEntity<Result> queryOrder(@RequestBody OrderSpecification specification);

    @PostMapping("/order/add")
    ResponseEntity<Result> addOrder(@RequestBody OrderForAdd order);

    @PostMapping("/order/update")
    ResponseEntity<Result> updateOrder(@RequestBody OrderForUpdate order);

    @PostMapping("/order/delete")
    ResponseEntity<Result> deleteOrder(@RequestBody OrderForDelete order);

    @PostMapping("/coupon/query")
    Page<CouponView> queryCoupon(@RequestBody CouponSpecification couponSpecification);

    @PostMapping("/coupon/add")
    ResponseEntity<Result> addCoupon(@RequestBody CouponCreateInput couponSubstance);

    @PostMapping("/coupon/update")
    ResponseEntity<Result> updateCoupon(@RequestBody CouponInputForUpdate couponSubstance);

    @PostMapping("/coupon/delete")
    ResponseEntity<Result> deleteCoupon(@RequestBody CouponForDelete coupon);

    @PostMapping("address-part1/query")
    ResponseEntity<Result> queryAddressPart1(@RequestBody AddressPart1Specification specification);

    @PostMapping("address-part1/add")
    ResponseEntity<Result> addAddressPart1(@RequestBody AddressPart1CreateInput addressPart1Input);

    @PostMapping("address-part1/update")
    ResponseEntity<Result> updateAddressPart1(@RequestBody AddressPart1UpdateInput addressPart1Input);

    @PostMapping("address-part1/delete")
    ResponseEntity<Result> deleteAddressPart1(@RequestBody AddressPart1ForDelete addressPart1);

    @PostMapping("address-part2/query")
    ResponseEntity<Result> readAddressPart2(@RequestBody AddressPart2Specification specification);

    @PostMapping("address-part2/add")
    ResponseEntity<Result> addAddressPart2(@RequestBody AddressPart2CreateInput addressPart2Input);

    @PostMapping("address-part2/update")
    ResponseEntity<Result> updateAddressPart2(@RequestBody AddressPart2UpdateInput addressPart2Input);

    @PostMapping("address-part2/delete")
    ResponseEntity<Result> deleteAddressPart2(@RequestBody AddressPart2ForDelete addressPart2);

    @PostMapping("/comment/add")
    ResponseEntity<Result> addComment(CommentForAdd comment);

    @PostMapping("/comment/delete")
    ResponseEntity<Result> deleteComment(CommentForDelete comment);

    @PostMapping("/comment/update")
    ResponseEntity<Result> updateComment(CommentForUpdate comment);

    @PostMapping("/comment/query")
    ResponseEntity<Result> queryComment(CommentSpecification specification);

    @PostMapping("/comment-like/add")
    ResponseEntity<Result> addCommentLike(CommentLikeForAdd comment);

    @PostMapping("/comment-like/delete")
    ResponseEntity<Result> deleteCommentLike(CommentLikeForDelete comment);

    @PostMapping("/comment-like/query")
    ResponseEntity<Result> queryCommentLike(CommentLikeSpecification specification);

    @PostMapping("/address/add")
    ResponseEntity<Result> addAddress(AddressCreateInput addressForQuery);
}
