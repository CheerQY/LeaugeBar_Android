package com.sicnu.cheer.generalmodule.util;

import android.content.Context;


import com.sicnu.cheer.generalmodule.R;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 *
 * @author cheer
 * @version 1.0
 * @created 2016-11-11
 */
public class StringUtils {
    private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(Context context, String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        //判断是否是同一天
        String curDate = dateFormater2.format(cal.getTime());
        String paramDate = dateFormater2.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + context.getString(R.string.minutes_ago);
            else
                ftime = hour + context.getString(R.string.hours_ago);
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + context.getString(R.string.minutes_ago);
            else
                ftime = hour + context.getString(R.string.hours_ago);
        } else if (days == 1) {
            ftime = context.getString(R.string.yesterday);
        } else if (days == 2) {
            ftime = context.getString(R.string.the_day_before_yesterday);
        } else if (days > 2 && days <= 10) {
            ftime = days + context.getString(R.string.days_ago);
        } else if (days > 10) {
            ftime = dateFormater2.format(time);
        }
        return ftime;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.format(today);
            String timeDate = dateFormater2.format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null或""，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        return isEmpty(input, false);
    }

    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null,"null"或""，返回true
     *
     * @param input
     * @return
     */
    public static boolean isEmptyWithNullString(String input) {
        return isEmpty(input, true);
    }

    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @param nullStringIsEmpty "null"是否判断为空
     * @return
     */
    public static boolean isEmpty(String input, boolean nullStringIsEmpty) {
        if (input == null || "".equals(input))
            return true;
        if (nullStringIsEmpty && "null".equals(input.toLowerCase()))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 验证字符串长度
     *
     * @param value
     * @return
     */
    public static boolean isLengthValidate(String value) {
        return value.length() <= 20;
    }

    /**
     * 验证字符串长度
     *
     * @param value
     * @param length
     * @return
     */
    public static boolean isLengthValidate(String value, int length) {
        return value.length() <= length;
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null) return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

//    /**
//     * xml转json string
//     *
//     * @param xml
//     * @return
//     */
//    public static String xml2JSON(String xml) {
//        return new XMLSerializer().read(xml).toString();
//    }

//    /**
//     * json string转xml
//     *
//     * @param json
//     * @return
//     */
//    public static String json2XML(String json) {
//        JSONObject jobj = JSONObject.fromObject(json);
//        String xml = new XMLSerializer().write(jobj);
//        return xml;
//    }

    /**
     * 随机生成6位数字验证码
     *
     * @return
     */
    public static String generate6ValidNum() {
        int intCount = 0;
        intCount = (new Random()).nextInt(999999);
        if (intCount < 100000) {
            intCount += 100000;
        }
        return intCount + "";
    }

    /**
     * 转换为金钱格式
     *
     * @param money
     * @return
     */
    public static String getMoneyStyle(String money) {
        try {
            double f = Double.valueOf(money) / 100.00d;
            BigDecimal b = new BigDecimal(f);
            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return String.format("%.2f", f1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.00";
    }


    /**
     * 返回对象字符串,如果参数为null则返回空字符串""
     *
     * @param object
     * @return
     */
    public static String toString(Object object) {
        String value = "";
        if (object != null) {
            value = object.toString();
        }
        return value;
    }

    /**
     * 字符全角化
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    // 替换、过滤特殊字符
    public static String StringFilter(String str) {
        try {
            str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!");//替换中文标号
            String regEx = "[『』]"; // 清除掉特殊字符
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.replaceAll("").trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}