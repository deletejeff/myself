package com.maco.service.impl;

import com.maco.common.enums.CouponStatusEnum;
import com.maco.common.po.CouponBean;
import com.maco.common.po.CouponGivenBean;
import com.maco.common.po.UserRole;
import com.maco.dao.CouponDao;
import com.maco.service.CouponService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponDao couponDao;
    private final WxMpService wxMpService;
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
    @Transactional(rollbackFor = Exception.class)
    public Boolean givenCoupon(String couponId, String givenOpenid, String belowOpenid) throws Exception {
        int count1 = couponDao.updateCoupon(couponId, givenOpenid, belowOpenid);
        int count2 = couponDao.insertCouponGiven(UUID.randomUUID().toString().replace("-", ""), couponId, givenOpenid, belowOpenid);
        if(count1 > 0 && count2 > 0) {
            CouponBean couponBean = couponDao.getCouponById(couponId);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            SimpleDateFormat simpleDateFormat_ = new SimpleDateFormat("yyyy-MM-dd");
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
//                    .toUser(belowOpenid)
                    .toUser("ookiQ1rsJByZn9Kl8ivmjK5QU_HE")
                    .templateId("hcX-qOgppKog7d999iRdfK4UO6fq46dnt8d_8CerRcw")
                    .url("http://www.myselfgo.net/customDraw.html?couponId=" + couponId + "&givenOpenid=" + givenOpenid)
                    .build();
            templateMessage.addData(new WxMpTemplateData("first", "恭喜您获得" + couponBean.getAmount() + "元现金优惠券", null));
            templateMessage.addData(new WxMpTemplateData("keyword1", couponId, null));
            templateMessage.addData(new WxMpTemplateData("keyword2", "***元", null));
            templateMessage.addData(new WxMpTemplateData("keyword3", simpleDateFormat.format(new Date()), null));
            templateMessage.addData(new WxMpTemplateData("remark",
                    "优惠券有效期：" + simpleDateFormat.format(simpleDateFormat_.parse(couponBean.getStartTime())) + " - " +
                            simpleDateFormat.format(simpleDateFormat_.parse(couponBean.getStartTime()))));
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            templateMessage.setToUser("ookiQ1mB0NdAzgdhc9VGByLQ_HIE");
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }
        return count1 > 0 && count2 > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCouponStatusById(String couponId, String givenOpenid, String couponStatus, String sessionOpenid) {
        CouponGivenBean couponGivenBean = couponDao.getCouponLastGiven(couponId);
        if (!couponGivenBean.getReceiveOpenid().equals(sessionOpenid)) {
            couponDao.insertCouponGiven(UUID.randomUUID().toString().replace("-",""), couponId, givenOpenid, sessionOpenid);
        }
        return couponDao.updateCouponStatusById(couponId, couponStatus, sessionOpenid) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean recallCoupon(String couponId, String sessionOpenid) {
        couponDao.updateCouponStatusById(couponId, CouponStatusEnum.ABANDONED.getValue(), sessionOpenid);
        //TODO MACHAO 作废的优惠券通知券主
        return true;
    }

    @Override
    public Boolean writeOff(String couponId, String sessionOpenid) {
        return couponDao.writeOff(couponId, CouponStatusEnum.WRITTEN_OFF.getValue(), sessionOpenid) > 0;
    }

}
