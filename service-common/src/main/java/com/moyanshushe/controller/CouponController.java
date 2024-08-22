package com.moyanshushe.controller;

import com.moyanshushe.constant.CouponConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.coupon.*;
import com.moyanshushe.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 优惠券控制器，负责处理与优惠券相关的HTTP请求。
 */
@Api
@Slf4j
@RestController
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService service;

    /**
     * 构造函数，初始化优惠券服务。
     *
     * @param service 优惠券服务实例
     */
    public CouponController(CouponService service) {
        this.service = service;
    }

    /**
     * 根据优惠券规格获取优惠券实体。
     *
     * @param couponSpecification 优惠券的规格参数
     * @return 包含优惠券实体的响应体
     */
    @Api
    @PostMapping("/query")
    public ResponseEntity<Result> query(
            @RequestBody CouponSpecification couponSpecification) {

        Page<CouponView> page = service.query(couponSpecification, CouponView.class);

        return ResponseEntity.ok(
                Result.success(page)
        );
    }

    /**
     * 添加新的优惠券实体。
     *
     * @param couponSubstance 待添加的优惠券实体
     * @return 添加结果的响应体
     */
    @Api
    @PostMapping("/add")
    public ResponseEntity<Result> add(
            @RequestBody CouponCreateInput couponSubstance) {

        service.add(couponSubstance);

        return ResponseEntity.ok(
                Result.success(CouponConstant.COUPON_ADD_SUCCESS)
        );
    }

    /**
     * 更新现有的优惠券实体。
     *
     * @param couponSubstance 待更新的优惠券实体
     * @return 更新结果的响应体
     */
    @Api
    @PostMapping("/update")
    public ResponseEntity<Result> update(
            @RequestBody CouponInputForUpdate couponSubstance) {

        service.update(couponSubstance);

        return ResponseEntity.ok(
                        Result.success(CouponConstant.COUPON_UPDATE_SUCCESS)
        );
    }

    /**
     * 根据删除规格删除优惠券实体。
     *
     * @param couponForDelete 优惠券的删除规格参数
     * @return 删除结果的响应体
     */
    @Api
    @PostMapping("/delete")
    public ResponseEntity<Result> delete(
            @RequestBody CouponForDelete couponForDelete) {

        service.delete(couponForDelete);

        return ResponseEntity.ok(
                        Result.success(CouponConstant.COUPON_DELETE_SUCCESS)
        );
    }
}
