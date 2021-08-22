package com.maco.service;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.util.List;

public interface WxUserService {
    void syncUser() throws Exception;
    void mergeUser(List<WxMpUser> list);

    void unsubscribeUser(String openId);
}
