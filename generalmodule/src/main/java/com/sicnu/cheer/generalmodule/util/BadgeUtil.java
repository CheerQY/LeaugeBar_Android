package com.sicnu.cheer.generalmodule.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;

import com.sctf.mobile.generalmodule.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 应用图标角标提示工具
 * Created by cheer on 2016/11/28.
 */
public class BadgeUtil {

    /**
     * 设置角标数字
     * @param context
     * @param count
     */
    public static void setBadgeCount(Context context, int count) {
        if (count < 0) {
            count = 0;
        } else {
            count = Math.max(0, Math.min(count, 99));
        }
      String manufacturer =  Build.MANUFACTURER;
        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            sendToXiaoMi(context, count);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("sony")) {
            sendToSony(context, count);
        } else if (Build.MANUFACTURER.toLowerCase().contains("samsung")) {
            sendToSamsung(context, count);
        }else {
            sendToSamsung(context,count);
        }
    }

    /**
     * 清除角标数字
     * @param context
     */
    public static void clearBadgeCount(Context context){
        setBadgeCount(context,0);
    }
    /**
     * 小米手机
     *
     * @param context
     * @param count
     */
    private static void sendToXiaoMi(Context context, int count) {
        try {
//            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            Notification notification = null;
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//                builder.setContentTitle("您有" + count + "未读消息");
//                builder.setTicker("您有" + count + "未读消息");
//                builder.setAutoCancel(true);
//                builder.setDefaults(Notification.DEFAULT_LIGHTS);
//                notification = builder.build();
//                Class miuiNotificationClass = Class.forName("android.app.MiuiNotification");
//                Object miuiNotification = miuiNotificationClass.newInstance();
//                Field field = miuiNotification.getClass().getDeclaredField("messageCount");
//                field.setAccessible(true);
//                field.set(miuiNotification, count);// 设置信息数
//                field = notification.getClass().getField("extraNotification");
//                field.setAccessible(true);
//                field.set(notification, miuiNotification);


            NotificationManager mNotificationManager = (NotificationManager) context

                    .getSystemService(Context.NOTIFICATION_SERVICE);

            Notification.Builder builder = new Notification.Builder(context)

                    .setContentTitle("title").setContentText("text").setSmallIcon(R.drawable.icon_no_result);

            Notification notification = builder.build();

            try {

                Field field = notification.getClass().getDeclaredField("extraNotification");

                        Object extraNotification = field.get(notification);

                Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);

                method.invoke(extraNotification, count);

            } catch (Exception e) {

                e.printStackTrace();

            }

            mNotificationManager.notify(0,notification);




//            Class miuiNotificationClass = Class.forName("android.app.MiuiNotification");
//            Object miniNotification = miuiNotificationClass.newInstance();
//            Field field = miuiNotificationClass.getDeclaredField("messageCount");
//            field.setAccessible(true);
//            field.set(miniNotification, count);
        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
            intent.putExtra("android.intent.extra.update_application_component_name", context.getPackageName() + "/" + getLauncherClassName(context));
            intent.putExtra("android.intent.extra.update_application_message_text", String.valueOf(count == 0 ? "" : count));
            context.sendBroadcast(intent);
        }
    }

    /**
     * 索尼手机
     *
     * @param context
     * @param count
     */
    private static void sendToSony(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        boolean isShow = true;
        if (count == 0) {
            isShow = false;
        }
        Intent intent = new Intent();
        intent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", isShow);
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", launcherClassName);
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", String.valueOf(count));
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.getPackageName());
        context.sendBroadcast(intent);
    }

    /**
     * 三星手机
     *
     * @param context
     * @param count
     */
    private static void sendToSamsung(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }


    /**
     * 获取launcherClassName
     *
     * @param context
     * @return
     */
    private static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ResolveInfo resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo == null) {
            resolveInfo = packageManager.resolveActivity(intent, 0);
        }
        return resolveInfo.activityInfo.name;
    }
}
