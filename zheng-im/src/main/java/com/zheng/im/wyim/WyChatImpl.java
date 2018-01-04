package com.zheng.im.wyim;

import com.zheng.im.base.*;

import java.util.Map;

/**
 * Created by acer on 2017/12/18.
 */
public class WyChatImpl implements ChatBase {


    private HttpBase httpBase;

    public WyChatImpl(Map<String, String> config){
        httpBase = new WyHttpImpl(config);
    }

    private GroupBase groupBase;

    private UserBase userBase;

    private MessageBase messageBase;

    public GroupBase getGroupBase() {
        if(groupBase == null){
            synchronized (this){
                if(groupBase == null){
                    groupBase = new WyGroupImpl(httpBase);
                }
            }
        }
        return groupBase;
    }


    public UserBase getUserBase() {
        if(userBase == null){
            synchronized (this){
                if(userBase == null){
                    userBase = new WyUserimImpl(httpBase);
                }
            }
        }
        return userBase;
    }


    public MessageBase getMessageBase() {
        if(messageBase == null){
            synchronized (this){
                if(messageBase == null){
                    messageBase = new WyMessageImpl(httpBase);
                }
            }
        }
        return messageBase;
    }

}
