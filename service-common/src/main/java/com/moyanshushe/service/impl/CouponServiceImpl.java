package com.moyanshushe.service.impl;

import com.moyanshushe.model.dto.coupon.CouponCreateInput;
import com.moyanshushe.model.dto.coupon.CouponForDelete;
import com.moyanshushe.model.dto.coupon.CouponInputForUpdate;
import com.moyanshushe.model.dto.coupon.CouponSpecification;
import com.moyanshushe.model.entity.Coupon;
import com.moyanshushe.model.entity.CouponTable;
import com.moyanshushe.service.CouponService;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.View;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.DeleteResult;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.jetbrains.annotations.NotNull;
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

    public @NotNull <T extends View<Coupon>> Page<T> query(CouponSpecification couponSpecification,
                                                           Class<T> type) {
        return jsqlClient.createQuery(table)
                .where(couponSpecification)
                .select(
                        table.fetch(type)
                )
                .fetchPage(couponSpecification.getPage() == null ? 0 : couponSpecification.getPage(),
                        couponSpecification.getPageSize() == null ? 10 : couponSpecification.getPageSize());
    }


    @Override
    public void add(CouponCreateInput couponSubstance) {
        SimpleSaveResult<Coupon> result = jsqlClient.save(couponSubstance);
    }

    @Override
    public void update(CouponInputForUpdate couponSubstance) {
        SimpleSaveResult<Coupon> result = jsqlClient.update(couponSubstance.toEntity());
    }

    @Override
    public void delete(CouponForDelete couponForDelete) {
        DeleteResult result = jsqlClient.deleteByIds(Coupon.class, couponForDelete.getIds());
    }
}
