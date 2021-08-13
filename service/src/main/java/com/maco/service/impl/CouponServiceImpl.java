package com.maco.service.impl;

import com.maco.common.po.CouponBean;
import com.maco.dao.CouponDao;
import com.maco.service.CouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService {
    public static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);
    @Autowired
    private CouponDao couponDao;
    @Override
    public void addCoupon(CouponBean couponBean) {
        couponDao.addCoupon(couponBean);
    }

    @Override
    public CouponBean getCouponById(String couponId) {
        return null;
    }
}
