package com.maco.client.controller;

import com.maco.client.utils.SessionUtil;
import com.maco.common.enums.MySelfEnums;
import com.maco.common.po.MyWxMpUser;
import com.maco.common.po.ResultMap;
import com.maco.common.po.ResultMapUtil;
import com.maco.service.UserService;
import com.maco.service.WxUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final WxUserService wxUserService;
    private final WxMpService wxMpService;
    private final UserService userService;

    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().print("success");
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            log.error("syncUser方法异常", e);
        }
    }
    @RequestMapping("/syncWxUser")
    public void syncWxUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            wxUserService.syncUser();
        } catch (Exception e) {
            log.error("syncUser方法异常", e);
        }
    }
    @PostMapping("/getUserInfo")
    public ResultMap getUserInfo(@RequestParam String code,HttpServletRequest request,HttpServletResponse response){
        MyWxMpUser userInfo;
        try {
            WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
            userInfo = userService.getUserInfo(accessToken.getOpenId());
            SessionUtil.setSessionUser(request, userInfo);
            return ResultMapUtil.success("user", userInfo);
        } catch (WxErrorException e) {
            log.error("获取微信openid失败", e);
            return ResultMapUtil.exception("获取微信openid失败");
        }
    }

    @PostMapping("/getRole")
    public ResultMap getRole(HttpServletRequest request,HttpServletResponse response){
        try {
            MyWxMpUser sessionUser = SessionUtil.getSessionUser(request);
            if(sessionUser == null){
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.USER_FAIL);
            }
            return ResultMapUtil.success("userRole", userService.getRole(sessionUser.getOpenId()));
        } catch (Exception e) {
            log.error("获取用户角色异常", e);
            return ResultMapUtil.exception("获取用户角色异常");
        }
    }
}
