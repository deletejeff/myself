package com.maco.service;

import com.maco.common.po.MyWxMpUser;
import com.maco.common.po.UserInfo;
import com.maco.common.po.UserRole;

import java.util.List;

public interface UserService {
    UserRole getRole(String openid);

    MyWxMpUser getUserInfo(String openId);

    List<UserInfo> getUserList();
}
