package com.zheng.sms.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zheng.common.base.BaseResult;
import com.zheng.common.http.HttpMethod;
import com.zheng.common.http.YZLHeader;
import com.zheng.common.http.YZLHttpClientFactory;
import com.zheng.common.util.*;
import com.zheng.oss.common.constant.SmsResult;
import com.zheng.oss.common.constant.SmsResultConstants;
import com.zheng.sms.SmsInterface;
import com.zheng.sms.enums.SmsTemplate;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import static com.zheng.common.http.YZLHeader.createHeaders;
import static com.zheng.common.util.CheckSumBuilder.getCheckSum;
import static com.zheng.common.util.StringUtil.getRandomString;
import static org.apache.zookeeper.ZooDefs.OpCode.auth;

/**
 * Created by acer on 2017/11/9.
 */
public class NetEaseSms implements SmsInterface {

    private String url = "https://api.netease.im/sms/sendcode.action";

    private String appKey = "ac0e318dc66b06f53242f1ecc4d39cce";

    private String appSecret = "09c26e387410";




    @Override
    public BaseResult send(String phones, SmsTemplate smsTemplate, Map<String, String> data) {

        String templateId = "3124350";

        String curTime = String.valueOf(System.currentTimeMillis() / 1000);

        String nonce = StringUtil.getRandomString(32);


        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);


        JSONObject jsonObject = new JSONObject(WowCollections.map(
                "templateid", templateId,
                "mobile", phones,
                "authCode", data.get("code")
        ));



        List<YZLHeader> headers = YZLHeader.createHeaders(WowCollections.map(
                "AppKey", this.appKey,
                "Nonce", nonce,
                "CurTime", curTime,
                "CheckSum", checkSum,
                "Content-Type" , "application/x-www-form-urlencoded;charset=utf-8"
        ));

        JSONObject response = YZLHttpClientFactory.create().sendRequestDecodeByJSON(url, jsonObject, headers, HttpMethod.POST );

        int code = response.getIntValue("code");
        System.out.println(JSON.toJSONString(response));

        return response != null && code == 200 ? FormatResponseUtil.formatResponse() : FormatResponseUtil.formatResponse(-100, response.getString("msg"));
    }
}
