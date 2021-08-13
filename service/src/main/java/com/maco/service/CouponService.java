package com.maco.service;

import com.maco.common.po.CouponBean;

public interface CouponService {
    void addCoupon(CouponBean couponBean);

    CouponBean getCouponById(String couponId);
}
