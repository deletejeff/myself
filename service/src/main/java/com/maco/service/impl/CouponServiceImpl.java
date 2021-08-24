package com.maco.service.impl;

import com.maco.common.po.CouponBean;
import com.maco.common.po.UserRole;
import com.maco.dao.CouponDao;
import com.maco.service.CouponService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponDao couponDao;
    @Override
    public void addCoupon(CouponBean couponBean) {
        couponDao.addCoupon(couponBean);
    }

    @Override
    public CouponBean getCouponById(String couponId) {
        return couponDao.getCouponById(couponId);
    }

    @Override
    public List<CouponBean> getCouponList(UserRole userRole, String openId, String couponStatus) {
        return couponDao.getCouponList(userRole, openId, couponStatus);
    }

    @Override
    public Boolean distributeCoupon(String couponId, String givenOpenid, String belowOpenid) {
        int count1 = couponDao.updateCoupon(couponId, givenOpenid, belowOpenid);
        int count2 = couponDao.insertCouponGiven(UUID.randomUUID().toString().replace("-",""), couponId, givenOpenid, belowOpenid);
        return count1 > 0 && count2 > 0;
    }
}
