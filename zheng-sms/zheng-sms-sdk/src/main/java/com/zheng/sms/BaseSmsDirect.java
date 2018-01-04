package com.zheng.sms;

import com.zheng.common.util.PropertiesFileUtil;
import com.zheng.common.util.StringUtil;
import com.zheng.common.util.ValidatorUtil;
import com.zheng.oss.common.constant.SmsResult;
import com.zheng.oss.common.constant.SmsResultConstants;
import com.zheng.sms.bean.SmsMessage;
import com.zheng.sms.enums.SmsTemplate;


import java.util.Map;

/**
 * Created by acer on 2017/11/9.
 */
public abstract class BaseSmsDirect implements SmsInterface {

    String sign = PropertiesFileUtil.getInstance("/code.properties").get("sign");

    @Override
    public SmsResult send(String phones, SmsTemplate smsTemplate, Map<String,String> data) {
        SmsMessage smsMessage = new SmsMessage();

        if(this.needSign()){
            smsMessage.setSign(sign);
        }

        String message = PropertiesFileUtil.getInstance("/code.properties").get(smsTemplate.getName());
        if(message == null){
            return new SmsResult(SmsResultConstants.FAILED);
        }


        message = message.replace("%min", data.get("min")).replace("%code", data.get("code"));

        smsMessage.setPhones(phones).setMessage(message);
        String content = smsMessage.getContent();
        if(!ValidatorUtil.validPhone(phones)){
            return new SmsResult(SmsResultConstants.PHONE_NOT_MATCH);
        }

        if(StringUtil.isEmpty(content)){
            return new SmsResult(SmsResultConstants.CONTENT_EMPTY);
        }
        return this.send(phones, content);
    }

    protected abstract boolean needSign();

    protected abstract SmsResult send(String phones, String content);
}
