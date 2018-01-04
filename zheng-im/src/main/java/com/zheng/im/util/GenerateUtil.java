package com.zheng.im.util;

import java.util.Random;

/**
 * Created by linlinyeyu on 2017/12/12.
 */
public class GenerateUtil {
    public static String getNonce(){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 32; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }
}
