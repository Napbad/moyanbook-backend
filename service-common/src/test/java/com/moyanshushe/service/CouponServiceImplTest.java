package com.moyanshushe.service;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.model.dto.coupon.CouponForDelete;
import com.moyanshushe.model.dto.coupon.CouponSpecification;
import com.moyanshushe.model.dto.coupon.CouponSubstance;
import com.moyanshushe.model.entity.Coupon;
import org.babyfish.jimmer.Page;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CouponServiceImplTest {
    private static final Logger log = LoggerFactory.getLogger(CouponServiceImplTest.class);
    @Autowired
    private CouponService couponService;

    @Test
    @Order(1)
    void testAdd() {
        CouponSubstance substance = new CouponSubstance();
        substance.setName("test1");
        substance.setPrice(100.00);
        substance.setUserId(1);
        substance.setStatus(Coupon.Status.NORMAL);

        Boolean added = couponService.add(substance);
        Boolean added1 = couponService.add(substance);

        assertTrue(added1);
        assertTrue(added);

        substance.setName("优惠券2");
        Boolean added2 = couponService.add(substance);
        Boolean added3 = couponService.add(substance);

        assertTrue(added2);
        assertTrue(added3);
    }

    @Test
    @Order(2)
    void testQuery() {
        CouponSpecification couponSpecification = new CouponSpecification();
        couponSpecification.setName("test1");
        couponSpecification.setPage(1);

        Page<CouponSubstance> page = couponService.query(couponSpecification);

        log.info("page: {}", page);

        couponSpecification.setName(null);
        Page<CouponSubstance> substancePage = couponService.query(couponSpecification);

        log.info("substancePage: {}", substancePage);

        assertEquals(2, page.getTotalRowCount());
        assertEquals(9, substancePage.getTotalRowCount());
    }

    @Test
    @Order(3)
    void testUpdate() {

        CouponSubstance couponSubstance1 = new CouponSubstance();
        couponSubstance1.setName("Test1");
        couponSubstance1.setPrice(100.00);
        couponSubstance1.setUserId(1);
        couponSubstance1.setStatus(Coupon.Status.NORMAL);

        assertTrue(couponService.add(couponSubstance1));

        CouponSubstance couponSubstance2 = new CouponSubstance();
        couponSubstance2.setName("Test2");
        couponSubstance2.setPrice(150.00);
        couponSubstance2.setUserId(1);
        couponSubstance2.setStatus(Coupon.Status.NORMAL);

        assertTrue(couponService.add(couponSubstance2));

        CouponSpecification specification = new CouponSpecification();
        specification.setName(couponSubstance1.getName());
        specification.setUserId(1);

        Page<CouponSubstance> page = couponService.query(specification);
        assertNotNull(page.getRows().getFirst().getId());

        couponSubstance1.setId(page.getRows().getFirst().getId());
        couponSubstance1.setName("Test - 1");
        couponSubstance1.setExpirationTime(page.getRows().getFirst().getExpirationTime().plusDays(3));
        couponSubstance1.setPrice(200.00);

        assertTrue(couponService.update(couponSubstance1));

        specification.setUserId(1);
        specification.setName(couponSubstance1.getName());
        page = couponService.query(specification);

        assertNotNull(page.getRows().getFirst().getId());
        assertEquals(couponSubstance1.getPrice(), page.getRows().getFirst().getPrice());
        assertEquals(couponSubstance1.getExpirationTime(), page.getRows().getFirst().getExpirationTime());
    }

    @Test
    @Order(4)
    void testDelete() {
        CouponForDelete couponForDelete = new CouponForDelete();
        Page<CouponSubstance> page = couponService.query(new CouponSpecification());
        List<Integer> list = null;
        while (page.getTotalRowCount() > 5) {
            page = couponService.query(new CouponSpecification());
            list = page.getRows().stream().filter(
                    couponSubstance -> {
                        assertNotNull(couponSubstance.getId());
                        return couponSubstance.getId() > 5;
                    }
            ).map(
                    couponSubstance -> {
                        assertNotNull(couponSubstance.getId());
                        return Math.toIntExact(couponSubstance.getId());
                    }
            ).toList();

            couponForDelete.setIds(list);

            assertTrue(couponService.delete(couponForDelete));
        }


    }
}
