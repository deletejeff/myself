package com.maco.service.impl;

import com.maco.common.po.MyWxMpUser;
import com.maco.dao.WxUserDao;
import com.maco.service.WxUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class WxUserServiceImpl implements WxUserService {
    private static final Logger logger = LoggerFactory.getLogger(WxUserServiceImpl.class);
    private final WxMpService wxMpService;
    private final WxUserDao wxUserDao;
    @Override
    public void syncUser() throws Exception {
        logger.info("定时执行批量获取公众号粉丝openid开始");
        List<String> openids = new ArrayList<>();
        String nextOpenid = "";
        while (true){
            WxMpUserList wxMpUserList = wxMpService.getUserService().userList(null);
            if (nextOpenid.equals(wxMpUserList.getNextOpenid())) {
                break;
            }
            openids.addAll(wxMpUserList.getOpenids());
            nextOpenid = wxMpUserList.getNextOpenid();
        }
        logger.info("共获取到{}个openid", openids.size());
        for (int i = 0; i < openids.size(); i=i+100) {
            if (i + 100 <= openids.size()) {
                List<String> openids_100 = openids.subList(i, i + 100);
                List<WxMpUser> wxMpUsers_100 = wxMpService.getUserService().userInfoList(openids_100);
                mergeUser(wxMpUsers_100);
                logger.info("同步数据{}条", i + 100);
            } else {
                List<String> openids_leftover = openids.subList(i, openids.size());
                List<WxMpUser> wxMpUsers_leftover = wxMpService.getUserService().userInfoList(openids_leftover);
                mergeUser(wxMpUsers_leftover);
                logger.info("同步数据{}条，完成同步", openids.size());
            }
        }
    }
    @Override
    public void mergeUser(List<WxMpUser> list){
        List<MyWxMpUser> wxMpUserList = beanCopy(list);
        wxUserDao.mergeUser(wxMpUserList);
    }

    @Override
    public void unsubscribeUser(String openId) {
        wxUserDao.unsubscribeUser(openId);
    }

    private List<MyWxMpUser> beanCopy(List<WxMpUser> list){
        List<MyWxMpUser> returnList = new ArrayList<>();
        for (WxMpUser wxMpUser : list) {
            MyWxMpUser myWxMpUser = new MyWxMpUser();
            BeanUtils.copyProperties(wxMpUser, myWxMpUser);
            String tagIds = "";
            if (wxMpUser.getTagIds() != null && wxMpUser.getTagIds().length > 0) {
                for (Long tagId : wxMpUser.getTagIds()) {
                    tagIds = tagIds + tagId + ",";
                }
                myWxMpUser.setTagidList(tagIds.substring(0, tagIds.length() - 1));
            }
            myWxMpUser.setSubscribe(wxMpUser.getSubscribe() ? 1 : 0);
            if (wxMpUser.getSubscribeTime() != null) {
                myWxMpUser.setSubscribeTime(new Date(wxMpUser.getSubscribeTime() * 1000));
            }
            returnList.add(myWxMpUser);
        }
        return returnList;
    }

}
