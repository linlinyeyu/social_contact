package com.zheng.sms.factory;

import com.zheng.common.util.WowCollections;
import com.zheng.sms.SmsInterface;
import com.zheng.sms.enums.SmsTemplate;
import com.zheng.sms.enums.SmsType;
import com.zheng.sms.sms.NetEaseSms;
import com.zheng.sms.sms.YunXunSms;

import java.util.HashMap;
import java.util.Map;


public class SmsFactory {
	
	private static Map<SmsType,SmsInterface> sms = new HashMap<>();
	
	public static SmsInterface create(){
		return create(SmsType.NETEASE);
	}

	public static SmsInterface create(SmsType type){
		if(sms.get(type)==null){
			SmsInterface smsClient = null;
			switch(type){

				case YunXun:
					smsClient = new YunXunSms();
					break;
				case NETEASE:
					smsClient = new NetEaseSms();
					break;
			}
			sms.put(type, smsClient);
		}
		return sms.get(type);
	}

	public static void main(String[] args){
		SmsFactory.create().send("15906716507", SmsTemplate.REGISTER, WowCollections.map("code", "123456"));
	}
}
