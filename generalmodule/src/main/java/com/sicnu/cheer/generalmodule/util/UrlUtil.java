package com.sicnu.cheer.generalmodule.util;

import android.content.Context;

import com.sctf.mobile.generalmodule.R;

/**
 * url管理工具类
 * Created by cheer on 2016/11/4.
 */

public class UrlUtil {

    /**
     * 根据action获取url
     *
     * @param context
     * @param action
     * @return
     */
    public static String getUrlWithAction(Context context, String action) {
        String url = context.getString(R.string.generalRoot) + action;
        return url;
    }
}
