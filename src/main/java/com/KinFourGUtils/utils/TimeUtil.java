package com.KinFourGUtils.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：KingGGG on 16/1/15 11:20
 * 描述：
 */
public class TimeUtil {
    public static final SimpleDateFormat M_d_H_m_s_DateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
    public static final SimpleDateFormat y_M_d_H_m_s_DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat y_M_d_H_m_DateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat y_M_d_H_m_DateFormat2 = new SimpleDateFormat("yy-MM-dd HH:mm");
    public static final SimpleDateFormat y_M_d_H_m_DateFormat3 = new SimpleDateFormat("yy/MM/dd HH:mm");
    public static final SimpleDateFormat y_M_d_DateFormat1 = new SimpleDateFormat("yyyy年MM月dd号");
    public static final SimpleDateFormat y_M_d_DateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat y_M_d_DateFormat3 = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat y_M_d_DateFormat4 = new SimpleDateFormat("yy/MM/dd");
    public static final SimpleDateFormat M_d_DateFormat = new SimpleDateFormat("MM月dd号");
    public static final SimpleDateFormat H_m_DateFormat = new SimpleDateFormat("HH:mm");


    public static long getSysTimeStamp() {
        Long tsLong = System.currentTimeMillis();
        return tsLong;
    }

    public static long getTimeStamp(String time, SimpleDateFormat sdf) throws ParseException {
        Date date = sdf.parse(time);
        long timeStamp = date.getTime();
        return timeStamp;
    }

    public static long getBetweenDays(long beginTime, long endTime) {
        return (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24));
    }

    public static long getBetweenHours(long beginTime, long endTime) {
        return (long) ((endTime - beginTime) / (1000 * 60 * 60));
    }

    // 获取时间格式
    public static String getTimeStr(long time, SimpleDateFormat sdf) {
        return sdf.format(new Date(time));
    }

    /**
     * 获取今天的时间戳
     * @return
     * @throws ParseException
     */
    public static long getTodayTimeStamp() throws ParseException {
        Long tsLong = System.currentTimeMillis();
        return getTimeStamp(getTimeStr(tsLong, y_M_d_DateFormat1), y_M_d_DateFormat1);
    }

}
