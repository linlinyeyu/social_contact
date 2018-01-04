package com.zheng.sms.sms;

import com.alibaba.fastjson.JSONObject;
import com.zheng.common.base.BaseResult;
import com.zheng.common.http.HttpMethod;
import com.zheng.common.http.YZLHeader;
import com.zheng.common.http.YZLHttpClientFactory;
import com.zheng.common.util.MD5Util;
import com.zheng.common.util.TimeUtil;
import com.zheng.common.util.WowCollections;
import com.zheng.oss.common.constant.SmsResult;
import com.zheng.oss.common.constant.SmsResultConstants;
import com.zheng.sms.SmsInterface;
import com.zheng.sms.enums.SmsTemplate;


import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2017/11/9.
 */
public class YunXunSms implements SmsInterface {


    @Override
    public BaseResult send(String phones, SmsTemplate smsTemplate, Map<String, String> data) {

        String url = "http://api.ytx.net/";
        String version = "201512";
        String accountSID = "7ff4b379f33c492183011e68e8c5d9f0";
        String authToken = "c19c672695104be4a7671c696b230e25";
        String func = "sms";
        String funcURL = "TemplateSMS.wx";
        url += version + "/sid/" + accountSID + "/" + func + "/" + funcURL;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "templateSms");
        jsonObject.put("appid", "2cd5b18f3d0e41a38a08460f8bd2fdb9");
        jsonObject.put("mobile", phones);
        jsonObject.put("templateId", 1243);
        String[] strings = new String[]{data.get("code"), data.get("min")};
        jsonObject.put("datas", strings);

        String timeStamp = TimeUtil.getTimeString(System.currentTimeMillis(),"yyyyMMddHHmmss");
        String encrypt = accountSID + "|" + timeStamp;
        String auth ="";

        try{
            auth = new String(Base64.getEncoder().encode(encrypt.getBytes("utf-8")));
        }catch (Exception e){
        }
        try{
            String sign = MD5Util.MD5(accountSID + authToken + timeStamp);
            url += "?sign=" + sign;
        }catch (Exception e){

        }



        List<YZLHeader> headers = YZLHeader.createHeaders(WowCollections.map("Authorization", auth, "content-type", "application/json"));

        String response = YZLHttpClientFactory.create().sendRequest(url, jsonObject, headers, HttpMethod.POST );

        System.out.println(response);
        return new SmsResult(SmsResultConstants.SUCCESS);
    }
}
