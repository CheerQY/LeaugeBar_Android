package com.sicnu.cheer.generalmodule.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 日期工具类
 *
 * @author cheer
 */
public class DateUtil {


    public static String formateDate() {
        String nowtime = null;

        SimpleDateFormat ch = new SimpleDateFormat("yyyyMMddHHmmssSSS",
                Locale.CHINESE);

        nowtime = ch.format(new Date());
        return nowtime;
    }

    public static String formateDate(String partten) {
        String nowtime = null;
        SimpleDateFormat ch = null;
        try {
            ch = new SimpleDateFormat(partten,
                    Locale.CHINESE);

            nowtime = ch.format(new Date());
            return nowtime;
        } catch (Exception e) {
            e.printStackTrace();
            ch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.CHINESE);

            nowtime = ch.format(new Date());
            return nowtime;
        }

    }

    public static String formateDate(Date date, String partten) {
        String nowtime = null;
        SimpleDateFormat ch = null;
        try {
            ch = new SimpleDateFormat(partten,
                    Locale.CHINESE);

            nowtime = ch.format(date);
            return nowtime;
        } catch (Exception e) {
            e.printStackTrace();
            ch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.CHINESE);

            nowtime = ch.format(new Date());
            return nowtime;
        }

    }

    public static String origDateStr(Date date) {
        String nowtime = null;

        if (date != null) {
            nowtime = DateUtil.getStandardDate(date);
            nowtime = DateUtil.getStandardDateShort(nowtime);
        }
        return nowtime;
    }

    public static Date origDate(String date) {
        Date nowtime = null;

        if (!date.equals("")) {
            nowtime = DateUtil.converDate(date + " 00:00:00");
        }
        return nowtime;
    }

    public static boolean IsLieDate(String lieDate) {
        boolean flag = false;
        try {
            Date c = new Date();
            if (lieDate != null && !lieDate.equals("")) {
                Date lie = getDateBystr(lieDate);
                if (lie.getTime() < c.getTime()) {
                    flag = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    public static Map getWeeks(String cur_day) {

        Map map = new java.util.Hashtable();

        try {
            DateFormat df = DateFormat.getDateInstance();
            Calendar c = Calendar.getInstance();
            c.setTime(df.parse(cur_day));
            int weekDay = c.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, Calendar.MONDAY - weekDay);
            Date start = c.getTime();
            Date end = null;
            map.put("back", dateAdd(start, -1));
            map.put("week0", DateUtil.getStandardDate("yyyy-M-d", start) + "|星期" + DateUtil.getDayOfWeek(start));
            for (int i = 0; i < 6; i++) {
                c.add(Calendar.DATE, 1);
                end = c.getTime();
                map.put("week" + (i + 1), DateUtil.getStandardDate("yyyy-M-d", end) + "|星期" + DateUtil.getDayOfWeek(end));
            }

            map.put("proview", dateAdd(end, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String getTodayWeekStr(){
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        int w=calendar.get(Calendar.DAY_OF_WEEK)-1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    public static Map getWeeksMap(String cur_day) {

        Map map = new Hashtable();

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(df.parse(cur_day));
            int weekDay = c.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, Calendar.MONDAY - weekDay);
            Date start = c.getTime();
            Date end = null;
            map.put("back", dateAdd(start, -1));
            map.put("week0", getStandardDate("yyyy-MM-dd", start) + "|星期" + getDayOfWeek(start));
            for (int i = 0; i < 6; i++) {
                c.add(Calendar.DATE, 1);
                end = c.getTime();
                map.put("week" + (i + 1), getStandardDate("yyyy-MM-dd", end) + "|星期" + getDayOfWeek(end));
            }

            map.put("proview", dateAdd(end, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public static Map getDays(String cur_day, int m) {

        Map map = new Hashtable();

        try {
            DateFormat df = DateFormat.getDateInstance();
            Calendar c = Calendar.getInstance();
            c.setTime(df.parse(cur_day));
            c.add(Calendar.DATE, m);

            int yyyy = c.get(Calendar.YEAR);
            int mm = c.get(Calendar.MONTH);
            int dd = c.get(Calendar.DATE);

            Date date = c.getTime();

            map.put("date", DateUtil.getStandardDate("yyyy-M-d", date));
            map.put("dateStr", yyyy + "年" + (mm + 1) + "月" + dd + "日 星期" + DateUtil.getDayOfWeek(date));

            map.put("yyyy", yyyy);
            map.put("mm", mm);
            map.put("dd", dd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static String getNextHour(String cur_day, int m) {

        String str = "";

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Calendar c = Calendar.getInstance();
            c.setTime(df.parse(cur_day));
            c.add(Calendar.HOUR, m);    //传入时间增加1小时
            Date date = c.getTime();

            str = df.format(date);

            str = str.substring(11, str.length());

        } catch (Exception e) {
            e.printStackTrace();
        }


        return str;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static long getNowTime() {
        return new Date().getTime();
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTime(String pattern) {
        String nowtime = "";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date dt = new Date();
        try {
            nowtime = df.format(dt);
        } catch (Exception e) {
            SimpleDateFormat dfe = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            nowtime = dfe.format(dt);
            e.printStackTrace();
        }
        return nowtime;
    }

    public static Date getDateBystr(String strDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = new Date();
        try {
            dt = df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static Date getDateBystr2(String strDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = new Date();
        try {
            dt = df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static String dateAdd(Date d, int day) {
        String dateStr = "";
        try {
            java.text.Format forma = new SimpleDateFormat("yyyy-MM-dd");
            Calendar Cal = Calendar.getInstance();
            Cal.setTime(d);
            Cal.add(Calendar.DAY_OF_MONTH, day);

            dateStr = forma.format(Cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static Date yearAdd(Date d, int year) {

        try {
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            c.add(Calendar.YEAR, year);    //传入时间增加N年
            d = c.getTime();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return d;
    }

    public static String hourAdd(String cur_day, int hour) {

        String str = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Calendar c = Calendar.getInstance();
            c.setTime(df.parse(cur_day));
            c.add(Calendar.HOUR, hour);    //传入时间增加1小时
            Date date = c.getTime();
            str = df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String formateDate2() {
        String nowtime = null;

        SimpleDateFormat ch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINESE);

        nowtime = ch.format(new Date());
        return nowtime;
    }

    public static String formateDate3(String partten) {

        String nowtime = null;

        SimpleDateFormat ch = new SimpleDateFormat(partten,
                Locale.CHINESE);

        nowtime = ch.format(new Date());
        return nowtime;
    }

    //比较时间大小
    public static int timeCompare(String t1, String t2) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(formatter.parse(t1));
            c2.setTime(formatter.parse(t2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = c1.compareTo(c2);
        return result;
    }


    public static String getStandardDate(String completeDate) {
        String nowtime = "";
        try {
            SimpleDateFormat ch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.CHINESE);

            nowtime = ch.format(ch.parse(completeDate));

        } catch (Exception ex) {
            System.out.println("getStandardDate:" + ex.getMessage());
        }

        return nowtime;
    }


    public static String getStandardDateShort(String completeDate) {
        String nowtime = "";
        try {

            SimpleDateFormat ch = new SimpleDateFormat("yyyy-MM-dd",
                    Locale.CHINESE);

            nowtime = ch.format(ch.parse(completeDate));
        } catch (Exception ex) {
            System.out.println("getStandardDate:" + ex.getMessage());
        }

        return nowtime;
    }

    public static String getStandardDate(String formatDate, String completeDate) {
        String nowtime = "";
        try {
            SimpleDateFormat ch = new SimpleDateFormat(formatDate,
                    Locale.CHINESE);

            nowtime = ch.format(ch.parse(completeDate));
        } catch (Exception ex) {
            System.out.println("getStandardDate:" + ex.getMessage());
        }

        return nowtime;
    }


    public static String getStandardDate(Date date) {
        String dateStr = "";
        if (date != null) {
            SimpleDateFormat ch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.CHINESE);
            dateStr = ch.format(date);
        }

        return dateStr;
    }

    public static String getStandardDate(String formatDate, Date date) {
        SimpleDateFormat ch = new SimpleDateFormat(formatDate, Locale.CHINESE);
        String dateStr = ch.format(date);
        return dateStr;
    }

    public static int getStandardDate(Date date, int type) {
        int year = 0;
        int month = 0;
        int day = 0;
        try {
            Calendar Cal = Calendar.getInstance();
            Cal.setTime(date);
            year = Cal.get(Calendar.YEAR);
            month = Cal.get(Calendar.MONTH) + 1;
            day = Cal.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (type == 1) {
            return year;
        } else if (type == 2) {
            return month;
        } else if (type == 3) {
            return day;
        }

        return 0;

    }

    public static String getDateStr() {
        String nowtime = null;

        SimpleDateFormat ch = new SimpleDateFormat("yyyyMMddHHmmssSSS",
                Locale.CHINESE);

        nowtime = ch.format(new Date());
        return nowtime;
    }

    public static String getToday() {
        SimpleDateFormat ch = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        Date date = new Date();
        return ch.format(date);
    }


    public static String getYesterday() {
        SimpleDateFormat ch = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(GregorianCalendar.HOUR, -24);
        Date date = cal.getTime();
        return ch.format(date);
    }

    public static String getYesterday(String partten) {
        SimpleDateFormat ch = null;
        try {
            ch = new SimpleDateFormat(partten, Locale.CHINESE);
        } catch (Exception e) {
            e.printStackTrace();
            ch = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        }


        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(GregorianCalendar.HOUR, -24);
        Date date = cal.getTime();
        return ch.format(date);
    }

    public static String getDayOfWeekAgo() {
        SimpleDateFormat ch = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(GregorianCalendar.HOUR, -24*7);
        Date date = cal.getTime();
        return ch.format(date);
    }

    public static String getDayOfWeekAfter() {
        SimpleDateFormat ch = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(GregorianCalendar.HOUR, 24*7);
        Date date = cal.getTime();
        return ch.format(date);
    }

    public static String getDayOfWeekAgo(String partten) {
        SimpleDateFormat ch = null;
        try {
            ch = new SimpleDateFormat(partten, Locale.CHINESE);
        } catch (Exception e) {
            e.printStackTrace();
            ch = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        }


        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(GregorianCalendar.HOUR, -24*7);
        Date date = cal.getTime();
        return ch.format(date);
    }
    public static String getDayOfWeekAgo(String currentDay,String partten) {
        SimpleDateFormat ch = null;
        try {
            ch = new SimpleDateFormat(partten, Locale.CHINESE);
        } catch (Exception e) {
            e.printStackTrace();
            ch = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        }


        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(DateUtil.converDate(currentDay,"yyyy-MM-dd"));
        cal.add(GregorianCalendar.HOUR, -24*7);
        Date date = cal.getTime();
        return ch.format(date);
    }

    /**
     * 方法描述:获取明天日期
     *
     * @return
     * @创建人: cao_xiaohua
     * @创建日期: May 12, 2012
     */
    public static String getTomorrow() {
        SimpleDateFormat ch = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(GregorianCalendar.HOUR, 24);
        Date date = cal.getTime();
        return ch.format(date);
    }

    public static String getLastMonthEndDate() {

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(GregorianCalendar.MONTH, -1);
        Date date = cal.getTime();

        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int last = cal.getActualMaximum(Calendar.DATE);

        String time = now.get(Calendar.YEAR) + "-"
                + (now.get(Calendar.MONTH) + 1) + "-" + last;
        return time;
    }

    public static String getMonthEndDate() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(GregorianCalendar.MONTH, 0);
        Date date = cal.getTime();

        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int last = cal.getActualMaximum(Calendar.DATE);

        String time = now.get(Calendar.YEAR) + "-"
                + (now.get(Calendar.MONTH) + 1) + "-" + last;
        return time;
    }


    public static String getMonthStartDate1() {
        Calendar now = Calendar.getInstance();
        String month = now.get(Calendar.MONTH) + 1 + "";
        if (now.get(Calendar.MONTH) + 1 < 10) {
            month = "0" + month;
        }
        String time = now.get(Calendar.YEAR) + "-"
                + month + "-01";
        return time;
    }

    public static String getMonthStartDate() {
        Calendar now = Calendar.getInstance();
        String time = now.get(Calendar.YEAR) + "-"
                + (now.get(Calendar.MONTH) + 1) + "-1";
        return time;
    }

    public static String getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int week_num = c.get(Calendar.WEEK_OF_YEAR);

        String dayOfWeekStr = "";

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                dayOfWeekStr = "日";
                break;
            case 2:
                dayOfWeekStr = "一";
                break;
            case 3:
                dayOfWeekStr = "二";
                break;
            case 4:
                dayOfWeekStr = "三";
                break;
            case 5:
                dayOfWeekStr = "四";
                break;
            case 6:
                dayOfWeekStr = "五";
                break;
            case 7:
                dayOfWeekStr = "六";
                break;
        }
        return dayOfWeekStr;
    }

    public static int isOverTime(String complainDate, String timeLimit,
                                 String completeDate) {
        int i = -1;
        try {
            SimpleDateFormat ch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.CHINESE);

            GregorianCalendar cal = new GregorianCalendar();

            Date date_TimeLimit = ch.parse(complainDate);
            Date date_CompleteDate = null;
            Date date_now = new Date();

            cal.setTime(date_TimeLimit);

            if (timeLimit.equals("12Сʱ"))
                cal.add(GregorianCalendar.HOUR, 12);
            if (timeLimit.equals("24Сʱ"))
                cal.add(GregorianCalendar.HOUR, 24);
            if (timeLimit.equals("48Сʱ"))
                cal.add(GregorianCalendar.HOUR, 48);
            if (timeLimit.equals("72Сʱ"))
                cal.add(GregorianCalendar.HOUR, 72);
            if (timeLimit.equals("96Сʱ"))
                cal.add(GregorianCalendar.HOUR, 96);
            if (timeLimit.equals("120Сʱ"))
                cal.add(GregorianCalendar.HOUR, 120);

            date_TimeLimit = cal.getTime();

            if (completeDate != null && !completeDate.equals("")) {

                if (completeDate.indexOf("1900") >= 0) {
                    if (date_now.compareTo(date_TimeLimit) >= 0)
                        return 1;
                } else {
                    date_CompleteDate = ch.parse(completeDate);
                    if (date_CompleteDate.compareTo(date_TimeLimit) >= 0)
                        i = 1;
                }
            } else {
                if (date_now.compareTo(date_TimeLimit) >= 0)
                    return 1;
            }

        } catch (Exception ex) {
            System.out.println(new Date() + " isOverTime:" + ex.getMessage());
        }
        return i;
    }

    public static String getOverTime(String complainDate, String timeLimit) {
        String overTime = "";
        try {
            SimpleDateFormat ch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.CHINESE);

            GregorianCalendar cal = new GregorianCalendar();

            Date date_TimeLimit = ch.parse(complainDate);
            cal.setTime(date_TimeLimit);

            if (timeLimit.equals("12Сʱ"))
                cal.add(GregorianCalendar.HOUR, 12);
            if (timeLimit.equals("24Сʱ"))
                cal.add(GregorianCalendar.HOUR, 24);
            if (timeLimit.equals("48Сʱ"))
                cal.add(GregorianCalendar.HOUR, 48);
            if (timeLimit.equals("72Сʱ"))
                cal.add(GregorianCalendar.HOUR, 72);
            if (timeLimit.equals("96Сʱ"))
                cal.add(GregorianCalendar.HOUR, 96);
            if (timeLimit.equals("120Сʱ"))
                cal.add(GregorianCalendar.HOUR, 120);

            date_TimeLimit = cal.getTime();
            overTime = ch.format(date_TimeLimit);

        } catch (Exception ex) {
            System.out.println(new Date() + " getOverTime:" + ex.getMessage());
        }
        return overTime;
    }

    public static boolean isInteger(String str) {
        if (str == null)
            return false;
        Pattern pattern = Pattern.compile("[0-9]+");
        return pattern.matcher(str).matches();
    }

    public static String dateAddReturnStr(Date d, int hour) {
        String dateStr = "";
        try {
            java.text.Format forma = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            Calendar Cal = Calendar.getInstance();
            Cal.setTime(d);
            Cal.add(Calendar.HOUR_OF_DAY, hour);
            dateStr = forma.format(Cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static String diffDateStr(Date begDate, Date endDate) {
        String result = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentTime = endDate;

            endDate.toString();

            if (currentTime.getTime() > begDate.getTime()) {
                long interval = (currentTime.getTime() - begDate.getTime()) / 1000;// 秒
                long day = interval / (24 * 3600);// 天
                long hour = interval % (24 * 3600) / 3600;// 小时
                long minute = interval % 3600 / 60;// 分钟
                long second = interval % 60;// 秒

                String dayResult = "";
                if (day > 0) {
                    dayResult = day + "天";
                }

                String hourResult = "";
                if (hour > 0) {
                    hourResult = hour + "小时";
                }

                result = dayResult + hourResult + minute + "分";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    public static Date converDate(String dateStr) {

        Date d1 = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d1 = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return d1;
    }

    public static Date converDate(String dateStr, String partten) {

        Date d1 = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(partten);
            d1 = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return d1;
    }

    public static int diffMin(Date begDate, Date endDate) {
        int min = 0;

        try {

            Date currentTime = endDate;


            if (currentTime.getTime() > begDate.getTime()) {
                long interval = (currentTime.getTime() - begDate.getTime()) / 1000;// 秒

                min = (int) interval / 60;// 分钟
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return min;
    }

    /**
     * 方法描述:日期格式字符串转换。将"Fri Jun 1 14:55:00 UTC +0700 2012"格式转换为"yyyy-MM-dd HH:mm:ss"格式
     *
     * @param strUTC
     * @return
     * @创建人: cao_xiaohua
     * @创建日期: Jun 5, 2012
     */
    public static String formatUTCTimeString(String strUTC) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat sf2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC' zzz yyyy", Locale.US);

        Date date2 = null;
        String returnStr = "";
        try {
            date2 = sf2.parse(strUTC);
            returnStr = format.format(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnStr;
    }

    /**
     * 获取日期相差天数
     *
     * @param
     * @return 日期类型时间
     * @throws ParseException
     */
    public static Long getDiffDay(String beginDate, String endDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Long checkday = 0l;
        //开始结束相差天数
        try {
            checkday = (formatter.parse(endDate).getTime() - formatter.parse(beginDate).getTime()) / (1000 * 24 * 60 * 60);
        } catch (ParseException e) {
            e.printStackTrace();
            checkday = null;
        }
        return checkday;
    }

    /**
     * 获取日期时间相差天数
     *
     * @param beginDate
     * @param endDate
     * @return
     * @author hh
     * @date Sep 1, 2012
     */
    public static Long getDiffDay(Date beginDate, Date endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strBeginDate = format.format(beginDate);

        String strEndDate = format.format(endDate);
        return getDiffDay(strBeginDate, strEndDate);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar ca = Calendar.getInstance();
        return ca.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar ca = Calendar.getInstance();
        return ca.get(Calendar.YEAR);
    }

    /**
     * 获取当前天
     *
     * @return
     */
    public static int getCurrentDay() {
        Calendar ca = Calendar.getInstance();
        return ca.get(Calendar.DAY_OF_MONTH);
    }

    public static String clanderTodatetime(Calendar localCalendar, String format) {
        Date date = localCalendar.getTime();

        return getStandardDate(format, date);

    }

    /**
     * 根据格式字符串转换时间戳为指定格式字符串
     *
     * @param format    格式字符串
     * @param timeStamp 时间戳
     * @return
     */
    public static String getTimeStringFromTimeStampWithFormat(String format, long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String time = dateFormat.format(timeStamp);
        return time;
    }

    /**
     * 格式化中文日期
     * 类型：如果是今天，则 上午（下午） hh:mm
     * 如果是昨天，则  昨天
     * 如果是更早，则  yyyy-MM-dd
     *
     * @param datetime
     * @return
     */
    public static String getChineseDate(String datetime) {
        String result = "";
        String times = DateUtil.getStandardDate("yyyy-MM-dd HH:mm", datetime);
        try {
            String[] resultArray = times.split(" ");
            if (resultArray[0].equals(getToday())) {
                String[] hours = resultArray[1].split(":");
                if (Integer.valueOf(hours[0]) >= 12) {
                    int hour = (Integer.valueOf(hours[0]) - 12);
                    if (hour < 10) {
                        result = "下午 0" + hour + ":" + hours[1];
                    } else {
                        result = "下午 " + hour + ":" + hours[1];
                    }
                } else {
                    result = "上午 " + resultArray[1];
                }

            } else if (resultArray[0].equals(getYesterday())) {
                result = "昨天";
            } else {
                result = resultArray[0].substring(5,resultArray[0].length())+" "+resultArray[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 格式化中文日期
     * 类型：如果是今天，则 上午（下午） hh:mm
     * 如果是昨天，则  昨天
     * 如果是更早，则  yyyy-MM-dd
     *
     * @param datetime
     * @param partten 时间格式
     * @return
     */
    public static String getChineseDate(String datetime,String partten) {
        String result = "";
        if(StringUtils.isEmpty(partten)){
            partten = "yyyy-MM-dd HH:mm:ss";
        }
        String times = DateUtil.getStandardDate("yyyy-MM-dd HH:mm", datetime);
        try {
            String[] resultArray = times.split(" ");
            if (resultArray[0].equals(getToday())) {
                String[] hours = resultArray[1].split(":");
                if (Integer.valueOf(hours[0]) >= 12) {
                    int hour = (Integer.valueOf(hours[0]) - 12);
                    if (hour < 10) {
                        result = "下午 0" + hour + ":" + hours[1];
                    } else {
                        result = "下午 " + hour + ":" + hours[1];
                    }
                } else {
                    result = "上午 " + resultArray[1];
                }

            } else if (resultArray[0].equals(getYesterday())) {
                result = "昨天";
            } else {
                result = resultArray[0].substring(5,resultArray[0].length())+" "+resultArray[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
