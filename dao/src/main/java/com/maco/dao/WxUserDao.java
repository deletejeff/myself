package com.maco.dao;

import com.maco.common.po.MyWxMpUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxUserDao {
    void mergeUser(@Param("wxMpUserList") List<MyWxMpUser> wxMpUserList);

    void unsubscribeUser(String openId);
}
