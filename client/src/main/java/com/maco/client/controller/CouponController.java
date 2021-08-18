package com.maco.client.controller;

import com.maco.client.annotation.RoleAdmin;
import com.maco.client.utils.SessionUtil;
import com.maco.common.enums.MySelfEnums;
import com.maco.common.po.CouponBean;
import com.maco.common.po.ResultMap;
import com.maco.common.po.ResultMapUtil;
import com.maco.service.CouponService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;

    @RoleAdmin
    @PostMapping("/createCoupon")
    public ResultMap createCoupon(@RequestBody CouponBean couponBean, HttpServletRequest request) {
        try {
            if (couponBean == null || !StringUtils.hasLength(couponBean.getAmount())
                    || couponBean.getStartTime() == null || couponBean.getEndTime() == null
                    || !StringUtils.hasLength(couponBean.getCouponName())
                    || !StringUtils.hasLength(couponBean.getDescription())
                    || !org.apache.commons.lang3.StringUtils.isNumeric(couponBean.getAmount())) {
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.PARAMS_FAIL);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.parse(couponBean.getStartTime());
            sdf.parse(couponBean.getEndTime());
            String couponId = UUID.randomUUID().toString().replace("-", "");
            couponBean.setCouponId(couponId);
            couponBean.setCreator(SessionUtil.getSessionUser(request).getOpenId());
            couponBean.setCouponTypeId("");
            couponService.addCoupon(couponBean);
            couponBean = couponService.getCouponById(couponId);
            return ResultMapUtil.success("coupon", couponBean);
        } catch (Exception e) {
            log.error("生成优惠券失败", e);
            return ResultMapUtil.exception("生成优惠券失败");
        }
    }

    @PostMapping("/getCoupon")
    public ResultMap getCoupon(@RequestBody Map<String, Object> params) {
        try {
            CouponBean couponBean = couponService.getCouponById(String.valueOf(params.get("couponId")));
            return ResultMapUtil.success("coupon", couponBean);
        } catch (Exception e) {
            log.error("获取优惠券失败", e);
            return ResultMapUtil.exception("获取优惠券失败");
        }
    }
}
