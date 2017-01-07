package com.sicnu.cheer.generalmodule.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sicnu.cheer.generalmodule.bean.Department;
import com.sicnu.cheer.generalmodule.bean.LoginInfo;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * SharedPreferences操作工具类
 * Created by cheer on 2016/11/7.
 */

public class SharedPreferencesUtil {
    /**
     * 获取SharedPreferences
     *
     * @param context
     * @return
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    /**
     * 保存登录用户信息
     *
     * @param context
     * @param userJson
     */
    public static void saveLoginInfo(Context context, String userJson, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            editor.putString("userJson", userJson);
            editor.putString("password", password);
            JSONObject user = new JSONObject(userJson);
            Iterator<String> iterator = user.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = user.optString(key, "");
                if ("null".equals(value)) {
                    value = "";
                }
                editor.putString(key, value);
            }
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户登录信息
     *
     * @param context
     * @return
     */
    @Nullable
    public static LoginInfo getLoginInfo(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String userJson = sharedPreferences.getString("userJson", "");
        if (userJson.length() > 0) {
            Gson gson = new Gson();
            LoginInfo loginInfo = gson.fromJson(userJson, LoginInfo.class);
            return loginInfo;
        }
        return null;
    }

    /**
     * 获取登录用户部门信息
     *
     * @param context
     * @return
     */
    @Nullable
    public static Department getDepartment(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String departmentJson = sharedPreferences.getString("departs", "");
        if (departmentJson.length() > 0) {
            Gson gson = new Gson();
            List<Department> departments = gson.fromJson(departmentJson, new TypeToken<List<Department>>() {
            }.getType());
            if (departments != null && departments.size() > 0) {
                return departments.get(0);
            }
        }
        return null;
    }

    /**
     * 获取登录用户部门列表
     *
     * @param context
     * @return
     */
    @Nullable
    public static List<Department> getDepartments(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String departmentJson = sharedPreferences.getString("departs", "");
        if (departmentJson.length() > 0) {
            Gson gson = new Gson();
            List<Department> departments = gson.fromJson(departmentJson, new TypeToken<List<Department>>() {
            }.getType());
            return departments;
        }
        return null;
    }

    /**
     * 获取userId
     *
     * @param context
     * @return
     */
    public static String getUserId(Context context) {
        String userId = getUserItem(context, "userId");
        return userId;
    }

    /**
     * 根据key获取对应值
     *
     * @param context
     * @param key
     * @return
     */
    public static String getUserItem(Context context, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String value = sharedPreferences.getString(key, "");
        return value;
    }

    /**
     * 单独保存一项数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveUserItem(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).commit();
    }

    /**
     * 清空所有用户数据
     *
     * @param context
     */
    public static void clearUserInfo(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();
    }
}
