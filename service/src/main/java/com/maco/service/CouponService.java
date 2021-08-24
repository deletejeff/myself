package com.maco.service;

import com.maco.common.po.CouponBean;
import com.maco.common.po.UserRole;

import java.util.List;

public interface CouponService {
    void addCoupon(CouponBean couponBean);

    CouponBean getCouponById(String couponId);

    List<CouponBean> getCouponList(UserRole userRole, String openId, String couponStatus);

    Boolean distributeCoupon(String couponId, String givenOpenid, String belowOpenid);
}
