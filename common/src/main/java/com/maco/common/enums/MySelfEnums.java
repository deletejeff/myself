package com.maco.common.enums;

/**
 * @Author machao
 * @Date 2017/10/10 10:57
 * 全局错误信息枚举类
 **/
public class MySelfEnums {
    public enum MySelfCommEnums {
        //成功
        SUCCESS("0", "成功"),
        FAIL("1", "失败"),
        EXCEPTION_FAIL("-1","程序异常"),
        RUNTIME_EXCEPTION_FAIL("-1", "程序异常"),
        USER_FAIL("-88", "未获取到用户信息"),
        ADMIN_FAIL("-99", "您不是管理员，没有权限"),
        STAFF_FAIL("-100", "您不是员工，没有权限"),
        ADMIN_OR_STAFF_FAIL("-101", "您不是管理员也不是员工，没有权限"),
        TOKEN_VALIDATE_FAIL("-999", "请求头中token验证失败"),
        PARAMS_FAIL("10001", "参数异常"),
        END_DATE_FAIL("10002", "结束日期不能早于开始日期"),
        START_DATE_FAIL("10003", "优惠券起始有效期至少从下月1号开始"),
        LOGIN_FAIL("100010","登录失败,用户名或密码错误,剩余重试次数：%s次"),
        LOGIN_FAIL_("100011","登录失败,请重新登录"),
        API_SWITCH_OFF("10002", "接口暂时关闭，无法调用"),
        LOGIN_EXPIRE_TIME_NOT_ARRIVE("100020","密码错误5次,请过%s分钟再登录"),
        JSON_TRANSLATE_FAIL("10003", "requestData数据解析失败"),
        JSON_TRANSLATE_ERROR("-10003", "requestData数据解析异常"),
        SAVE_IMG_TO_LOCAL("-10004","保存图片到本地异常"),
        IMAGE_TYPE_ERROR("-10005","图片格式错误"),
        IMAGE_EXISTS("-10006","该名称图片已经存在"),
        UNABLE_GET_IMAGE_URL("-10007","提供的图片URL无法访问"),
        TEMPLATE_HANDLE_ERROR("20001","模板消息事件推送结果XML处理失败"),
        OUT_OF_MEMORY_ERROR("99999","压力过大，请稍后再试"),
        ;
        public String code;
        public String message;


        MySelfCommEnums(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

}
