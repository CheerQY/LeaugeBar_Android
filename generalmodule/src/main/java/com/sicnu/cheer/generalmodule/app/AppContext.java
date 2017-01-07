package com.sicnu.cheer.generalmodule.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.sicnu.cheer.generalmodule.R;
import com.sicnu.cheer.generalmodule.bean.YKTMenus;
import com.sicnu.cheer.generalmodule.util.SharedPreferencesUtil;
import com.sicnu.cheer.generalmodule.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by cheer on 2016/10/11.
 */
public class AppContext {
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;
    //定义全局变量，配合onCreate方法，以便在任何一个地方都可以获取AppContext对象
    public static String ACCESS_TOKEN = "";
    /**
     * 定义菜单集合（与权限相关）
     */
    public static List<YKTMenus> appMenusList = new ArrayList<YKTMenus>();
    /**
     * 定义权限集合
     */
    public static Map<String, Object> appRulesMap = new HashMap<String, Object>();
    /**
     * app菜单集合(与菜单相关)
     */
    public static List<YKTMenus> appMenus = new ArrayList<YKTMenus>();

    /**
     * app首页菜单集合(与菜单相关)
     */
    public static List<YKTMenus> appHomeMenus = new ArrayList<YKTMenus>();


    //允许编辑员工部门权限key
    public static final String CONTACT_EDIT_DEP = "YKTBianjiBuMen";
    //允许删除员工信息权限key
    public static final String CONTACT_DEL_PERSON = "YKTShanChuYuanGong";
    //仅发布本部门公告权限key
    public static final String ANNOUNCEMENT_DEPART = "ANNOUNCEMENT_DEPART";
    //仅发布本公司公告权限key
    public static final String ANNOUNCEMENT_COMPANY = "ANNOUNCEMENT_COMPANY";
    //仅发布指定部门公告权限key
    public static final String ANNOUNCEMENT_ASSIGN = "ANNOUNCEMENT_ASSIGN";
    //仅查询本部门公告权限key
    public static final String ANNOUNCEMENT_CHECK_DEPART = "ANNOUNCEMENT_CHECK_DEPART";
    //仅查询本公司公告权限key
    public static final String ANNOUNCEMENT_CHECK_COMPANY = "ANNOUNCEMENT_CHECK_COMPANY";
    //仅查询指定部门公告权限key
    public static final String ANNOUNCEMENT_CHECK_ASSIGN = "ANNOUNCEMENT_CHECK_ASSIGN";
    //考勤模块key
    public static final String YKTKaoQin = "ykt_app_attend_home";
    //一键打卡菜单权限key
    public static final String YKTYJDK = "YKTYJDK";
    //历史考勤菜单权限key
    public static final String YKTLSKQ = "YKTLSKQ";
    //部门考勤菜单权限key
    public static final String YKTBMKQ = "YKTBMKQ";
    //考勤模块菜单CODE
    public static final String YKT_APP_ATTEND_HOME = "ykt_app_attend_home";
    //门禁模块菜单CODE
    public static final String YKT_APP_DOOR_OPEN_HOME = "ykt_app_door_open_home";
    //会议管理模块菜单CODE
    public static final String YKT_APP_MEETING_HOME = "ykt_app_meeting_home";
    //申请模块菜单CODE
    public static final String YKT_APP_APPLY_HOME = "ykt_app_apply_home";
    //公告模块菜单CODE
    public static final String YKT_APP_NOTICE_HOME = "ykt_app_notice_home";
    //待办模块菜单CODE
    public static final String YKT_APP_TODO_HOME = "ykt_app_todo_home";
    //食堂消费模块菜单CODE
    public static final String YKT_APP_COSUME_HOME = "ykt_app_cosume_home";
    //物业模块菜单CODE
    public static final String YKT_APP_PROPERTY_HOME = "ykt_app_property_home";
    //视频模块菜单CODE
    public static final String YKT_APP_VIDEO_HOME = "ykt_app_video_home";
    //访客模块菜单CODE
    public static final String YKT_APP_VISITOR_HOME = "ykt_app_visitor_home";
    //车辆模块菜单CODE
    public static final String YKT_APP_CAR_HOME = "ykt_app_car_home";
    //能源模块菜单CODE
    public static final String YKT_APP_ENERGY_HOME = "ykt_app_energy_home";


    //请求码
    // 待办详情html页面
    public static final int REQUEST_CODE_FOR_TODO_COMMON_WEBVIEW = 1;
    //用户删除成功
    public static final int REQUEST_CODE_FOR_USER_DEL = 2;
    //部门编辑
    public static final int REQUEST_CODE_FOR_DEP_EDIT = 3;
    //返回码
    //待办详情html页面
    public static final int RESULT_CODE_FOR_TODO_COMMON_WEBVIEW = 11;
    //用户删除成功
    public static final int RESULT_CODE_FOR_USER_DEL = 12;
    //部门编辑
    public static final int RESULT_CODE_FOR_DEP_EDIT = 13;


    /**
     * 检测网络是否可用
     *
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!StringUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
     * <p/>
     * 渠道标志为：
     * 1，andriod（a）
     * <p/>
     * 识别符来源标志：
     * 1， wifi mac地址（wifi）；
     * 2， IMEI（imei）；
     * 3， 序列号（sn）；
     * 4， id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
     *
     * @return
     */
    public static String getDeviceId(Context context) {


        StringBuilder deviceId = new StringBuilder();
// 渠道标志
        deviceId.append("a");

        try {

//wifi mac地址
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String wifiMac = info.getMacAddress();
            if (!StringUtils.isEmpty(wifiMac)) {
                deviceId.append("wifi");
                deviceId.append(wifiMac);
                Log.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }

//IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if (!StringUtils.isEmpty(imei)) {
                deviceId.append("imei");
                deviceId.append(imei);
                Log.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }

//序列号（sn）
            String sn = tm.getSimSerialNumber();
            if (!StringUtils.isEmpty(sn)) {
                deviceId.append("sn");
                deviceId.append(sn);
                Log.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }

//如果上面都没有， 则生成一个id：随机码
            String uuid = getUUID(context);
            if (!StringUtils.isEmpty(uuid)) {
                deviceId.append("id");
                deviceId.append(uuid);
                Log.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id").append(getUUID(context));
        }

        Log.e("getDeviceId : ", deviceId.toString());

        return deviceId.toString();
    }


    /**
     * 得到全局唯一UUID
     */
    public static String getUUID(Context context) {
        String uuId = SharedPreferencesUtil.getUserItem(context, "uuId");
        if (StringUtils.isEmpty(uuId)) {
            uuId = UUID.randomUUID().toString();
            SharedPreferencesUtil.saveUserItem(context, "uuId", uuId);
        }
        Log.e("addUUid", "getUUID : " + uuId);
        return uuId;
    }

    /**
     * 获取附件的图片url
     *
     * @param context
     * @param attachId
     * @return
     */
    public static String getAttachPhotoPreUrl(Context context, String attachId) {
        String url = context.getString(R.string.generalRoot) + context.getString(R.string.attachPhotoPreAction);
        return url + "?picture=" + attachId;
    }

    /**
     * 获取头像的图片url
     *
     * @param context
     * @param userId
     * @param path
     * @return
     */
    public static String getUserPhotoPreUrl(Context context, String userId, String path) {
        String url = context.getString(R.string.generalRoot) + context.getString(R.string.userHeadPhotoAction);
        return url + "?userId=" + userId + "&path=" + path;
    }


}
