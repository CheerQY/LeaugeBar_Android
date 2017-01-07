package com.sicnu.cheer.generalmodule.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.sicnu.cheer.generalmodule.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日期时间选择控件 使用方法： private EditText inputDate;//需要设置的日期时间文本编辑框 private String
 * initDateTime="2012-9-3 14:44",//初始日期时间值 在点击事件中使用：
 * inputDate.setOnClickListener(new OnClickListener() {
 *
 * @author
 * @Override public void onClick(View v) { DateTimePickDialogUtil
 * datePickerDialog=new
 * DateTimePickDialogUtil(SinvestigateActivity.this,initDateTime);
 * datePickerDialog.datePickerDialog(inputDate);
 * <p/>
 * } });
 */
public class DateTimePickDialogUtil implements OnDateChangedListener,
        OnTimeChangedListener {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private AlertDialog ad;
    private String dateTime;
    private String initDateTime;
    private String maxDateTime;
    private Activity activity;
    //是否限制时间
    private boolean isEndDate = false;
    //申请类型，因为会根据不同的申请类型设置不同的时间规则
    //申请类型 1-- 出差	2-- 请假	3-- 外勤	4-- 加班	5-- 调休	6-- 其它  7--出差行程明细中的时间
    private int applyType;
    //控件来源 1 -- 开始时间  2--结束时间
    private int comefrom;

    /**
     * 日期时间弹出选择框构造函数
     *
     * @param activity     ：调用的父activity
     * @param initDateTime 初始日期时间值，作为弹出窗口的标题和日期时间初始值
     */
    public DateTimePickDialogUtil(Activity activity, String initDateTime, String maxDateTime, boolean isEndDate, int applyType, int comeform) {
        this.activity = activity;
        this.initDateTime = initDateTime;
        this.maxDateTime = maxDateTime;
        this.isEndDate = isEndDate;
        this.applyType = applyType;
        this.comefrom = comeform;
    }

    public void init(DatePicker datePicker, TimePicker timePicker) {
        Calendar calendar = Calendar.getInstance();
        if (!(null == initDateTime || "".equals(initDateTime))) {
            calendar = this.getCalendarByInintData(initDateTime);
        } else {
            initDateTime = calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DAY_OF_MONTH) + " "
                    + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                    + calendar.get(Calendar.MINUTE);
        }
        Log.d("date--", initDateTime + "---hour" + calendar.get(Calendar.HOUR_OF_DAY));
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
        if (isEndDate) {
            try {
                datePicker.setMinDate(DateUtil.converDate(initDateTime, "yyyy-MM-dd HH:mm").getTime());
                if (!StringUtils.isEmpty(maxDateTime)) {
                    datePicker.setMaxDate(DateUtil.converDate(maxDateTime, "yyyy-MM-dd HH:mm").getTime());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }

    /**
     * 弹出日期时间选择框方法
     *
     * @param inputDate :为需要设置的日期时间文本编辑框
     * @return
     */
    public AlertDialog dateTimePicKDialog(final EditText inputDate) {
        LinearLayout dateTimeLayout = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.common_date_time, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
        timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        init(datePicker, timePicker);
        timePicker.setOnTimeChangedListener(this);

        ad = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle(initDateTime)
                .setView(dateTimeLayout)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText(dateTime);
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
    public AlertDialog dateTimePicKDialog(final Button inputDate) {
        LinearLayout dateTimeLayout = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.common_date_time, null);

        ad = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle(initDateTime)
                .setView(dateTimeLayout)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        boolean validateFlag = true;
                        String tosMes = "加班结束时间不能超过第二天6点!";
                        //暂时取消
                        //针对加班申请设置小时限制 只能在当前时间到第二天的6点之前
//                        if (4 == applyType && comefrom == 2) {
//                            if (isEndDate) {
//                                try {
//                                    //开始时间的天数
//                                    int initTimeDay = Integer.valueOf(initDateTime.split(" ")[0].split("-")[2]);
//                                    //结束时间天数
//                                    int dayOfMonth = datePicker.getDayOfMonth();
//                                    //结束时间小时数
//                                    int hourOfDay = timePicker.getCurrentHour();
//
//                                    if (initTimeDay <= (dayOfMonth - 1)) {
//                                        if (hourOfDay > 6) {
//                                            validateFlag = false;
//                                        } else if (hourOfDay == 6) {
//                                            int minuteOfHour = timePicker.getCurrentMinute();
//                                            if (minuteOfHour > 0) {
//                                                validateFlag = false;
//                                            }
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    validateFlag = false;
//                                    tosMes = e.getMessage();
//                                }
//                            }
//                        }
                        if (validateFlag) {
                            inputDate.setText(dateTime);
                        } else {
                            UIHelper.ToastMessage(activity, tosMes);
                            dateTimePicKDialog(inputDate);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
//                        inputDate.setText("");
                    }
                }).show();


        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
        timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        init(datePicker, timePicker);
        timePicker.setOnTimeChangedListener(this);

        onDateChanged(null, 0, 0, 0);
        return ad;
    }

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null, 0, 0, 0);
    }

    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        // 获得日历实例  
        Calendar calendar = Calendar.getInstance();

//        if(isEndDate){
//            //针对加班申请设置小时限制
//            if(4 == applyType && comefrom == 2){
//                try {//反射
//
//                    int initTimeDay = Integer.valueOf(initDateTime.split(" ")[0].split("-")[2]);
//                    if(initTimeDay == (dayOfMonth - 1)){
//                        Field hourSpinnerField = timePicker.getClass().getDeclaredField("mHourSpinner");
//                        hourSpinnerField.setAccessible(true);
//                        NumberPicker hourSpinner = (NumberPicker) hourSpinnerField.get(timePicker);
//                        hourSpinner.setMinValue(0);
//                        hourSpinner.setMaxValue(6);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }


        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
                timePicker.getCurrentMinute());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        dateTime = sdf.format(calendar.getTime());
        ad.setTitle(dateTime);
    }

    /**
     * 实现将初始日期时间2012-07-02 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar
     *
     * @param initDateTime 初始日期时间值 字符串型
     * @return Calendar
     */
    private Calendar getCalendarByInintData(String initDateTime) {
        Calendar calendar = Calendar.getInstance();

        // 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒  
        String date = spliteString(initDateTime, " ", "index", "front"); // 日期  
        String time = spliteString(initDateTime, " ", "index", "back"); // 时间  

        String yearStr = spliteString(date, "-", "index", "front"); // 年份  
        String monthAndDay = spliteString(date, "-", "index", "back"); // 月日  

        String monthStr = spliteString(monthAndDay, "-", "index", "front"); // 月  
        String dayStr = spliteString(monthAndDay, "-", "index", "back"); // 日  

        String hourStr = spliteString(time, ":", "index", "front"); // 时  
        String minuteStr = spliteString(time, ":", "index", "back"); // 分  

        int currentYear = Integer.valueOf(yearStr.trim()).intValue();
        int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
        int currentDay = Integer.valueOf(dayStr.trim()).intValue();
        int currentHour = Integer.valueOf(hourStr.trim()).intValue();
        int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();

        calendar.set(currentYear, currentMonth, currentDay, currentHour,
                currentMinute);
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
    public static String spliteString(String srcStr, String pattern,
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
