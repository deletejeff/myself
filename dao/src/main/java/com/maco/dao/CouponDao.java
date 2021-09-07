package com.maco.dao;

import com.maco.common.po.CouponBean;
import com.maco.common.po.CouponGivenBean;
import com.maco.common.po.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponDao {
    public void addCoupon(@Param("couponBean") CouponBean couponBean);

    CouponBean getCouponById(String couponId);

    List<CouponBean> getCouponList(UserRole userRole, String openId, String couponStatus);

    int insertCouponGiven(String givenId, String couponId, String givenOpenid, String belowOpenid);

    int updateCoupon(String couponId, String givenOpenid, String belowOpenid);

    int updateCouponStatusById(String couponId, String couponStatus, String sessionOpenid);

    CouponGivenBean getCouponLastGiven(String couponId);

    int writeOff(String couponId, String couponStatus, String sessionOpenid);
}
