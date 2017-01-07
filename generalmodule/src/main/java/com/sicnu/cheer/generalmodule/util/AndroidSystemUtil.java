package com.sicnu.cheer.generalmodule.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取安卓手机系统方法的工具类
 * Created by cheer on 2016/11/19.
 */
public class AndroidSystemUtil {

    public static final String TAG = AndroidSystemUtil.class.getSimpleName();

    /**
     * 获取手机最近通话记录
     * @param context
     * @return
     */
    public static List<Map<String, String>> getRecentCalls(Context context) {
        List<Map<String,String>> resultList = new ArrayList();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(
                    // CallLog.Calls.CONTENT_URI, Columns, null,
                    // null,CallLog.Calls.DATE+" desc");
                    CallLog.Calls.CONTENT_URI, null, null, null,
                    CallLog.Calls.DATE + " desc");
            if (cursor == null)
                return null;
          
            while (cursor.moveToNext()) {
                Map<String,String> data =new HashMap<String, String>();

                String name = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.CACHED_NAME));
                String number = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.NUMBER));
                String type = String.valueOf(cursor.getInt(cursor
                        .getColumnIndex(CallLog.Calls.TYPE)));
                String lDate = String.valueOf(cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.DATE)));
                String duration = String.valueOf(cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.DURATION)));
                String _new = String.valueOf(cursor.getInt(cursor
                        .getColumnIndex(CallLog.Calls.NEW)));
//                Log.e(TAG, name+"    "+number);

                data.put("name", name);
                data.put("mobile", number);
                data.put("type", type);
                data.put("date", lDate);
                data.put("duration", duration);
                data.put("new",_new);

//                      int photoIdIndex = cursor.getColumnIndex(CACHED_PHOTO_ID);
//                      if (photoIdIndex >= 0) {
//                          String cachePhotoId = cursor.getLong(photoIdIndex);
//                      }

                resultList.add(data);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return resultList;
    }

    /**
     * 获取当前客户端版本信息
     */
    public static Map<String,String> getCurrentVersion(Context context) {
        Map<String,String> result = new HashMap<String, String>();
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            result.put("curVersionName",info.versionName);
            result.put("curVersionCode",info.versionCode+"");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return result;
    }
}
