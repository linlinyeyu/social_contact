package com.zheng.message.sdk.interfaces;

import com.zheng.common.base.BaseResult;
import com.zheng.message.sdk.MessageClient;
import com.zheng.message.sdk.MessageEntity;

/**
 * Created by acer on 2017/12/25.
 */
public interface Platform {


    /**
     * 根据传递的消息信息
     * 实现发送的方法
     * */
    BaseResult send(MessageClient client, MessageEntity messageEntity);

    /**
     * 由于可以使用队列推送
     * 那就需要使用beanName
     * */
    String getBeanName();
}
