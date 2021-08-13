package com.maco.client.controller;

import com.maco.common.po.ResultMap;
import com.maco.common.po.ResultMapUtil;
import com.maco.service.WxUserService;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private WxMpService wxMpService;
    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().print("success");
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("syncUser方法异常", e);
        }
    }
    @RequestMapping("/syncWxUser")
    public void syncWxUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            wxUserService.syncUser();
        } catch (Exception e) {
            logger.error("syncUser方法异常", e);
        }
    }
    @PostMapping("/getUserInfo")
    public ResultMap getUserInfo(@RequestParam String code,HttpServletRequest request,HttpServletResponse response){
        WxOAuth2UserInfo userInfo = new WxOAuth2UserInfo();
        try {
            WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
            userInfo = wxMpService.getOAuth2Service().getUserInfo(accessToken, "zh_CN");
        } catch (WxErrorException e) {
            logger.error("获取微信openid失败", e);
        }
        return ResultMapUtil.success("user", userInfo);
    }
}
