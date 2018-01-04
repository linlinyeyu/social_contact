package com.zheng.test;

import com.zheng.message.platform.JPushPlatform;
import com.zheng.message.sdk.MessageClient;
import com.zheng.message.sdk.MessageEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by acer on 2017/12/25.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:applicationContext.xml" ,
        "classpath:applicationContext-jdbc.xml",
        "classpath:applicationContext-dubbo-consume.xml",
        "classpath:applicationContext-chat.xml",
        "classpath:applicationContext-message.xml",
         })
public class ActiveMqTest {

    @Resource
    private MessageClient client;

    @Autowired
    private JPushPlatform jPushPlatform;

    @Test
    public void test(){
        client.consume();
    }

    @Test
    public void pushCache(){
        MessageEntity entity = new MessageEntity();
        entity.setPlatforms( jPushPlatform)
                .setTitle("测试")
                .setRegisterTokens("160a3797c8011d8f074")
                .setContent("测试出")
                .setUserIds("13532937");
        client.pushCache(entity);
    }
}
