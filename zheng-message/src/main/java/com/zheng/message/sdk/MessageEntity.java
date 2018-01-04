package com.zheng.message.sdk;


import com.zheng.common.util.WowCollections;
import com.zheng.message.sdk.interfaces.Platform;

import java.util.*;

/**
 * Created by admin on 2017/3/8.
 */
public class MessageEntity {

    //发送者类型：后台管理员发送
    public static final int SENDER_TYPE_ADMIN = 0;

    //发送者类型：用户发送
    public static final int SENDER_TYPE_USER = 1;


    //跳转动作类型：无跳转动作
    public static final int ACTION_NULL = 1;

    //发送目标类型：发给指定用户
    public static final int TARGET_TYPE_USER = 1;


    public static final int TARGET_TYPE_TAGS = 2;

    //发送目标类型：发送给全部用户
    public static final int TARGET_TYPE_ALL = 3;


    //系统消息为推送发出
    public static final int SYSTEM_PUSH = 1;

    //系统消息为非推送发出
    public static final int SYSTEM_NO_PUSH = 0;

    //发送者id
    private Long senderId;

    //发送者类型
    private Integer senderType;

    //发送者名称
    private String senderName;

    //发送消息类型
    private Integer messageType = 1;

    //发送消息标题
    private String title;

    //发送消息内容
    private String content;

    //额外关联id，如订单消息推送时订单id
    private Long foreignId;

    //需发送的平台代号
    private Set<Platform> platforms;

    //需要发送的平台名
    private Set<String> platformNames;

    //是否发送所有平台
    private boolean sendAllPlatforms;

    //微信发送模板代号
    private Integer template;

    //制定发送目标
    private Integer targetType = TARGET_TYPE_USER;

    //用户uuid
    private Set<String> userIds;

    //用户Token
    private Set<String> registerTokens;

    private Set<String> androidRegisterTokens;

    private Set<String> iosRegisterTokens;

    private String voice ;

    //传入的参数
    private Map<String, String > extra;

    //推送的额外参数
    private Map<String, String> pushExtra;

    //微信的额外参数
    private Map<String, String> weixinExtra;

    //跳转方向
    private Integer actionId = ACTION_NULL;

    public Long getSenderId() {
        return this.senderId;
    }

    public MessageEntity setSenderId(Long senderId) {
        this.senderId = senderId;
        return this;
    }


    public String getVoice(){
        return voice;
    }

    public MessageEntity setVoice(String voice){
        this.voice = voice;
        return this;
    }


    public Integer getMessageType() {
        return this.messageType;
    }

    public MessageEntity setMessageType(Integer messageType) {
        this.messageType = messageType;
        return this;
    }



    public MessageEntity setPlatformNames(String... names){
        if(this.platformNames == null){
            this.platformNames = new HashSet<>();
        }else{
            this.platformNames.clear();
        }
        for(String name : names){
            this.platformNames.add(name);
        }
        return this;
    }

    public Set<String> getPlatformNames(){
        return this.platformNames;
    }

    public Set<String> getRegisterTokens(){
        return this.registerTokens;
    }

    public MessageEntity setRegisterTokens(String... tokens){
        if(this.registerTokens == null){
            this.registerTokens = new HashSet<>();
        }else{
            this.registerTokens.clear();
        }
        for(String token : tokens){
            this.registerTokens.add(token);
        }
        return this;
    }

    public Set<String> getAndroidRegisterTokens() {
        return androidRegisterTokens;
    }

    public MessageEntity setAndroidRegisterTokens(Set<String> androidRegisterTokens) {
        if(this.androidRegisterTokens == null){
            this.androidRegisterTokens = new HashSet<>();
        }else{
            this.androidRegisterTokens.clear();
        }
        for(String token : androidRegisterTokens){
            this.androidRegisterTokens.add(token);
        }
        return this;
    }

    public Set<String> getIosRegisterTokens() {
        return iosRegisterTokens;
    }

    public MessageEntity setIosRegisterTokens(Set<String> iosRegisterTokens) {
        if(this.iosRegisterTokens == null){
            this.iosRegisterTokens = new HashSet<>();
        }else{
            this.iosRegisterTokens.clear();
        }
        for(String token : iosRegisterTokens){
            this.iosRegisterTokens.add(token);
        }
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MessageEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageEntity setContent(String content) {
        this.content = content;
        return this;
    }


    public Set<Platform> getPlatforms() {
        return this.platforms;
    }

    public MessageEntity setPlatforms(Platform... platforms) {
        if(platforms == null){
            return this;
        }
        if(this.platforms == null){
            this.platforms = new HashSet<Platform>();
        }else{
            this.platforms.clear();
        }
        for(Platform platform : platforms){
            this.platforms.add(platform);
        }
        return this;
    }

    public boolean isSendAllPlatforms() {
        return sendAllPlatforms;
    }

    public void setSendAllPlatforms(boolean sendAllPlatforms) {
        this.sendAllPlatforms = sendAllPlatforms;
    }

    public Integer getTemplate() {
        return this.template;
    }

    public MessageEntity setTemplate(Integer template) {
        this.template = template;
        return this;
    }


    public Set<String> getUserIds() {
        return this.userIds;
    }

    public MessageEntity setUserIds(String... userIds) {

        if(this.userIds == null){
            this.userIds = new HashSet<>();
        }else{
            this.userIds.clear();
        }
        for(String userId : userIds){
            this.userIds.add(userId);
        }
        return this;
    }

    public Map<String, String> getExtra() {
        return this.extra;
    }

    public MessageEntity setExtra(Map<String, String> extra) {
        this.extra = extra;
        return this;
    }

    public Map<String, String > getPushExtra(){
        return this.pushExtra;
    }

    public Map<String, String> pushMergeExtra() {

        Map<String, String > pushMergeExtra = new HashMap<>();

        if(this.extra != null){
            for (String key : this.extra.keySet()) {
                pushMergeExtra.put(key, this.extra.get(key));
            }
        }

        if(this.pushExtra != null) {
            for (String key : this.pushExtra.keySet()) {
                pushMergeExtra.put(key, this.pushExtra.get(key));
            }
        }
        return pushMergeExtra;
    }

    public MessageEntity setPushExtra(Map<String, String> pushExtra) {
        this.pushExtra = pushExtra;
        return this;
    }

    public Map<String, String> getWeixinExtra(){
        return this.weixinExtra;
    }

    public Map<String, String> weixinMergeExtra() {
        Map<String, String > weixinMergeExtra = new HashMap<>();

        if(this.extra != null){
            for (String key : this.extra.keySet()) {
                weixinMergeExtra.put(key, this.extra.get(key));
            }
        }

        if(this.weixinExtra != null) {
            for (String key : this.weixinExtra.keySet()) {
                weixinMergeExtra.put(key, this.weixinExtra.get(key));
            }
        }
        return weixinMergeExtra;
    }

    public MessageEntity setWeixinExtra(Map<String, String> weixinExtra) {
        this.weixinExtra = weixinExtra;
        return this;
    }


    public Integer getSenderType() {
        return senderType;
    }

    public void setSenderType(Integer senderType) {
        this.senderType = senderType;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }
}
