package com.moyanshushe.service;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.model.dto.coupon.CouponForDelete;
import com.moyanshushe.model.dto.coupon.CouponSpecification;
import com.moyanshushe.model.dto.coupon.CouponSubstance;
import org.babyfish.jimmer.Page;

public interface CouponService {

    Page<CouponSubstance> query(CouponSpecification couponSpecification);

    Boolean add(CouponSubstance couponSubstance);

    Boolean update(CouponSubstance couponSubstance);

    Boolean delete(CouponForDelete couponForDelete);
}
