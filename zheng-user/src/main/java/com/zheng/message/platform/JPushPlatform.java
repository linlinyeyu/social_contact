package com.zheng.message.platform;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.zheng.action.dao.model.Action;
import com.zheng.action.service.ActionService;
import com.zheng.common.base.BaseResult;
import com.zheng.common.util.FormatResponseUtil;
import com.zheng.common.util.StringUtil;
import com.zheng.common.util.WowCollections;
import com.zheng.message.sdk.MessageClient;
import com.zheng.message.sdk.MessageEntity;
import com.zheng.message.sdk.interfaces.PushMessageService;
import com.zheng.user.dao.model.UcenterUser;
import com.zheng.user.service.UcenterUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * Created by acer on 2017/12/25.
 */
public class JPushPlatform implements com.zheng.message.sdk.interfaces.Platform {

    private String appKey;

    private String appSecret;

    private String androidKey;

    private String androidSecret;

    private String iOSKey;

    private String iOSSecret;

    private int limit = 20;

    private Logger logger = LoggerFactory.getLogger(JPushPlatform.class);

    @Autowired
    ActionService actionService;

    @Autowired
    Environment env;

    @Autowired
    UcenterUserService ucenterUserService;

    private void sliceUsers(MessageEntity messageEntity, int length){
        if(messageEntity != null){

        }
    }

    @Override
    public BaseResult send(MessageClient client, MessageEntity messageEntity) {


        JsonObject jsonObject = new JsonObject();
        //添加额外数据
        Map<String, String> extra = messageEntity.pushMergeExtra();
        if(extra != null){
            Set<Map.Entry<String,String>> set = extra.entrySet();

            for(Map.Entry<String,String> entry : set){
                jsonObject.addProperty(entry.getKey(), entry.getValue());
            }
        }
        jsonObject.addProperty("voice", messageEntity.getVoice());
        Audience androidAudience = null;
        Audience iOSAudience = null;
        Audience allAudience = null;

        boolean isOneKey = !StringUtil.isEmpty(appKey);
        if(messageEntity.getTargetType() == MessageEntity.TARGET_TYPE_USER){
            Map<String, List<String>> registrationTokens = getRegisterTokens(messageEntity);

            if(isOneKey){
                List<String> all = registrationTokens.get("all");
                if(all != null && all.size() > limit ){
                    String[] newTokens = new String[all.size() - limit];
                    for(int i = limit; i < all.size(); i ++){
                        newTokens[i - limit] = all.get(i);
                    }
                    messageEntity.setPlatforms(this);
                    messageEntity.setRegisterTokens(newTokens);
                    client.pushCache(messageEntity);
                    all = all.subList(0, limit);
                }
                allAudience = Audience.registrationId(all);
            }else{
                List<String> androidTokens = registrationTokens.get("android");
                androidAudience = Audience.registrationId(androidTokens);

                List<String> iOStokens = registrationTokens.get("ios");
                iOSAudience = Audience.registrationId(iOStokens);
            }

        }else if(messageEntity.getTargetType() == MessageEntity.TARGET_TYPE_ALL){
            if(isOneKey){
                allAudience = Audience.all();
            }else{
                androidAudience = Audience.all();
                iOSAudience = Audience.all();
            }
        }

        if(allAudience == null && androidAudience == null && iOSAudience == null){
            return FormatResponseUtil.formatResponse(-100, "没有发送目标");
        }



        Action action = actionService.selectByPrimaryKey(messageEntity.getActionId());
        if(action == null){
            action = new Action();
        }

        //安卓的发送内容
        AndroidNotification androidNotification = AndroidNotification.newBuilder()
                .setTitle(messageEntity.getTitle())
                .setAlert(messageEntity.getContent())
                .addExtra("params",  jsonObject)
                .addExtra("jump", action.getAndroidParam())
                .addExtra("need_login", action.getNeedLogin()).build();


        //iOS的发送内容
        IosNotification iosNotification = IosNotification.newBuilder()
                .setAlert(messageEntity.getContent())
                .setSound(messageEntity.getVoice() + ".caf")
                .setBadge(1)
                .addExtra("jump", action.getIosParam())
                .addExtra("need_login", action.getNeedLogin())
                .addExtra("params",  jsonObject).build();



        String[] profiles = env.getActiveProfiles();
        boolean isProd = true;

        for(String profile : profiles){
            if("dev".equals(profile)){
                isProd = false;
                break;
            }
        }

        //单发安卓
        if(androidAudience != null){
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(androidAudience)
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(androidNotification).build()).build();
            BaseResult baseResult = push(payload, androidKey, androidSecret);
            logger.info("android:" + JSON.toJSONString(baseResult) );

        }

