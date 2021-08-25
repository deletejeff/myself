package com.maco.common.po;

import lombok.Data;

import java.util.Date;

@Data
public class CouponGivenBean {
    private String givenId;
    private String couponId;
    private String givenOpenid;
    private String receiveOpenid;
    private Date givenTime;

}
