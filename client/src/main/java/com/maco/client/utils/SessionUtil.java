package com.maco.client.utils;

import com.maco.common.po.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class SessionUtil {
    public static void setSessionUser(HttpServletRequest request, UserInfo userInfo) {
        request.getSession().setAttribute("user",userInfo);
    }
    public static UserInfo getSessionUser(HttpServletRequest request){
        Object obj = request.getSession().getAttribute("user");
        if(obj != null){
            return (UserInfo) obj;
        }else{
            return null;
        }
    }
    public static String getSessionOpenid(HttpServletRequest request){
        return Objects.requireNonNull(getSessionUser(request)).getWxMpUser().getOpenId();
    }
}
