package com.maco.service;

import com.maco.common.po.CouponBean;
import com.maco.common.po.UserRole;

import java.util.List;

public interface CouponService {
    void addCoupon(CouponBean couponBean);

    CouponBean getCouponById(String couponId);

    List<CouponBean> getCouponList(UserRole userRole, String openId, String couponStatus);

    Boolean givenCoupon(String couponId, String givenOpenid, String belowOpenid) throws Exception;

    Boolean updateCouponStatusById(String couponId, String givenOpenid, String couponStatus, String sessionOpenid);

    Boolean recallCoupon(String couponId, String sessionOpenid);

    Boolean writeOff(String couponId, String sessionOpenid);
}