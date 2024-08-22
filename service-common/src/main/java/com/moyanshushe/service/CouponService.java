package com.moyanshushe.service;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.model.dto.coupon.CouponCreateInput;
import com.moyanshushe.model.dto.coupon.CouponForDelete;
import com.moyanshushe.model.dto.coupon.CouponInputForUpdate;
import com.moyanshushe.model.dto.coupon.CouponSpecification;
import com.moyanshushe.model.entity.Coupon;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.View;
import org.jetbrains.annotations.NotNull;

public interface CouponService {

    @NotNull <T extends View<Coupon>> Page<T> query(CouponSpecification couponSpecification,
                                                           Class<T> type);

    void add(CouponCreateInput coupon);

    void update(CouponInputForUpdate coupon);

    void delete(CouponForDelete coupon);
}
