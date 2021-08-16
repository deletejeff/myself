package com.maco.common.po;

import lombok.Data;

@Data
public class UserRole {
    private String openid;
    private String name;
    private String role;
    private Integer status;
}
