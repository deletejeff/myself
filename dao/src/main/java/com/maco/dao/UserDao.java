package com.maco.dao;

import com.maco.common.po.MyWxMpUser;
import com.maco.common.po.UserRole;

public interface UserDao {
    UserRole getRole(String openid);

    MyWxMpUser getUser(String openid);
}
