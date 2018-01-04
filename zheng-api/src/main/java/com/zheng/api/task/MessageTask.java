package com.zheng.api.task;

import com.zheng.message.sdk.MessageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

/**
 * Created by acer on 2017/12/25.
 */
@Component
@Lazy(false)
public class MessageTask {

    private Logger logger = LoggerFactory.getLogger(MessageTask.class);

    @Autowired
    private MessageClient client;

    @Scheduled(cron = "0 * * * * ?")
    public void test(){
        try{
            client.consume();
        }catch (Exception e){
            logger.error("error", e);
        }

    }

}
