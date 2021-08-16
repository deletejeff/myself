package com.maco.client.utils;

import com.maco.common.po.MyWxMpUser;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    public static void setSessionUser(HttpServletRequest request, MyWxMpUser userInfo) {
        request.getSession().setAttribute("user",userInfo);
    }
    public static MyWxMpUser getSessionUser(HttpServletRequest request){
        Object obj = request.getSession().getAttribute("user");
        if(obj != null){
            return (MyWxMpUser) obj;
        }else{
            return null;
        }
    }
}
