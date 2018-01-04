package com.zheng.sms.bean;



import com.zheng.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class SmsMessage {
	//发送的消息内容
	private String message="";
	//发送手机号，多个手机号间用“,”隔开
	private String phones="";
	//发送消息的签名
	private String sign="";
	//签名的位置，0签名放尾部，其他数字签名放头部，默认放尾部
	private int sign_position=0;
	
	public String getMessage() {
		return message;
	}

	public SmsMessage setMessage(String message) {
		if(message == null){
			message="";
		}
		this.message = message;
		return this;
	}

	public String getPhones() {
		return phones;
	}

	public SmsMessage setPhones(String phones) {
		if(phones==null){
			phones="";
		}
		this.phones = phones;
		return this;
	}
	
	public SmsMessage setPhones(List<String> phones){
		String phone = "";
		for(String p : phones){
			phone = phone+p+",";
			System.out.println(phone);
		}
		if(!"".equals(phone))
		this.phones = phone.substring(0, phone.length()-1);
		return this;
	}
	
	public String getSign() {
		return sign;
	}

	public SmsMessage setSign(String sign) {
		if(sign == null){
			sign="";
		}
		this.sign = sign;
		return this;
	}

	public int getSign_position() {
		return sign_position;
	}

	public SmsMessage setSign_position(int sign_position) {
		this.sign_position = sign_position;
		return this;
	}
	
	//获取消息与签名合并后的字符串
	public String getContent(){
		if(StringUtil.isEmpty(message)){
			return "";
		}

		if(!StringUtil.isEmpty(sign)){
			if(!sign.endsWith("】") || !('【'==sign.charAt(0))){
				sign="【"+sign+"】";
			}
		}

		return sign_position==0?(message+sign):(sign+message);
	}
	
	public String toString(){
		return "[message="+message+";phones="+phones+";sign="+sign+";sign_position="+sign_position+"]";
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1232134");
		list.add("");
		list.add("");
		list.add("");
		SmsMessage s = new SmsMessage().setPhones(list);
		String[] ss = s.getPhones().split(",");
		System.out.println(s);
		System.out.println(ss.length);
	}
}
