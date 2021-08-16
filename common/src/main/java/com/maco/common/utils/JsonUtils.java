package com.maco.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author machao
 */
public class JsonUtils {
    public static String toJson(Object obj) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        return gson.toJson(obj);
    }
}
