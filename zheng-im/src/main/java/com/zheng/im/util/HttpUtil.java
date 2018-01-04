package com.zheng.im.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by linlinyeyu on 2017/12/12.
 */
public class HttpUtil {
    private static DefaultHttpClient httpClient = new DefaultHttpClient();

    public static String post(HttpPost httpPost, Object obj){
        List<NameValuePair> param = new ArrayList<>();
        Field fields[] = obj.getClass().getDeclaredFields();
        try {
            for (Field field:fields){
                String name = field.getName();
                if (name.equalsIgnoreCase("serialVersionUID")){
                    continue;
                }
                String upName = name.substring(0,1).toUpperCase()+name.substring(1);
                String value = String.valueOf(obj.getClass().getMethod("get"+upName).invoke(obj));
                if (value == "null"){
                    continue;
                }
                param.add(new BasicNameValuePair(name,value));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(param,"utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String entity = EntityUtils.toString(response.getEntity(),"utf-8");
            return entity;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "";
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return "";
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String post(HttpPost httpPost,List<NameValuePair> list){
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list,"utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String entity = EntityUtils.toString(response.getEntity(),"utf-8");
            return entity;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static HttpPost setHeader(String url){
        HttpPost httpPost = new HttpPost(url);
        String curTime = String.valueOf((new Date()).getTime()/1000L);
        String nonce = GenerateUtil.getNonce();
        String checkSum = CheckSumBuilder.getCheckSum(UrlConstant.appSecret,nonce,curTime);

        //设置请求header
        httpPost.addHeader("AppKey",UrlConstant.appKey);
        httpPost.addHeader("Nonce",nonce);
        httpPost.addHeader("CurTime",curTime);
        httpPost.addHeader("CheckSum",checkSum);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");

        return httpPost;
    }
}
