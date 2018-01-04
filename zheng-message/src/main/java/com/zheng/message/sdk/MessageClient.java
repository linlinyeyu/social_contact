package com.zheng.message.sdk;


import com.alibaba.fastjson.JSON;
import com.zheng.common.base.BaseResult;
import com.zheng.common.constants.CacheConstants;
import com.zheng.common.exception.ArgumentErrorException;
import com.zheng.common.util.*;
import com.zheng.message.sdk.interfaces.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.zheng.common.util.SpringContextUtil.getBean;
import static java.awt.Color.red;

/**
 * Created by admin on 2017/3/9.
 */
public class MessageClient {

    private static final String DEFAULT_QUEUE_NAME = "message_queue";

    private static final String DEFAULT_MINUTE_NAME = "message_minute";

    private String queueName = DEFAULT_QUEUE_NAME;

    private String minuteName = DEFAULT_MINUTE_NAME;

    //一分钟最多能推送的次数
    private static final int MAX_TIME = 10;

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    private List<String> allForPlatForms = new ArrayList<>();

    public List<String> getAllForPlatForms() {
        return allForPlatForms;
    }

    public void setAllForPlatForms(List<String> allForPlatForms) {
        this.allForPlatForms = allForPlatForms;
    }

    private static Logger logger = LoggerFactory.getLogger(MessageClient.class);

    private String validate(MessageEntity messageEntity){
        if(messageEntity == null){
            return "请传入消息" ;
        }

        if(StringUtil.isEmpty(messageEntity.getTitle())){
            return "请传入消息标题";
        }

        if(StringUtil.isEmpty(messageEntity.getContent())){
             return "请传入消息内容";
        }

        if((messageEntity.getPlatforms() == null || messageEntity.getPlatforms().size() == 0)
                && (messageEntity.getPlatformNames() == null || messageEntity.getPlatformNames().size() == 0) ){
            return "请至少选择一个发送平台";
        }

        if(messageEntity.getMessageType() == null) {
            return "请指定正确的消息类型";
        }

        if(messageEntity.getTargetType() == null ){
            return "请指定正确目标类型";
        }

        if(messageEntity.getTargetType() == MessageEntity.TARGET_TYPE_USER &&
                (messageEntity.getUserIds() == null  &&  messageEntity.getRegisterTokens() == null)){
            return "请传入用户信息";
        }

        return null;
    }

    public void pushCache(MessageEntity messageEntity){
        String error = validate(messageEntity);
        if(!StringUtil.isEmpty(error)){
            throw new ArgumentErrorException(error);
        }
        if(messageEntity.getPlatforms() != null){
            Set<Platform> sets = messageEntity.getPlatforms();
           String[] platformNames = new String[sets.size()];
            int i = 0;
            for(Platform p : sets){
                platformNames[i++] = p.getBeanName();
            }
            messageEntity.setPlatforms();
            messageEntity.setPlatformNames(platformNames);
        }

        logger.info("push cache:" + JSON.toJSONString(messageEntity));
        try{
            RedisUtil.rpushObject(queueName, messageEntity);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    /**
     * 消费缓存内的数据
     * */
    public void consume(){
        MessageEntity entity;
        do {
            entity = null;

            int minute = new Date().getMinutes();
            String key = minuteName + minute;
            Integer times = RedisUtil.getObject(key, Integer.class);
            if(times != null && times > MAX_TIME){
                break;
            }

            try {
                entity = RedisUtil.lpop(this.queueName, MessageEntity.class);
                if (entity == null) {
                    break;
                }
                RedisUtil.incr(key, 1, 60);

                logger.info("bean:" + JSON.toJSONString(entity));

                BaseResult result = this.send(entity);
                logger.info("result:" + JSON.toJSONString(result));
            }catch (Exception e){
                e.printStackTrace();
            }
            logger.error("minute:" + times);
        }while (entity != null);
    }

    public BaseResult send(MessageEntity messageEntity){

        String error = validate(messageEntity);

        if(!StringUtil.isEmpty(error)){
            return FormatResponseUtil.formatResponse(-100, error);
        }
        Set<Platform> platforms = messageEntity.getPlatforms();

        boolean flag = false;

        if(messageEntity.isSendAllPlatforms()){
            for (String p : allForPlatForms){
                Platform platform = getBean(p, Platform.class);
                Map<String, Object> map = WowCollections.map();


                boolean isSuccessed = true;
                if(platform != null){
                    BaseResult baseResult = platform.send(this, messageEntity);
                    map.put(p, baseResult);
                    if(!FormatResponseUtil.isSuccess(baseResult)){
                        isSuccessed = false;

                    }
                }
                return FormatResponseUtil.formatResponse(isSuccessed ? 0 : -100, "发送失败");
            }
        }

        if(messageEntity.getPlatformNames() != null){
            Set<String> set = messageEntity.getPlatformNames();
            if(set != null){
                Platform[] platform1 = new Platform[set.size()];
                int i = 0;
                for(String s : set){
                    Platform p = SpringContextUtil.getBean(s, Platform.class);
                    platform1[i++] = p;
                }
                messageEntity.setPlatforms(platform1);
            }
        }

        Set<Platform> platformSet = messageEntity.getPlatforms();

        if(platformSet != null && platformSet.size() > 0){

            Map<String, Object> map = WowCollections.map();
            boolean isSuccessed = true;

            for (Platform p : platformSet){
                BaseResult baseResult = p.send(this, messageEntity);
                map.put(p.getBeanName(), baseResult);
                if(!FormatResponseUtil.isSuccess(baseResult)){
                    isSuccessed = false;
                }

                return FormatResponseUtil.formatResponse(isSuccessed ? 0 : -100,
                        isSuccessed ? "发送成功" : "发送失败");
            }

        }

        return FormatResponseUtil.formatResponse(-100, "找不到相关平台");

    }

}
