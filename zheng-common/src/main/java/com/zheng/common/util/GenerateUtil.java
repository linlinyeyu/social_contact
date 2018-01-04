package com.zheng.common.util;

import java.util.Date;
import java.util.UUID;

/**
 * Created by linlinyeyu on 2017/12/8.
 */
public class GenerateUtil {
    public static String getUuid(){
        String id = String.valueOf(new Date().getTime()/1000L).substring(2);
        return id;
    }

    public static String getToken(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
