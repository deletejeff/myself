package com.maco.dao;

import com.maco.common.po.CouponBean;
import org.apache.ibatis.annotations.Param;

public interface CouponDao {
    public void addCoupon(@Param("couponBean") CouponBean couponBean);

    CouponBean getCouponById(String couponId);
}
