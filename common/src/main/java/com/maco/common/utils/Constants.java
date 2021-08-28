package com.maco.common.utils;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static String ROLE_ADMIN = "admin";
    public static String ROLE_STAFF = "staff";
    public static String ROLE_CUSTOMER = "customer";
    public static List<String> UN_CHECK_URL = new ArrayList<>();
    static {
        UN_CHECK_URL.add("/user/getUserInfo");
        UN_CHECK_URL.add("/wx/handler");
        UN_CHECK_URL.add("/wx/jsapiSignature");
        UN_CHECK_URL.add("/MP_verify_h8uh6EWRJByiYxwq.txt");
    }
    public static boolean isUnCheckUrl(String url){
        for (String unCheckUrl : Constants.UN_CHECK_URL) {
            if (url.contains(unCheckUrl)) {
                return true;
            }
        }
        return false;
    }

}
