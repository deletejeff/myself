package com.maco.common.po;

import com.maco.common.enums.MySelfEnums;
import com.maco.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author machao
 * @Date 2019/3/21 16:25
 **/
@Slf4j
public class ResultMapUtil {
    public static ResultMap success(List<String> dataKey, List<Object> data) {
        ResultMap returnMap = new ResultMap();
        returnMap.success();
        for (int i = 0; i < dataKey.size(); i++) {
            returnMap.getData().put(dataKey.get(i), data.get(i));
        }
        log.info(JsonUtils.toJson(returnMap));
        return returnMap;
    }

    public static ResultMap success(String dataKey, Object data) {
        ResultMap returnMap = new ResultMap();
        returnMap.success();
        returnMap.getData().put(dataKey, data);
        log.info(JsonUtils.toJson(returnMap));
        return returnMap;
    }

    public static ResultMap success(String message) {
        ResultMap returnMap = new ResultMap();
        returnMap.setRetcode(MySelfEnums.MySelfCommEnums.SUCCESS.getCode());
        returnMap.setRetmsg(message);
        log.info(JsonUtils.toJson(returnMap));
        return returnMap;
    }

    public static ResultMap success() {
        ResultMap returnMap = new ResultMap();
        returnMap.success();
        log.info(JsonUtils.toJson(returnMap));
        return returnMap;
    }

    public static ResultMap failure() {
        ResultMap returnMap = new ResultMap();
        returnMap.failure();
        log.info(JsonUtils.toJson(returnMap));
        return returnMap;
    }

    public static ResultMap error(MySelfEnums.MySelfCommEnums enumName) {
        ResultMap returnMap = new ResultMap();
        returnMap.setRetcode(enumName.getCode());
        returnMap.setRetmsg(enumName.getMessage());
        log.error(enumName.getCode() + " ---> " + enumName.getMessage());
        return returnMap;
    }

    public static ResultMap error(MySelfEnums.MySelfCommEnums enumName, String retmsg) {
        ResultMap returnMap = new ResultMap();
        returnMap.setRetcode(enumName.getCode());
        returnMap.setRetmsg(retmsg);
        log.error(enumName.getCode() + " ---> " + retmsg);
        return returnMap;
    }

    public static ResultMap error(ResultMap resultMap) {
        ResultMap returnMap = new ResultMap();
        returnMap.setRetcode(resultMap.getRetcode());
        returnMap.setRetmsg(resultMap.getRetmsg());
        log.error(resultMap.getRetcode() + " ---> " + resultMap.getRetmsg());
        return returnMap;
    }

    public static ResultMap exception() {
        ResultMap returnMap = new ResultMap();
        returnMap.setRetcodeRetmsg(MySelfEnums.MySelfCommEnums.EXCEPTION_FAIL);
        log.error(returnMap.getRetcode() + " ---> " + returnMap.getRetmsg());
        return returnMap;
    }

    public static ResultMap exception(String msg) {
        ResultMap returnMap = new ResultMap();
        returnMap.setRetcode(MySelfEnums.MySelfCommEnums.EXCEPTION_FAIL);
        returnMap.setRetmsg(msg);
        log.error(returnMap.getRetcode() + " ---> " + returnMap.getRetmsg());
        return returnMap;
    }

    public static ResultMap exception(MySelfEnums.MySelfCommEnums enumName, Exception e) {
        ResultMap returnMap = new ResultMap();
        returnMap.setRetcode(enumName.getCode());
        returnMap.setRetmsg(e);
        log.error(enumName.getCode() + " ---> " + (StringUtils.hasLength(e.getMessage()) ? e.toString() : e.getMessage()));
        return returnMap;
    }

    public static ResultMap exception(Exception e) {
        ResultMap returnMap = new ResultMap();
        returnMap.setRetcode(MySelfEnums.MySelfCommEnums.EXCEPTION_FAIL.getCode());
        returnMap.setRetmsg(e);
        log.error(MySelfEnums.MySelfCommEnums.EXCEPTION_FAIL.getCode() + " ---> " + (StringUtils.hasLength(e.getMessage()) ? e.toString() : e.getMessage()));
        return returnMap;
    }
}
