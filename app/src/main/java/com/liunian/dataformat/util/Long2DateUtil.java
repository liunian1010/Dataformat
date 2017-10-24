package com.liunian.dataformat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Long2DateUtil {

    private static final SimpleDateFormat sdf_Parse = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm", Locale.CHINA);
    private static final SimpleDateFormat sdfHHmm = new SimpleDateFormat("HH:mm", Locale.CHINA);

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");


    /**
     * 获取日期字符串，为今天，昨天，周几，日期
     */
    public static String getDateStr4Week(long shike) {
        Date date = new Date(shike);
        String s = format.format(date);
        if (isToday(s)) {
            return "今天 " + getHHmm(shike);
        } else if (isYesterDay(s)) {
            return "昨天 " + getHHmm(shike);
        } else if (isSameWeek(s)) {
            return getWeek(s) + " " + getHHmm(shike);
        } else {
            return s;
        }
    }

    /**
     * 时间转换，智能转化昨天，今天，本周内的显示周几，其他显示日期
     * 星期一为一周的开始
     */
    public static String getDateStr4WeekAndTime(long shike) {
        Date date = new Date(shike);
        String s = format.format(date);
        if (isToday(s)) {
            return "今天 " + getHHmm(shike);
        } else if (isYesterDay(s)) {
            return "昨天 " + getHHmm(shike);
        } else if (isSameWeek(s)) {
            return getWeek(s) + " " + getHHmm(shike);
        } else {
            return s + " " + getHHmm(shike);
        }
    }


    /**
     * yyyy/MM/dd HH:mm
     *
     * @param date
     * @return
     */
    public static final String getData(long date) {
        String sDate = sdf_Parse.format(new Date(date));
        return sDate;
    }


    /**
     * HH:mm
     *
     * @param time
     * @return
     */
    public static final String getHHmm(long time) {
        return sdfHHmm.format(new Date(time));
    }


    /**
     * 判断是否同一天
     *
     * @param date
     * @return
     */
    private static boolean isToday(String date) {
        Date dateTemp = new Date();
        String s = format.format(dateTemp);
        return date.equals(s);
    }

    /**
     * 判断是否同一周,如果同一周则返回周几,否则返回null
     *
     * @param date
     * @return
     */
    private static String getWeek(String date) {
        if (!isSameWeek(date)) {
            //不是同一周
            return "";
        }

        String week = null;
        switch (dayForWeek(date)) {
            case 1:
                week = "周一";
                break;
            case 2:
                week = "周二";
                break;
            case 3:
                week = "周三";
                break;
            case 4:
                week = "周四";
                break;
            case 5:
                week = "周五";
                break;
            case 6:
                week = "周六";
                break;
            case 7:
                week = "周日";
                break;

        }
        return week;
    }


    /**
     * 判断是否同一周
     *
     * @param date1
     * @return
     */
    private static boolean isSameWeek(String date1) {
        Date d1 = null;
        Date d2 = new Date();
        try {
            d1 = format.parse(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (d1 == null || d2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(d1);
        cal2.setTime(d2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        //subYear==0,说明是同一年
        if (subYear == 0) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        //例子:cal1是"2005-1-1"，cal2是"2004-12-25"
        //java对"2004-12-25"处理成第52周
        // "2004-12-26"它处理成了第1周，和"2005-1-1"相同了
        //大家可以查一下自己的日历
        //处理的比较好
        //说明:java的一月用"0"标识，那么12月用"11"
        else if (subYear == 1) {
            int s = cal2.get(Calendar.MONTH);
            if (s == 11) {
                if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                    return true;
            }
        }
        //例子:cal1是"2004-12-31"，cal2是"2005-1-1"
        else if (subYear == -1) {
            int s = cal1.get(Calendar.MONTH);
            if (s == 11) {
                if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                    return true;
            }


        }
        return false;
    }

    /**
     * 根据日期获得周几
     *
     * @param pTime
     * @return
     */
    private static int dayForWeek(String pTime) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 判断是否是昨天
     *
     * @param datE
     * @return
     */
    private static boolean isYesterDay(String datE) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(datE));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);
        return format.format(c.getTime()).equals(format.format(new Date()));
    }

}
