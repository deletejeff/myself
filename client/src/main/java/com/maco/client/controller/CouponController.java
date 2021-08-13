package com.maco.client.controller;

import com.maco.common.po.CouponBean;
import com.maco.common.po.ResultMap;
import com.maco.common.po.ResultMapUtil;
import com.maco.service.CouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/coupon")
public class CouponController {
    public static final Logger logger = LoggerFactory.getLogger(CouponController.class);
    @Autowired
    private CouponService couponService;

    @PostMapping("/createCoupon")
    public ResultMap createCoupon(CouponBean couponBean){
        try {
            String couponId = UUID.randomUUID().toString().replace("-", "");
            couponBean.setCouponId(couponId);
            couponService.addCoupon(couponBean);
            couponBean = couponService.getCouponById(couponId);
        } catch (Exception e) {
            couponBean = null;
            logger.error("生成优惠券失败", e);
        }
        return ResultMapUtil.success("coupon", couponBean);
    }
}
