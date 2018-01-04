package com.zheng.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2017/4/6.
 */
public class TimeUtil {

    private TimeUtil(){

    }

    /**
     * 将符合"yyyy-MM-dd"格式的字符串日期转成时间戳
     * @param day
     * @return
     */
    public static Long changDate(String day){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(day);
            return date.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDiffDate(long firstTime, long secondTime){
        if(secondTime - firstTime < 0){
            return "0天";
        }
        return ((secondTime - firstTime ) / 86400000L) + "天";
    }


    //将时间(2017-2-27 14:37:14)
    //转换为时间戳格式
    public static String dateToStamp(String s)
            throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }


    //将时间戳转换为时间格式
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 比较两个时间戳相差的时间数
     * 例：比较2017-03-05是否比2017-05-08早一个月
     * 	   compareTimeMills(1488643200000, 1494172800000, Calendar.MONTH, -1);
     * 	   1488643200000：2017-03-05时间戳
     * 	   1494172800000：2017-05-08时间戳
     * 	   Calendar.MONTH：对月份的计算
     * 	   -1：对2017-05-08的月份进行减一个月操作
     * @param firstTime 第一个时间戳
     * @param secondTime 第二个时间戳
     * @param type 相差的类型，具体类型参考Calendar常量参数表
     * @param num 对第二个时间错操作的量级，正数为加，负数为减
     * @return
     */
    public static boolean compareTimeMills(long firstTime, long secondTime, int type, int num){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(firstTime);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(secondTime);
        calendar2.add(type, num);
        return calendar1.before(calendar2);
    }

    /**
     * 将现有的时间戳进行制定的日期类型（日，月，年，周等）计算操作，返回时间戳
     * @param timeMills 修改前的时间戳
     * @param type 调整日期的类型，具体类型参考Calendar常量参数表
     * @param num 调整的时间量级
     * @return
     */
    public static long timeMillsChange(long timeMills, int type, int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMills);
        calendar.add(type, num);
        return calendar.getTime().getTime();
    }

    /**
     * 获取系统当日零点的时间戳和次日的零点的时间戳
     * long[0]：当日零点时间戳
     * long[1]：次日零点时间戳
     * @return
     */
    public static long[] getTodayTime(){
        return getTodayTime(System.currentTimeMillis());
    }

    /**
     * 获取指定时间戳当日点的时间戳和次日的零点的时间戳
     * long[0]：当日零点时间戳
     * long[1]：次日零点时间戳
     * @param time
     * @return
     */
    public static long[] getTodayTime(long time){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(time));
        long todayMin = time -
                (c.get(Calendar.HOUR_OF_DAY) * 60 * 60 +
                        c.get(Calendar.MINUTE) * 60 +
                        c.get(Calendar.SECOND)) * 1000
                - c.get(Calendar.MILLISECOND);
        long todayMax = todayMin + 24 * 60 * 60 * 1000;
        return new long[]{todayMin, todayMax};
    }

    /**
     * 将时间戳转换成指定格式的字符串返回
     * @param time
     * @param pattern
     * @return
     */
    public static String getTimeString(Object time, String pattern){
        if(time == null){
            return "";
        }
        long t = 0;
        try{
            t = Long.parseLong(time.toString());
        }catch (Exception e){
        }

        if(t == 0){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = new Date();
        date.setTime(t);
        return sdf.format(date);
    }

    /**
     * 将时间戳转换成指定格式的字符串返回
     * @param time
     * @return
     */
    public static String getTimeString(Object time){

        return getTimeString(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentTime(){
        return getCurrentTime("yyyy-MM-dd");
    }

    public static String getCurrentTime(String pattern){
        return getTimeString(System.currentTimeMillis(),pattern);
    }

}
