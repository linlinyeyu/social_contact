package com.zheng.user.service;

import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServicePinet;
import com.zheng.sms.enums.SmsTemplate;
import com.zheng.user.dao.model.VerifyCode;

/**
* VerifyCodeService接口
* Created by pinet on 2017/12/15.
*/
public interface VerifyCodeService extends BaseServicePinet<VerifyCode> {

    boolean verify(String phone, String code, SmsTemplate smsTemplate);

    BaseResult sendCode(String phone, SmsTemplate smsTemplate);

    //验证信息是否还有效
    boolean isActive(String phone, SmsTemplate smsTemplate);
}