        //单发iOS
        if(iOSAudience != null){
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(iOSAudience)
                    .setOptions(Options.newBuilder().setApnsProduction(isProd).build())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(
                            iosNotification).build()).build();
            BaseResult baseResult = push(payload, iOSKey, iOSSecret);

            logger.info("ios:" + JSON.toJSONString(baseResult) );

        }

        //发送安卓和iOS
        if(allAudience != null){
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.all())
                    .setAudience(allAudience)
                    .setOptions(Options.newBuilder().setApnsProduction(isProd).build())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(
                                    iosNotification)
                            .addPlatformNotification(
                                    androidNotification)
                            .build()).build();
            BaseResult baseResult = push(payload, appKey, appSecret);
            logger.info("all:" + JSON.toJSONString(baseResult) );
            return baseResult;
        }

        return FormatResponseUtil.formatResponse();

    }

    @Override
    public String getBeanName() {
        return "jpushPlatform";
    }

    private Map<String, List<String>> getRegisterTokens(MessageEntity entity){

        List<String> allRegisterToken = new ArrayList<>();
        List<String> androidRegisterTokens = new ArrayList<>();
        List<String> iOSRegisterTokens = new ArrayList<>();


        if (entity.getUserIds() != null && entity.getUserIds().size() > 0){
            List<UcenterUser> users = ucenterUserService.selectUsersByUuid(new ArrayList<>(entity.getUserIds()));

            for (UcenterUser user : users){
                if(StringUtil.isEmpty(user.getJpushToken())){
                    continue;
                }
                if(user.getTerminal() == 1){
                    iOSRegisterTokens.add(user.getJpushToken());
                }else if(user.getTerminal() == 2){
                    androidRegisterTokens.add(user.getJpushToken());
                }
                allRegisterToken.add(user.getJpushToken());
            }
        }

        if(entity.getAndroidRegisterTokens() != null){
            for(String token : entity.getAndroidRegisterTokens()){
                androidRegisterTokens.add(token);
                allRegisterToken.add(token);
            }
        }


        if(entity.getIosRegisterTokens() != null){
            for(String token : entity.getIosRegisterTokens()){
                iOSRegisterTokens.add(token);
                allRegisterToken.add(token);
            }
        }

        if(entity.getRegisterTokens() != null){
            for(String token : entity.getRegisterTokens()){
                allRegisterToken.add(token);
            }
        }
        WowCollections.uniqList(allRegisterToken);

        return WowCollections.map("all", allRegisterToken, "android", androidRegisterTokens, "ios", iOSRegisterTokens);

    }

    private BaseResult push(PushPayload payload, String appKey, String appSecret){
        JPushClient jPushClient = new JPushClient(appSecret, appKey, null, ClientConfig.getInstance());
        try {
            PushResult result = jPushClient.sendPush(payload);

            return FormatResponseUtil.formatResponse(result.statusCode, result.getOriginalContent());
        } catch (APIConnectionException e) {
            return FormatResponseUtil.formatResponse(-200, "Connection error, should retry later");
        } catch (APIRequestException e) {
            e.printStackTrace();
            return FormatResponseUtil.formatResponse(-200, "Should review the error, and fix the request");
        }catch (Exception e){
            return FormatResponseUtil.formatResponse(-200, e.getMessage());
        }
    }


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAndroidKey() {
        return androidKey;
    }

    public void setAndroidKey(String androidKey) {
        this.androidKey = androidKey;
    }

    public String getAndroidSecret() {
        return androidSecret;
    }

    public void setAndroidSecret(String androidSecret) {
        this.androidSecret = androidSecret;
    }

    public String getiOSKey() {
        return iOSKey;
    }

    public void setiOSKey(String iOSKey) {
        this.iOSKey = iOSKey;
    }

    public String getiOSSecret() {
        return iOSSecret;
    }

    public void setiOSSecret(String iOSSecret) {
        this.iOSSecret = iOSSecret;
    }

}
