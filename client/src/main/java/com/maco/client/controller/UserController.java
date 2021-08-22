package com.maco.client.controller;

import com.github.pagehelper.PageHelper;
import com.maco.client.annotation.RoleAdmin;
import com.maco.client.annotation.RoleAdminOrStaff;
import com.maco.client.annotation.RoleStaff;
import com.maco.client.utils.SessionUtil;
import com.maco.common.enums.MySelfEnums;
import com.maco.common.po.*;
import com.maco.common.utils.RedisUtil;
import com.maco.service.UserService;
import com.maco.service.WxUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final WxUserService wxUserService;
    private final WxMpService wxMpService;
    private final UserService userService;
    private final RedisUtil redisUtil;

    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response) {
        try {
            redisUtil.set("machao", "test111111");
            log.info(redisUtil.get("machao").toString());
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
        UserInfo userInfo = new UserInfo();
        try {
            WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
            MyWxMpUser wxMpUser = userService.getUserInfo(accessToken.getOpenId());
            UserRole userRole = userService.getRole(wxMpUser.getOpenId());
            userInfo.setWxMpUser(wxMpUser);
            userInfo.setUserRole(userRole);
            SessionUtil.setSessionUser(request, userInfo);
            return ResultMapUtil.success("user", wxMpUser);
        } catch (WxErrorException e) {
            log.error("获取微信openid失败", e);
            return ResultMapUtil.exception("获取微信openid失败");
        }
    }
    @PostMapping("/getUserInfoAdmin")
    public ResultMap getUserInfoAdmin(HttpServletRequest request,HttpServletResponse response){
        UserInfo userInfo = new UserInfo();
        try {
            String adminOpenid = "ookiQ1rsJByZn9Kl8ivmjK5QU_HE";
            MyWxMpUser wxMpUser = userService.getUserInfo(adminOpenid);
            UserRole userRole = userService.getRole(wxMpUser.getOpenId());
            userInfo.setWxMpUser(wxMpUser);
            userInfo.setUserRole(userRole);
            SessionUtil.setSessionUser(request, userInfo);
            return ResultMapUtil.success("user", userInfo);
        } catch (Exception e) {
            log.error("获取微信openid失败", e);
            return ResultMapUtil.exception("获取微信openid失败");
        }
    }

    @PostMapping("/getRole")
    public ResultMap getRole(HttpServletRequest request,HttpServletResponse response){
        try {
            UserInfo sessionUser = SessionUtil.getSessionUser(request);
            if(sessionUser == null){
                return ResultMapUtil.error(MySelfEnums.MySelfCommEnums.USER_FAIL);
            }
            return ResultMapUtil.success("userRole", userService.getRole(sessionUser.getWxMpUser().getOpenId()));
        } catch (Exception e) {
            log.error("获取用户角色异常", e);
            return ResultMapUtil.exception("获取用户角色异常");
        }
    }
    @RoleAdminOrStaff
    @PostMapping("/getUserList")
    public ResultMap getUserList(@RequestBody Map<String, String> params, HttpServletRequest request, HttpServletResponse response){
        try {
            PageHelper.startPage(Integer.parseInt(params.get("pageNum")), Integer.parseInt(params.get("pageSize")));
            List<UserInfo> userList = userService.getUserList();
            return ResultMapUtil.success("userList", userList);
        } catch (Exception e) {
            log.error("获取用户列表异常", e);
            return ResultMapUtil.exception("获取用户列表异常");
        }
    }
}
