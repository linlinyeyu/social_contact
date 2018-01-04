package com.zheng.sms;

import com.zheng.common.base.BaseResult;
import com.zheng.oss.common.constant.SmsResult;
import com.zheng.sms.enums.SmsTemplate;

import java.util.Map;

public interface SmsInterface {

	BaseResult send(String phones, SmsTemplate smsTemplate, Map<String,String> data);


	
}
