package com.sicnu.cheer.generalmodule.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sctf.mobile.generalmodule.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by cheer on 2016/12/19.
 */

public class DatePickDialogUtil implements DatePicker.OnDateChangedListener {
    private DatePicker datePicker;
    private AlertDialog ad;
    private String date;
    private String initDate;
    private String minDate;
    private String maxDate;
    private Activity activity;
    //是否限制时间
    private boolean isLimitDate = false;
    //控件来源 1 -- 开始时间  2--结束时间
    private int comefrom;

    //弹出框标题
    private String title;

    /**
     * 日期时间弹出选择框构造函数
     *
     * @param activity     ：调用的父activity
     * @param initDate 初始日期时间值，作为弹出窗口的标题和日期时间初始值
     */
    public DatePickDialogUtil(Activity activity, String initDate, String minDate, String maxDate, boolean isLimitDate, String title, int comeform) {
        this.activity = activity;
        this.initDate = initDate;
        this.minDate=minDate;
        this.maxDate = maxDate;
        this.isLimitDate = isLimitDate;
//        this.applyType = applyType;
        this.comefrom = comeform;
        this.title=title;
    }

    public void init(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        if (!(null == initDate || "".equals(initDate))) {
            calendar = this.getCalendarByInitData(initDate);
        } else {
            initDate = calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DAY_OF_MONTH);
        }
        Log.d("date--", initDate);
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
        if (isLimitDate) {
            try {
//                datePicker.setMinDate(DateUtil.converDate(initDate, "yyyy-MM-dd").getTime());
                if (!StringUtils.isEmpty(minDate)){
                    datePicker.setMinDate(DateUtil.converDate(minDate,"yyyy-MM-dd").getTime());
                }
                if (!StringUtils.isEmpty(maxDate)) {
                    datePicker.setMaxDate(DateUtil.converDate(maxDate, "yyyy-MM-dd").getTime());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 弹出日期时间选择框方法
     *
     * @param inputDate :为需要设置的日期时间文本编辑框
     * @return
     */
    public AlertDialog datePickerDialog(final EditText inputDate) {
        LinearLayout dateTimeLayout = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.common_date, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
        init(datePicker);

        ad = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle(initDate)
                .setView(dateTimeLayout)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText(date);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
//                        inputDate.setText("");
                    }
                }).show();

        onDateChanged(null, 0, 0, 0);
        return ad;
    }

    /**
     * 弹出日期时间选择框方法
     *
     * @param inputDate :为需要设置的日期时间文本编辑框
     * @return
     */
    public AlertDialog datePickerDialog(final Button inputDate) {
        LinearLayout dateTimeLayout = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.common_date, null);

        ad = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle(title!=null?title:initDate)
                .setView(dateTimeLayout)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
//                        boolean validateFlag = true;
                        if (comefrom==1) {
                            inputDate.setText(date);
                        } else {
                            inputDate.setText(DateUtil.getDayOfWeekAgo(date,"yyyy-MM-dd")+"--"+date);
//                            UIHelper.ToastMessage(activity, "日期设置异常");
//                            datePickerDialog(inputDate);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
//                        inputDate.setText("");
                    }
                }).show();


        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
        init(datePicker);

        onDateChanged(null, 0, 0, 0);
        return ad;
    }
    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        // 获得日历实例
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        date = sdf.format(calendar.getTime());
        ad.setTitle(date);
    }

    /**
     * 实现将初始日期时间2012-07-02拆分成年 月 日,并赋值给calendar
     *
     * @param initDate 初始日期时间值 字符串型
     * @return Calendar
     */
    private Calendar getCalendarByInitData(String initDate) {
        Calendar calendar = Calendar.getInstance();

        String yearStr = splitString(initDate, "-", "index", "front"); // 年份
        String monthAndDay = splitString(initDate, "-", "index", "back"); // 月日

        String monthStr = splitString(monthAndDay, "-", "index", "front"); // 月
        String dayStr = splitString(monthAndDay, "-", "index", "back"); // 日


        int currentYear = Integer.valueOf(yearStr.trim()).intValue();
        int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
        int currentDay = Integer.valueOf(dayStr.trim()).intValue();

        calendar.set(currentYear, currentMonth, currentDay);
        return calendar;
    }

    /**
     * 截取子串
     *
     * @param srcStr      源串
     * @param pattern     匹配模式
     * @param indexOrLast
     * @param frontOrBack
     * @return
     */
    public static String splitString(String srcStr, String pattern,
                                     String indexOrLast, String frontOrBack) {
        String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern); // 取得字符串第一次出现的位置
        } else {
            loc = srcStr.lastIndexOf(pattern); // 最后一个匹配串的位置
        }
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != -1)
                result = srcStr.substring(0, loc); // 截取子串
        } else {
            if (loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串
        }
        return result;
    }

}
