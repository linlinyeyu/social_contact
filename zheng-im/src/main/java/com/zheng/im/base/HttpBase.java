package com.zheng.im.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zheng.common.exception.ArgumentErrorException;
import com.zheng.common.http.HttpMethod;
import com.zheng.common.util.StringUtil;

import javax.annotation.Resource;
import java.util.Map;
/**
 * Created by acer on 2017/12/17.
 */
public abstract class HttpBase {

    public HttpBase(Map<String, String> config){
        this.config = config;
    }

    protected Map<String, String> config;

    protected boolean checkParams(String... key){
        if(key != null){
            for (String k : key){
                if(config == null || StringUtil.isEmpty(config.get(k))){
                    throw new ArgumentErrorException("IM信息参数错误");
                }
            }
        }
        return true;

    }

    public JSONObject sendRequestReturnJson(String url, JSONObject jsonObject, HttpMethod method){
        String response = this.sendRequest(url, jsonObject, method);
        return JSON.parseObject(response);
    }

    public abstract String sendRequest(String url, JSONObject jsonObject, HttpMethod method);

}
