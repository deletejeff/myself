package com.maco.common.po;

import com.maco.common.enums.MySelfEnums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author machao
 * @Date 2018/1/19 18:16
 **/
public class ResultMap {
    /**
     * 返回码
     */
    private String retcode;
    /**
     * 返回信息
     */
    private String retmsg;

    /**
     * 数据
     */
    private Map<String, Object> data = new HashMap<String, Object>();

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public void setRetcode(MySelfEnums.MySelfCommEnums enumName) {
        this.retcode = enumName.getCode();
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public void setRetmsg(MySelfEnums.MySelfCommEnums enumName) {
        this.retmsg = enumName.getMessage();
    }

    public void setRetmsg(Exception e) {
        if (e.getMessage() != null && !"".equals(e.getMessage())) {
            this.retmsg = e.getMessage();
        } else {
            this.retmsg = e.toString();
        }
    }

    public void success(){
        this.retcode = MySelfEnums.MySelfCommEnums.SUCCESS.getCode();
        this.retmsg = MySelfEnums.MySelfCommEnums.SUCCESS.getMessage();
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setRetcodeRetmsg(MySelfEnums.MySelfCommEnums enumName) {
        this.retcode = enumName.getCode();
        this.retmsg = enumName.getMessage();
    }

    public void setRetcodeRetmsg(String retcode, String retmsg) {
        this.retcode = retcode;
        this.retmsg = retmsg;
    }

    public void setRetcodeRetmsg(MySelfEnums.MySelfCommEnums enumName, Exception e) {
        this.retcode = enumName.getCode();
        if (e.getMessage() != null && !"".equals(e.getMessage())) {
            this.retmsg = e.getMessage();
        } else {
            this.retmsg = e.toString();
        }
    }

    public void setRetcodeRetmsg(Exception e) {
        this.retcode = MySelfEnums.MySelfCommEnums.EXCEPTION_FAIL.getCode();
        if (e.getMessage() != null && !"".equals(e.getMessage())) {
            this.retmsg = e.getMessage();
        } else {
            this.retmsg = e.toString();
        }
    }

    public void setRetcodeRetmsg(String message, Exception e) {
        this.retcode = MySelfEnums.MySelfCommEnums.EXCEPTION_FAIL.getCode();
        this.retmsg = message;
    }
}
