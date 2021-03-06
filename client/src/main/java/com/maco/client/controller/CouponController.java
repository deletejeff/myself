package com.maco.client.controller;

import com.github.pagehelper.PageHelper;
import com.maco.client.annotation.RoleAdminOrStaff;
import com.maco.client.utils.SessionUtil;
import com.maco.common.enums.CouponStatusEnum;
import com.maco.common.enums.MySelfEnums;
import com.maco.common.po.CouponBean;
import com.maco.common.po.ResultMap;
import com.maco.common.po.ResultMapUtil;
import com.maco.common.po.UserInfo;
import com.maco.service.CouponService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author machao
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;
    private final WxMpService wxMpService;

    @RoleAdminOrStaff
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
            Date startTime = sdf.parse(couponBean.getStartTime());
            Date endTime = sdf.parse(couponBean.getEndTime());
            if(endTime.before(startTime)){
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.END_DATE_FAIL);
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.DAY_OF_MONTH, 0);
            if(!startTime.after(cal.getTime())){
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.START_DATE_FAIL);
            }
            String couponId = UUID.randomUUID().toString().replace("-", "");
            couponBean.setCouponId(couponId);
            couponBean.setCreator(SessionUtil.getSessionUser(request).getWxMpUser().getOpenId());
            couponBean.setCouponTypeId("");
            couponBean.setBelowOpenid(SessionUtil.getSessionUser(request).getWxMpUser().getOpenId());
            couponService.addCoupon(couponBean);
            couponBean = couponService.getCouponById(couponId);
            return ResultMapUtil.success("coupon", couponBean);
        } catch (Exception e) {
            log.error("?????????????????????", e);
            return ResultMapUtil.exception("?????????????????????");
        }
    }

    @PostMapping("/getCouponById")
    public ResultMap getCouponById(@RequestBody Map<String, Object> params) {
        try {
            CouponBean couponBean = couponService.getCouponById(String.valueOf(params.get("couponId")));
            return ResultMapUtil.success("coupon", couponBean);
        } catch (Exception e) {
            log.error("?????????????????????", e);
            return ResultMapUtil.exception("?????????????????????");
        }
    }

    @PostMapping("/getCouponList")
    public ResultMap getCouponList(@RequestBody Map<String, String> params, HttpServletRequest request) {
        try {
            UserInfo sessionUser = SessionUtil.getSessionUser(request);
            assert sessionUser != null;
            PageHelper.startPage(Integer.parseInt(params.get("pageNum")), Integer.parseInt(params.get("pageSize")));
            List<CouponBean> couponList = couponService.getCouponList(sessionUser.getUserRole(), sessionUser.getWxMpUser().getOpenId(), params.get("couponStatus"));
            return ResultMapUtil.success("coupon", couponList);
        } catch (Exception e) {
            log.error("?????????????????????", e);
            return ResultMapUtil.exception("?????????????????????");
        }
    }

    @RoleAdminOrStaff
    @PostMapping("/givenCoupon")
    public ResultMap givenCoupon(@RequestBody Map<String, String> params, HttpServletRequest request){
        try {
            UserInfo sessionUser = SessionUtil.getSessionUser(request);
            assert sessionUser != null;
            if (!StringUtils.hasLength(params.get("belowOpenid")) || !StringUtils.hasLength(params.get("couponId"))) {
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.PARAMS_FAIL);
            }
            Boolean res = couponService.givenCoupon(params.get("couponId"), sessionUser.getWxMpUser().getOpenId(), params.get("belowOpenid"));
            if (res) {
                return ResultMapUtil.success();
            } else {
                return ResultMapUtil.failure();
            }
        } catch (Exception e) {
            log.error("?????????????????????", e);
            return ResultMapUtil.exception("?????????????????????");
        }
    }

    @PostMapping("/receiveCoupon")
    public ResultMap receiveCoupon(@RequestBody Map<String, String> params, HttpServletRequest request, HttpServletResponse response){
        try {
            if (!StringUtils.hasLength(params.get("couponId")) || !StringUtils.hasLength(params.get("givenOpenid"))) {
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.PARAMS_FAIL);
            }
            boolean res = couponService.updateCouponStatusById(params.get("couponId"), params.get("givenOpenid"), CouponStatusEnum.RECEIVED.getValue(), SessionUtil.getSessionOpenid(request));
            if(res){
                return ResultMapUtil.success("?????????????????????");
            } else {
                return ResultMapUtil.failure();
            }
        } catch (Exception e) {
            log.error("?????????????????????", e);
            return ResultMapUtil.exception("?????????????????????");
        }
    }

    @RoleAdminOrStaff
    @PostMapping("/recallCoupon")
    public ResultMap recallCoupon(@RequestBody Map<String, String> params, HttpServletRequest request, HttpServletResponse response) {
        try {
            if(!StringUtils.hasLength(params.get("couponId"))){
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.PARAMS_FAIL);
            }
            Boolean res = couponService.recallCoupon(params.get("couponId"), SessionUtil.getSessionOpenid(request));
            if (res) {
                return ResultMapUtil.success("??????????????????");
            } else {
                return ResultMapUtil.failure();
            }
        } catch (Exception e) {
            log.error("?????????????????????", e);
            return ResultMapUtil.exception("?????????????????????");
        }
    }

    @RoleAdminOrStaff
    @PostMapping("/writeOffCoupon")
    public ResultMap writeOffCoupon(@RequestBody Map<String, String> params, HttpServletRequest request, HttpServletResponse response){
        try {
            if(!StringUtils.hasLength(params.get("couponId"))){
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.PARAMS_FAIL);
            }
            CouponBean couponBean = couponService.getCouponById(params.get("couponId"));
            if (couponBean.getCouponStatus().equals(CouponStatusEnum.WRITTEN_OFF.getValue())) {
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.COUPON_STATUS_WRITE_OFF_ERROR2);
            }
            if (!couponBean.getCouponStatus().equals(CouponStatusEnum.RECEIVED.getValue())) {
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.COUPON_STATUS_WRITE_OFF_ERROR1);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = sdf.parse(couponBean.getStartTime() + " 00:00:00");
            Date endTime = sdf.parse(couponBean.getEndTime() + " 23:59:59");
            if (startTime.after(new Date())) {
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.COUPON_WRITE_OFF_START_TIME_ERROR);
            }
            if (endTime.before(new Date())) {
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.COUPON_WRITE_OFF_END_TIME_ERROR);
            }
            Boolean res = couponService.writeOff(params.get("couponId"), SessionUtil.getSessionOpenid(request));
            if (res) {
                return ResultMapUtil.success("?????????????????????????????????" + couponBean.getAmount() + "???");
            } else {
                return ResultMapUtil.failure();
            }
        } catch (Exception e) {
            log.error("?????????????????????", e);
            return ResultMapUtil.exception("?????????????????????");
        }
    }



}
