package com.maco.service.impl;

import com.maco.common.po.CouponBean;
import com.maco.dao.CouponDao;
import com.maco.service.CouponService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        return null;
    }
}
