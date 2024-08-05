package com.moyanshushe.service.impl;

import com.moyanshushe.model.dto.coupon.CouponForDelete;
import com.moyanshushe.model.dto.coupon.CouponSpecification;
import com.moyanshushe.model.dto.coupon.CouponSubstance;
import com.moyanshushe.model.entity.Coupon;
import com.moyanshushe.model.entity.CouponTable;
import com.moyanshushe.model.entity.Fetchers;
import com.moyanshushe.service.CouponService;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.DeleteResult;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/*
 * Author: Napbad
 * Version: 1.0
 */
@Service
public class CouponServiceImpl implements CouponService {
    private final JSqlClient jsqlClient;
    private final CouponTable table;
    public CouponServiceImpl(JSqlClient jsqlClient) {
        this.jsqlClient = jsqlClient;
        this.table = CouponTable.$;
    }

    @Override
    public Page<CouponSubstance> query(CouponSpecification couponSpecification) {
        return jsqlClient.createQuery(table)
                .where(couponSpecification)
                .select(
                        table.fetch(CouponSubstance.class)
                )
                .fetchPage(couponSpecification.getPage() == null ? 0 : couponSpecification.getPage(),
                        couponSpecification.getPageSize() == null ? 10 : couponSpecification.getPageSize());
    }

    @Override
    public @NotNull Boolean add(CouponSubstance couponSubstance) {
        SimpleSaveResult<Coupon> result = jsqlClient.save(couponSubstance);
        return result.isModified();
    }

    @Override
    public @NotNull Boolean update(CouponSubstance couponSubstance) {
        SimpleSaveResult<Coupon> result = jsqlClient.update(couponSubstance.toEntity());
        return true;
    }

    @Override
    public @NotNull Boolean delete(CouponForDelete couponForDelete) {
        DeleteResult result = jsqlClient.deleteByIds(Coupon.class, couponForDelete.getIds());
        return result.getAffectedRowCount(Coupon.class) == couponForDelete.getIds().size();
    }
}
