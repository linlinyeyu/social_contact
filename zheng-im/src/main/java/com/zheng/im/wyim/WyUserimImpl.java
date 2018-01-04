package com.zheng.im.wyim;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zheng.common.base.BaseResult;
import com.zheng.common.http.HttpMethod;
import com.zheng.im.base.HttpBase;
import com.zheng.im.base.UserBase;
import com.zheng.im.domain.user.Friend;
import com.zheng.im.domain.user.User;
import com.zheng.im.util.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by linlinyeyu on 2017/12/12.
 */

public class WyUserimImpl implements UserBase {

    public WyUserimImpl(HttpBase httpBase){
        this.httpBase = httpBase;
    }

    private HttpBase httpBase;

    @Override
    public BaseResult imRegister(User user){
        JSONObject jsonObject = httpBase.sendRequestReturnJson(
                UrlConstant.regUrl,
                (JSONObject) JSON.toJSON(user),
                HttpMethod.POST);
        return FormatResultUtil.judgeResult(jsonObject);
    }

    @Override
    public BaseResult addFriend(Friend friend) {
        String entity = httpBase.sendRequest(UrlConstant.addUrl,
                (JSONObject) JSON.toJSON(friend),
                HttpMethod.POST
        );
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult getFriends(Friend friend) {
        JSONObject jsonObject = httpBase.sendRequestReturnJson(UrlConstant.getUrl, (JSONObject) JSON.toJSON(friend),
                HttpMethod.POST);
        return FormatResultUtil.judgeResult(jsonObject);
    }

    @Override
    public BaseResult getUserCard(String[] accid) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.cardUrl);
        List<NameValuePair> list = new ArrayList<>();
        String data = JSON.toJSONString(accid);
        list.add(new BasicNameValuePair("accids",data));
        String entity = HttpUtil.post(httpPost,list);

        return FormatResultUtil.judegResultMap(entity);
    }

    @Override
    public BaseResult updUserId(String accid) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accid", accid);
        JSONObject result = httpBase.sendRequestReturnJson(UrlConstant.updIdUrl, jsonObject, HttpMethod.POST);

        return FormatResultUtil.judgeResult(result);
    }

    @Override
    public BaseResult updAndGetUser(String accid) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accid", accid);
        JSONObject result = httpBase.sendRequestReturnJson(UrlConstant.updAndGetUrl, jsonObject, HttpMethod.POST);

        return FormatResultUtil.judgeResult(result);
    }

    @Override
    public BaseResult unBlockUser(String accid) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.unBlockUrl);
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("accid",accid));
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult blockUser(String accid) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.blockUrl);
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("accid",accid));
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult udpUser(User user) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.updUserUrl);
        String entity = HttpUtil.post(httpPost,user);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult setDeskToApp(String accid, String donnopOpen) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.destToAppUrl);
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("accid",accid));
        list.add(new BasicNameValuePair("donnopOpen",donnopOpen));
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult updFriend(Friend friend) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.updFriendUrl);
        String entity = HttpUtil.post(httpPost,friend);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult delFriend(Friend friend) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.delFriendUrl);
        String entity = HttpUtil.post(httpPost,friend);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult setSpecialRelation(final String accid, final String targetAcc, final int relationType, final int value) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.setSpecialUrl);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{
            add(new BasicNameValuePair("accid",accid));
            add(new BasicNameValuePair("targetAcc",targetAcc));
            add(new BasicNameValuePair("relationType",String.valueOf(relationType) ));
            add(new BasicNameValuePair("value",String.valueOf(value)));
        }};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult getSpecialRelationList(final String accid) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.specialUrl);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{add(new BasicNameValuePair("accid",accid));}};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judegResultMap(entity);
    }
}
