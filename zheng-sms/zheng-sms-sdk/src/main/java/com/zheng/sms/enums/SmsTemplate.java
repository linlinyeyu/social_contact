package com.zheng.sms.enums;

import com.zheng.common.util.StringUtil;

/**
 * Created by acer on 2017/11/9.
 */
public enum SmsTemplate {
    REGISTER("regist", "", 15),
    LOGIN("login",  "", 15),
    FORGET("forget", "", 15),
    EDITPWD("editpwd",  "", 15),
    VERIFYPHONE("verifyphone", "", 15),
    EDITPHONE("editphone", "", 15);

    private String name;


    private String template;

    //在验证通过后的有效可操作时间，以分钟为单位
    private int activeTime;

    SmsTemplate(String name ,  String template, int activeTime){
        this.name = name;
        this.template = template;
        this.activeTime = activeTime;
    }

    public int getActiveTime(){ return this.activeTime;}

    public String getName(){
        return this.name;
    }

    public String getTemplate(){
        return this.template;
    }

    public static SmsTemplate getTemplateByName(String name){
        if(StringUtil.isEmpty(name)){
            return null;
        }
        SmsTemplate[] smsTemplates = values();
        for(SmsTemplate smsTemplate : smsTemplates){
            if(name.equals(smsTemplate.getName()) ){
                return smsTemplate;
            }
        }
        return null;
    }
}
