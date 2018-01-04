package com.zheng.message.sdk.interfaces;

import com.zheng.message.sdk.MessageEntity;

import java.util.List;
import java.util.Map;
/**
 * Created by acer on 2017/12/25.
 */
public interface PushMessageService {

    Map<String, String> getPushParams(MessageEntity messageEntity);

    /**
     * 方法分安卓和iOS和全部，
     * 返回的map的key值为
     * all
     * android
     * ios
     * */
    Map<String, List<String >> getRegisterTokens(MessageEntity messageEntity);


    boolean isProduce();

}
