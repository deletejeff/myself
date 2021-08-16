package com.maco.service;

import com.maco.common.po.MyWxMpUser;
import com.maco.common.po.UserRole;

public interface UserService {
    UserRole getRole(String openid);

    MyWxMpUser getUserInfo(String openId);
}
