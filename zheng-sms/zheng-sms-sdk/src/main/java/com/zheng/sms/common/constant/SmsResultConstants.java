package com.zheng.oss.common.constant;

/**
 * oss系统接口结果常量枚举类
 * Created by shuzheng on 2017/4/18.
 */
public enum SmsResultConstants {

    FAILED(-1, "failed"),
    PHONE_NOT_MATCH(-2,"手机号不符合要求"),
    CONTENT_EMPTY(-3,"内容为空"),
    SUCCESS(0, "success");

    public int code;

    public String message;

    SmsResultConstants(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
