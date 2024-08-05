package com.moyanshushe.controller;

import com.moyanshushe.constant.CouponConstant;
import com.moyanshushe.constant.CommonConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.coupon.CouponForDelete;
import com.moyanshushe.model.dto.coupon.CouponSpecification;
import com.moyanshushe.model.dto.coupon.CouponSubstance;
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
    @PostMapping("/get")
    public ResponseEntity<Result> get(
            @RequestBody CouponSpecification couponSpecification) {

        Page<CouponSubstance> page = service.query(couponSpecification);

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
            @RequestBody CouponSubstance couponSubstance) {

        Boolean result = service.add(couponSubstance);

        return ResponseEntity.ok(
                Boolean.TRUE.equals(result) ? Result.success(CouponConstant.COUPON_ADD_SUCCESS) : Result.error(CouponConstant.COUPON_ADD_FAIL)
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
            @RequestBody CouponSubstance couponSubstance) {

        if (couponSubstance.getId() == null) {
            return ResponseEntity.ok(Result.error(CommonConstant.INPUT_INVALID));
        }

        Boolean result = service.update(couponSubstance);

        return ResponseEntity.ok(
                Boolean.TRUE.equals(result)
                        ? Result.success(CouponConstant.COUPON_UPDATE_SUCCESS)
                        : Result.error(CouponConstant.COUPON_UPDATE_FAIL)
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

        Boolean result = service.delete(couponForDelete);

        return ResponseEntity.ok(
                Boolean.TRUE.equals(result)
                        ? Result.success(CouponConstant.COUPON_DELETE_SUCCESS)
                        : Result.error(CouponConstant.COUPON_DELETE_FAIL)
        );
    }
}
