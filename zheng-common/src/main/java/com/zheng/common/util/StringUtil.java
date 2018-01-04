package com.zheng.common.util;


import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 工具类
 * Created by shuzheng on 2016/12/07.
 */
public class StringUtil {

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 下划线转驼峰
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);

        str = sb.toString();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);

        return str;
    }

    /**
     * 产生随机6位整数
     * @return
     */
    public static int randomSixCode(){
        Random rand = new Random();
        int num = rand.nextInt(899999)+100000;
        return num;
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    /**
     * 驼峰转下划线,效率比上面高
     * @param str
     * @return
     */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 首字母转小写
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (StringUtils.isBlank(s)) {
            return s;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (StringUtils.isBlank(s)) {
            return s;
        }
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuffer()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * object转String
     * @param object
     * @return
     */
    public static String getString(Object object) {
        return getString(object, "");
    }

    public static String getString(Object object, String defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return object.toString();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转Integer
     * @param object
     * @return
     */
    public static int getInt(Object object) {
        return getInt(object, -1);
    }

    public static int getInt(Object object, Integer defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转Boolean
     * @param object
     * @return
     */
    public static boolean getBoolean(Object object) {
        return getBoolean(object, false);
    }

    public static boolean getBoolean(Object object, Boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static <T> String join(List<T> list, String seperator){
        if(list == null || list.size() == 0){
            return "";
        }

        StringBuffer stringBuffer = new StringBuffer();
        for(T t : list){
            if(t == null){
                continue;
            }
            stringBuffer.append(seperator);
            stringBuffer.append(t.toString());

        }
        return stringBuffer.substring(seperator.length()).toString();
    }

    public static <T> String join(T[] list, String seperator){
        if(list == null || list.length == 0){
            return "";
        }

        StringBuffer stringBuffer = new StringBuffer();
        for(T t : list){
            if(t == null){
                continue;
            }
            stringBuffer.append(seperator);
            stringBuffer.append(t.toString());

        }
        return stringBuffer.substring(seperator.length()).toString();
    }

    public static <T> String join(List<T> list){
        return join(list, ",");
    }

    public static <T> String join(T[] list){
        return join(list, ",");
    }

    public static void main(String[] args){
        String s = "中";
        System.out.println(substring(s, 0, 2)) ;

    }

    public static boolean isEmpty(String str){
        return str == null || "".equals(str.trim());
    }

    public static boolean isNullOrEmpty(String str){
        return str == null || "".equals(str.trim());
    }

    public static String substring(String str, int start, int end){
        if(isEmpty(str)){
            return str;
        }
        if(str.length() < start){
            return "";
        }
        if(end == -1 || str.length() < end){
            end = str.length() ;
        }
        if(start > end){
            return "";
        }

        return str.substring(start, end);
    }

    public static String hideName(String name){
        if(name == null || "".equals(name)){
            return "";
        }
        substring(name, 0, 5);
        if(name.length() > 4){
            name = name.substring(0, 5);
        }
        return hide(name, 2, -1);

    }

    public static String hide(String str, int start, int end){
        if(isNullOrEmpty(str)){
            return "";
        }

        if(end == -1){
            end = str.length() - 1;
        }

        if(end < start){
            return str;
        }



        if(end > str.length() - 1){
            end = str.length() - 1;
        }
        if(start >= str.length()){
            return str;
        }
        System.out.println(end);

        str = str.substring(0, start) +
                createAsterisk(end - start) + str.substring(end, str.length() - 1);

        return str;
    }

    public static String createAsterisk(int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }

    /**
     *生成随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length){
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i<length;i++){
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }



}
