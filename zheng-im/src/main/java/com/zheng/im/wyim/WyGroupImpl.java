package com.zheng.im.wyim;

import com.alibaba.fastjson.JSONObject;
import com.zheng.common.base.BaseResult;
import com.zheng.common.http.HttpMethod;
import com.zheng.common.util.WowCollections;
import com.zheng.im.base.GroupBase;
import com.zheng.im.base.HttpBase;
import com.zheng.im.domain.group.AddUserGroup;
import com.zheng.im.domain.group.CreateGroup;
import com.zheng.im.domain.group.EditGroup;
import com.zheng.im.util.FormatResultUtil;
import com.zheng.im.util.HttpUtil;
import com.zheng.im.util.UrlConstant;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by linlinyeyu on 2017/12/13.
 */

public class WyGroupImpl implements GroupBase {

    public WyGroupImpl(HttpBase httpBase){
        this.httpBase = httpBase;
    }

    private HttpBase httpBase;

    @Override
    public BaseResult createGroup(CreateGroup createGroup) {
        String entity = httpBase.sendRequest(UrlConstant.groupUrl,
                (JSONObject) JSONObject.toJSON(createGroup), HttpMethod.POST);

        return FormatResultUtil.judegResultMap(entity);
    }

    @Override
    public BaseResult addUserGroup(AddUserGroup addUserGroup) {

        String entity = httpBase.sendRequest(UrlConstant.addUserGroupUrl,
                (JSONObject) JSONObject.toJSON(addUserGroup), HttpMethod.POST);

        return FormatResultUtil.judegResultMap(entity);
    }

    @Override
    public BaseResult kickUser(final String tid, final String owner, final String member) {
        JSONObject jsonObject = new JSONObject(WowCollections.map(
                "tid", tid,
                "owner", owner,
                "member", member
        ));
        String entity = httpBase.sendRequest(UrlConstant.kickUserUrl,
                jsonObject, HttpMethod.POST);

        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult remove(final String tid, final String owner) {

        JSONObject jsonObject = new JSONObject(WowCollections.map(
                "tid", tid,
                "owner", owner
        ));
        String entity = httpBase.sendRequest(UrlConstant.removeUrl,
                jsonObject, HttpMethod.POST);

        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult editGroup(EditGroup editGroup) {

        String entity = httpBase.sendRequest(UrlConstant.editGroupUrl,
                (JSONObject) JSONObject.toJSON(editGroup), HttpMethod.POST);

        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult getGrouoUser(final String tids,final int ope) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.groupUserUrl);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{
            add(new BasicNameValuePair("tids",tids));
            add(new BasicNameValuePair("ope",String.valueOf(ope)));
        }};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judegResultMap(entity);
    }

    @Override
    public BaseResult changeOwner(final String tid, final String owner, final String newowner, final int leave) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.changeOwnerUrl);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{
            add(new BasicNameValuePair("tid",tid));
            add(new BasicNameValuePair("owner",owner));
            add(new BasicNameValuePair("newowner",newowner));
            add(new BasicNameValuePair("leave",String.valueOf(leave)));
        }};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult addManager(final String tid, final String owner, final String members) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.addManagerUrl);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{
            add(new BasicNameValuePair("tid",tid));
            add(new BasicNameValuePair("owner",owner));
            add(new BasicNameValuePair("members",members));
        }};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult removeManager(final String tid, final String owner, final String members) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.removeMangerUrl);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{
            add(new BasicNameValuePair("tid",tid));
            add(new BasicNameValuePair("owner",owner));
            add(new BasicNameValuePair("members",members));
        }};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult getGroups(final String accid) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.userGroupUrl);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{
            add(new BasicNameValuePair("accid",accid));
        }};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judegResultMap(entity);
    }

    @Override
    public BaseResult updGroupNickname(final String tid, final String owner, final String accid, final String nick) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.groupNickUrl);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{
            add(new BasicNameValuePair("tid",tid));
            add(new BasicNameValuePair("owner",owner));
            add(new BasicNameValuePair("accid",accid));
            add(new BasicNameValuePair("nick",nick));
        }};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult changeGroupNotify(final String tid, final String accid, final int ope) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.changeGroupNotify);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{
            add(new BasicNameValuePair("tid",tid));
            add(new BasicNameValuePair("accid",accid));
            add(new BasicNameValuePair("ope",String.valueOf(ope)));
        }};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult muteGroupUser(final String tid, final String owner, final String accid, final int mute) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.muteUserUrl);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{
            add(new BasicNameValuePair("tid",tid));
            add(new BasicNameValuePair("owner",owner));
            add(new BasicNameValuePair("accid",accid));
            add(new BasicNameValuePair("mute",String.valueOf(mute)));
        }};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult leave(final String tid, final String accid) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.leaveUrl);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{
            add(new BasicNameValuePair("tid",tid));
            add(new BasicNameValuePair("accid",accid));
        }};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult muteAll(final String tid, final String owner, final String mute) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.muteAllUrl);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{
            add(new BasicNameValuePair("tid",tid));
            add(new BasicNameValuePair("owner",owner));
            add(new BasicNameValuePair("mute",mute));
        }};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult getMuteUser(final String tid, final String owner) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.muteUserListUrl);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{
            add(new BasicNameValuePair("tid",tid));
            add(new BasicNameValuePair("owner",owner));
        }};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judegResultMap(entity);
    }
}
