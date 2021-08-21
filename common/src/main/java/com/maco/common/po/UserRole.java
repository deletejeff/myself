package com.maco.common.po;

import lombok.Data;

/**
 * @author admin
 */
@Data
public class UserRole {
    private String openId;
    private String name;
    private String role;
    private Integer status;
}
