package com.maco.client.controller;

import com.github.pagehelper.PageHelper;
import com.maco.client.annotation.RoleAdminOrStaff;
import com.maco.client.utils.SessionUtil;
import com.maco.common.enums.MySelfEnums;
import com.maco.common.po.CouponBean;
import com.maco.common.po.ResultMap;
import com.maco.common.po.ResultMapUtil;
import com.maco.common.po.UserInfo;
import com.maco.service.CouponService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
            couponService.addCoupon(couponBean);
            couponBean = couponService.getCouponById(couponId);
            return ResultMapUtil.success("coupon", couponBean);
        } catch (Exception e) {
            log.error("生成优惠券失败", e);
            return ResultMapUtil.exception("生成优惠券失败");
        }
    }

    @PostMapping("/getCouponById")
    public ResultMap getCouponById(@RequestBody Map<String, Object> params) {
        try {
            CouponBean couponBean = couponService.getCouponById(String.valueOf(params.get("couponId")));
            return ResultMapUtil.success("coupon", couponBean);
        } catch (Exception e) {
            log.error("获取优惠券失败", e);
            return ResultMapUtil.exception("获取优惠券失败");
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
            log.error("获取优惠券失败", e);
            return ResultMapUtil.exception("获取优惠券失败");
        }
    }

    @RoleAdminOrStaff
    @PostMapping("/distributeCoupon")
    public ResultMap distributeCoupon(@RequestBody Map<String, String> params, HttpServletRequest request){
        try {
            UserInfo sessionUser = SessionUtil.getSessionUser(request);
            assert sessionUser != null;
            if (!StringUtils.hasLength(params.get("belowOpenid")) || !StringUtils.hasLength(params.get("couponId"))) {
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.PARAMS_FAIL);
            }
            CouponBean couponBean = couponService.getCouponById(params.get("couponId"));
            Boolean res = couponService.distributeCoupon(params.get("couponId"), sessionUser.getWxMpUser().getOpenId(), params.get("belowOpenid"));
            if (res) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                SimpleDateFormat simpleDateFormat_ = new SimpleDateFormat("yyyy-MM-dd");
                WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
//                        .toUser(params.get("belowOpenid"))
                        .toUser("ookiQ1rsJByZn9Kl8ivmjK5QU_HE")
                        .templateId("hcX-qOgppKog7d999iRdfK4UO6fq46dnt8d_8CerRcw")
                        .url("http://www.myselfgo.net/myself?couponId=" + params.get("couponId"))
                        .build();
                templateMessage.addData(new WxMpTemplateData("first", "恭喜您获得100元现金优惠券", null));
                templateMessage.addData(new WxMpTemplateData("keyword1", params.get("couponId"), null));
                templateMessage.addData(new WxMpTemplateData("keyword2", "保密", null));
                templateMessage.addData(new WxMpTemplateData("keyword3", simpleDateFormat.format(new Date()), null));
                templateMessage.addData(new WxMpTemplateData("remark",
                        "优惠券有效期：" + simpleDateFormat.format(simpleDateFormat_.parse(couponBean.getStartTime())) + " - " +
                                simpleDateFormat.format(simpleDateFormat_.parse(couponBean.getStartTime()))));
                wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
                return ResultMapUtil.success();
            } else {
                return ResultMapUtil.failure();
            }
        } catch (Exception e) {
            log.error("优惠券分发失败", e);
            return ResultMapUtil.exception("优惠券分发失败");
        }
    }

}
