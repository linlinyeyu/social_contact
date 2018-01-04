package com.zheng.message.platform;

import com.alibaba.fastjson.JSONObject;
import com.zheng.common.base.BaseResult;
import com.zheng.common.util.FormatResponseUtil;
import com.zheng.common.util.StringUtil;
import com.zheng.common.util.WowCollections;
import com.zheng.message.dao.model.Message;
import com.zheng.message.dao.model.UcenterUserMessage;
import com.zheng.message.sdk.MessageClient;
import com.zheng.message.sdk.MessageEntity;
import com.zheng.message.sdk.interfaces.Platform;
import com.zheng.message.service.MessageService;
import com.zheng.message.service.UcenterUserMessageService;
import com.zheng.user.service.UcenterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by acer on 2017/12/25.
 */
@Component("databaseMessagePlatform")
public class DatabaseMessagePlatform implements Platform {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UcenterUserMessageService userMessageService;

    @Override
    public BaseResult send(MessageClient client, MessageEntity messageEntity) {

        Set<Platform> platforms = messageEntity.getPlatforms();
        Set<String> platFormNames = messageEntity.getPlatformNames();
        if(platFormNames == null){
            platFormNames = new HashSet<>();
        }
        if(platforms != null){
            for (Platform p : platforms){
                platFormNames.add(p.getBeanName());
            }
        }


        Message message = new Message();
        message.setMessageType(messageEntity.getMessageType().shortValue());
        message.setTargetType(messageEntity.getTargetType().shortValue());
        message.setTitle(messageEntity.getTitle());
        message.setContent(messageEntity.getContent());
        message.setActionId(messageEntity.getActionId().shortValue());
        message.setCreateTime(System.currentTimeMillis());
        message.setSendTime(System.currentTimeMillis());
        message.setPlatform(WowCollections.join(platFormNames, ","));
        message.setIsShow(true);
        message.setIsSend(true);
        Map<String, String> params = messageEntity.getExtra();
        if (params != null) {
            message.setActionParams(JSONObject.toJSONString(params));
        }
        int rows = messageService.insertSelective(message);
        if (rows == 0) {
            return FormatResponseUtil.formatResponse(-100, "插入失败");
        }
        int messageId = message.getMessageId();
        if (messageEntity.getTargetType() == MessageEntity.TARGET_TYPE_USER && messageEntity.getUserIds() != null) {
            List<UcenterUserMessage> userMessageList = new ArrayList<>();
            Set<String> userIds = messageEntity.getUserIds();
            for (String userId : userIds) {
                if (!StringUtil.isEmpty(userId)) {
                    UcenterUserMessage um = new UcenterUserMessage();
                    um.setUuid(userId);
                    um.setMessageId(messageId);
                    um.setCreateTime(message.getCreateTime());
                    um.setIsRead(false);
                    um.setIsDeleted(false);
                    userMessageList.add(um);
                }
            }
            boolean flag = userMessageService.inserUserMessageList(userMessageList);
            if (!flag) {
                return FormatResponseUtil.formatResponse(-100, "插入失败");
            }
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public String getBeanName() {
        return "databaseMessagePlatform";
    }
}



