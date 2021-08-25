package com.maco.common.enums;

/**
 * @Author machao
 * @Date 2017/10/10 10:57
 * 全局错误信息枚举类
 **/
public enum CouponStatusEnum {
    CREATED("", "0"),
    DISTRIBUTED("", "1"),
    GIVEN("", "2"),
    RECEIVED("", "3"),
    WRITTEN_OFF("", "4"),
    ABANDONED("", "9"),
    ;
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    CouponStatusEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
