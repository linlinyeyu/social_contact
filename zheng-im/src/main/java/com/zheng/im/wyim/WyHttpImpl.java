package com.zheng.im.wyim;

import com.alibaba.fastjson.JSONObject;
import com.zheng.common.http.HttpMethod;
import com.zheng.common.http.YZLHeader;
import com.zheng.common.http.YZLHttpClientFactory;
import com.zheng.common.util.WowCollections;
import com.zheng.im.base.HttpBase;
import com.zheng.im.util.CheckSumBuilder;
import com.zheng.im.util.GenerateUtil;
import com.zheng.im.util.UrlConstant;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2017/12/17.
 */

public class WyHttpImpl extends HttpBase {


    public WyHttpImpl(Map<String, String> config) {
        super(config);
    }

    @Override
    public String sendRequest(String url, JSONObject jsonObject, HttpMethod method) {
        checkParams("appSecret", "appKey");

        System.out.println("appKey:" + this.config.get("appKey") + "; appSecret:" + this.config.get("appSecret"));

        String curTime = String.valueOf((new Date()).getTime()/1000L);
        String nonce = GenerateUtil.getNonce();
        String checkSum = CheckSumBuilder.getCheckSum(this.config.get("appSecret"),nonce,curTime);

        List<YZLHeader> headerList = YZLHeader.createHeaders(WowCollections.map(
                "AppKey", this.config.get("appKey"),
                "Nonce", nonce,
                "CurTime", curTime,
                "CheckSum", checkSum,
                "Content-Type", "application/x-www-form-urlencoded;charset=utf8"
        ));

        return YZLHttpClientFactory.create().sendRequest(url, jsonObject, headerList, method);

    }
}
