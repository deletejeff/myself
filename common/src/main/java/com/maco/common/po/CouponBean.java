package com.maco.common.po;

import lombok.Data;

import java.util.Date;

@Data
public class CouponBean {
    private String couponId;
    private String couponName;
    private String couponTypeId;
    private String startTime;
    private String endTime;
    private String amount;
    /**
     * 0:已生成待分配
     * 1:已分配待发放
     * 2:已发放待领取
     * 3:已领取待核销
     * 4:已核销
     * 9:已作废
     */
    private String couponStatus;
    private String creator;
    private Date createTime;
    private String updater;
    private Date updateTime;

}
