package com.maco.dao;

import com.maco.common.po.MyWxMpUser;
import com.maco.common.po.UserInfo;
import com.maco.common.po.UserRole;

import java.util.List;

public interface UserDao {
    UserRole getRole(String openid);

    MyWxMpUser getUser(String openid);

    List<UserInfo> getUserList();
}
