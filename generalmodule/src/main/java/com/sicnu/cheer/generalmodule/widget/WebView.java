package com.sicnu.cheer.generalmodule.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sctf.mobile.generalmodule.R;
import com.sicnu.cheer.generalmodule.bean.YKTUser;
import com.sicnu.cheer.generalmodule.util.SharedPreferencesUtil;
import com.sicnu.cheer.generalmodule.util.UIHelper;
import com.sicnu.cheer.generalmodule.util.UriUtil;
import com.sicnu.cheer.libqrcode.zxing.activity.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by cheer on 2016/4/25.
 */
public class WebView extends android.webkit.WebView {
    public static final int JS_LOGIN_USER = 11;
    public static final int JS_CONTACT = 12;
    public static final int JS_TIME_ONE = 13;
    public static final int JS_TIME_TWO = 14;
    public static final int JS_FILE = 15;
    public static final int JS_FILE_CHOOSER_CODE = 16;
    public static final int JS_SIGN_IN = 17;
    public static final int JS_F_CHOOSE_POPUP = 18;
    public static final int GO_BACK = 19;
    public static final int CONTACT_CODE = 1303;
    public static final int FILE_CODE = 1304;
    public static final int FILE_CHOOSER_RESULT_CODE = 1305;
    public static final int UPLOAD_FILE_CHOOSER_RESULT_CODE = 1306;
    public static final int SIGN_IN_REQUEST_CODE = 1307;
    public static final int JS_WHAT_SAVE_KEY_VALUE = 37;
    public static final int JS_WHAT_GET_VALUE_WITH_KEY = 38;

    private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> filePathCallback;
    private Handler handler;
    private Activity context = null;
    private WebViewCallback callback;

    public void setCallback(WebViewCallback callback) {
        this.callback = callback;
    }

    public WebView(Context context) {
        super(context);
        init();
    }

    public WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    private void init() {
        handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == JS_LOGIN_USER) {
                    loginUser();
                } else if (msg.what == JS_CONTACT) {
                    contact();
                } else if (msg.what == JS_TIME_ONE) {
                    timeOne();
                } else if (msg.what == JS_TIME_TWO) {
                    timeTwo();
                } else if (msg.what == JS_FILE) {
                    openFile();
                } else if (msg.what == JS_FILE_CHOOSER_CODE) {
                } else if (msg.what == JS_SIGN_IN) {
                    signInWithQrCode();
                } else if (msg.what == JS_F_CHOOSE_POPUP) {
                    choosePopup();
                } else if (msg.what == GO_BACK) {
                    context.onBackPressed();
                } else if (msg.what == JS_WHAT_SAVE_KEY_VALUE) {
                    String param = "";
                    if (msg.obj != null) {
                        param = msg.obj.toString();
                    }
                    saveKeyValue(param);
                } else if (msg.what == JS_WHAT_GET_VALUE_WITH_KEY) {
                    String param = "";
                    if (msg.obj != null) {
                        param = msg.obj.toString();
                    }
                    getValueWithKey(param);
                }
                return false;
            }
        });
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(false);

        addJavascriptInterface(this, "LOCAL");
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAppCacheEnabled(true);


        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(android.webkit.WebView view, String url, String message,
                                     final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }

            @Override
            public boolean onJsConfirm(android.webkit.WebView view, String url,
                                       String message, final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }

            @Override
            public boolean onJsPrompt(android.webkit.WebView view, String url, String message,
                                      String defaultValue, final JsPromptResult result) {
                final LayoutInflater factory = LayoutInflater.from(getContext());
                final View dialogView = factory.inflate(R.layout.prom_dialog, null);
                ((TextView) dialogView.findViewById(R.id.TextView_PROM)).setText(defaultValue);
                ((EditText) dialogView.findViewById(R.id.EditText_PROM)).setText(defaultValue);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView);
                builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String value = ((EditText) dialogView.findViewById(R.id.EditText_PROM)).getText().toString();
                        result.confirm(value);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
                builder.setOnCancelListener(new AlertDialog.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        result.cancel();
                    }
                });
                builder.show();
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
                setUploadFile(uploadFile);
            }

            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType) {
                openFileChooser(uploadFile, acceptType, null);
            }

            public void openFileChooser(ValueCallback<Uri> uploadFile) {
                openFileChooser(uploadFile, null);
            }


        });

    }


    public void setUploadFile(ValueCallback<Uri> uploadFile) {
        if (this.uploadFile != null) {
            this.uploadFile.onReceiveValue(null);
        }
        this.uploadFile = uploadFile;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        ((Activity) getContext()).startActivityForResult(Intent.createChooser(intent, "图片选择"), FILE_CHOOSER_RESULT_CODE);
    }

    public void setFilePathCallback(ValueCallback<Uri[]> filePathCallback) {
        if (this.filePathCallback != null) {
            this.filePathCallback.onReceiveValue(null);
        }
        this.filePathCallback = filePathCallback;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        ((Activity) getContext()).startActivityForResult(Intent.createChooser(intent, "图片选择"), FILE_CHOOSER_RESULT_CODE);
    }

    public void fileChooseResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            try {
                if (resultCode == Activity.RESULT_OK) {
                    if (this.uploadFile != null) {
                        if (data != null) {
                            Uri uri = data.getData();
                            this.uploadFile.onReceiveValue(data.getData());
                        }
                        this.uploadFile = null;
                    } else if (this.filePathCallback != null) {
                        if (data != null) {
                            String dataString = data.getDataString();
                            Uri[] uris = new Uri[]{Uri.parse(dataString)};
                            this.filePathCallback.onReceiveValue(uris);
                        }
                        this.filePathCallback = null;
                    }
                } else {
                    if (this.uploadFile != null) {
                        this.uploadFile.onReceiveValue(null);
                        this.uploadFile = null;
                    } else if (this.filePathCallback != null) {
                        this.filePathCallback.onReceiveValue(null);
                        this.filePathCallback = null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 保存数据到手机
     *
     * @param param
     */
    @JavascriptInterface
    private void saveKeyValueForJS(String param) {
        sendMessage(JS_WHAT_SAVE_KEY_VALUE, 0, param);
    }

    /**
     * 保存数据到手机
     *
     * @param param
     */
    private void saveKeyValue(String param) {
        try {
            JSONObject object = new JSONObject(param);
            if (object != null) {
                String key = object.optString("key");
                String value = object.optString("value");
                SharedPreferencesUtil.saveUserItem(getContext(), key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取手机保存的数据
     *
     * @param key
     */
    @JavascriptInterface
    private void getValueWithKeyForJs(String key) {
        sendMessage(JS_WHAT_GET_VALUE_WITH_KEY, 0, key);
    }

    /**
     * 获取手机保存的数据
     *
     * @param key
     */
    private void getValueWithKey(String key) {
        String value = SharedPreferencesUtil.getUserItem(getContext(), key);
        String url = "javascript:getValueWithKeyForResult(" + value + ")";
        loadUrl(url);
    }


    @JavascriptInterface
    public void f_choosePopup() {
        sendMessage(JS_F_CHOOSE_POPUP, 0);
    }


    private void choosePopup() {
        if (callback != null) {
            callback.callback("choosePopup");
        }
    }


    @JavascriptInterface
    public void signInForIJS() {
        sendMessage(JS_SIGN_IN, 0);
    }

    private void signInWithQrCode() {
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            activity.startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
        }
    }


    /**
     * 登录信息
     */
    @JavascriptInterface
    public void loginUserForJS() {
        sendMessage(JS_LOGIN_USER, 0);
    }


    /**
     * 登录信息
     */
    public void loginUser() {
        JSONObject jsonObject = getLoginUserJsonObject();
        String result = jsonObject.toString();
        this.loadUrl("javascript:SCTF.API.STATUS.OK('" + result + "')");
    }

    public JSONObject getLoginUserJsonObject() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        JSONObject jsonObject = new JSONObject();
        JSONObject loginUser = new JSONObject();
        try {
            loginUser.put("headPic", sharedPreferences.getString("headPic", "").replace("\\", "/"));
            loginUser.put("userId", sharedPreferences.getString("userId", ""));
            loginUser.put("userName", sharedPreferences.getString("realname", ""));
            loginUser.put("userMobile", sharedPreferences.getString("mobilePhone", ""));
            loginUser.put("userJobNo", sharedPreferences.getString("jobNumber", ""));
            loginUser.put("userOrgId", sharedPreferences.getString("departId", ""));
            loginUser.put("userOrgNo", sharedPreferences.getString("departNo", ""));
            loginUser.put("token", sharedPreferences.getString("token", ""));
            jsonObject.put("loginUser", loginUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 通讯录
     */
    @JavascriptInterface
    public void contactForJS() {
        sendMessage(JS_CONTACT, 0);
    }

    public void contactResult(List<YKTUser> users) {

        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            if (users != null) {
                for (int i = 0; i < users.size(); i++) {
                    JSONObject item = new JSONObject();
                    YKTUser user = users.get(i);
                    item.put("userId", user.getuId());
                    item.put("userName", user.getName());
                    item.put("userMobile", user.getMobile());
                    item.put("userOrgId", user.getOrganization().getOrgId());
                    item.put("userPortraitUrl", user.getHeadPortrait().getId().replace("\\", "/"));
                    jsonArray.put(item);
                }
            }
            jsonObject.put("staffs", jsonArray);
            this.loadUrl("javascript:SCTF.API.STATUS.OK('" + jsonObject.toString() + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通讯录
     */
    public void contact() {
        try {
            Intent intent = new Intent(getContext(), Class.forName("com.sctf.addressList.ContactsActivity"));
            intent.putExtra("isShowCheckBox", "1");
            intent.putExtra("isShowOkBt", "1");
            intent.putExtra("isMulitCheck", "1");
            ((Activity) getContext()).startActivityForResult(intent, CONTACT_CODE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置时间
     */
    @JavascriptInterface
    public void timeOneForJS() {
        sendMessage(JS_TIME_ONE, 0);
    }

    /**
     * 设置时间
     */
    public void timeOne() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.view_time_one, null);
        new AlertDialog.Builder(getContext()).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker1);
                TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker1);
                JSONObject jsonObject = new JSONObject();
                String time = getTime(datePicker, timePicker);
                try {
                    jsonObject.put("time", time);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadUrl("javascript:SCTF.API.STATUS.OK('" + jsonObject.toString() + "')");

            }
        }).setNegativeButton("取消", null).show();
    }

    @JavascriptInterface
    public void goBackMainForJs() {
        sendMessage(GO_BACK, 0);
    }

    /**
     * 设置时间范围
     */
    @JavascriptInterface
    public void timeTwoForJS() {
        sendMessage(JS_TIME_TWO, 0);
    }

    /**
     * 设置时间范围
     */
    public void timeTwo() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.view_time_two, null);
        new AlertDialog.Builder(getContext()).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatePicker datePicker1 = (DatePicker) view.findViewById(R.id.datePicker1);
                TimePicker timePicker1 = (TimePicker) view.findViewById(R.id.timePicker1);
                DatePicker datePicker2 = (DatePicker) view.findViewById(R.id.datePicker2);
                TimePicker timePicker2 = (TimePicker) view.findViewById(R.id.timePicker2);
                String time1 = getTime(datePicker1, timePicker1);
                String time2 = getTime(datePicker2, timePicker2);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("meetingStartTime", time1);
                    jsonObject.put("meetingEndTime", time2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadUrl("javascript:SCTF.API.STATUS.OK('" + jsonObject.toString() + "')");
            }
        }).setNegativeButton("取消", null).show();
    }

    private String getTime(DatePicker datePicker, TimePicker timePicker) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        String time = String.format("%04d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", day) + " " + String.format("%02d", hour) + ":" + String.format("%02d", minute) + ":00";
        return time;
    }


    @JavascriptInterface
    public void fileForJS() {
        sendMessage(JS_FILE, 0);
    }

    /**
     * 打开文件选择
     */
    public void openFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            ((Activity) getContext()).startActivityForResult(intent.createChooser(intent, "文件选择"), FILE_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.ToastMessage(getContext(), "没有文件管理器");
        }
    }

    /**
     * 选择文件结果
     *
     * @param uri
     */
    public void fileResult(Uri uri) {
        String base64 = getBase64FromUri(uri);
        String path = UriUtil.getPath(getContext(), uri);
        File file = new File(path);
        String name = file.getName();
        String type = "";
        if (name.contains(".")) {
            type = name.substring(name.lastIndexOf(".") + 1);
        }
        long size = file.length();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("size", size);
            jsonObject.put("type", type);
            jsonObject.put("content", base64);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.loadUrl("javascript:SCTF.API.STATUS.OK('" + jsonObject.toString() + "')");
    }

    /**
     * 根据Uri获取对应的文件路径
     *
     * @param uri
     * @return
     */
    private String getBase64FromUri(Uri uri) {
        String result = "";
        if (uri != null) {
            InputStream inputStream = null;
            ByteArrayOutputStream byteArrayOutputStream = null;
            try {
                inputStream = getContext().getContentResolver().openInputStream(uri);
                byteArrayOutputStream = new ByteArrayOutputStream();
                int len = 0;
                byte[] by = new byte[1024];
                while ((len = inputStream.read(by)) != -1) {
                    byteArrayOutputStream.write(by, 0, len);
                }
                byte[] bytes = byteArrayOutputStream.toByteArray();
                result = Base64.encodeToString(bytes, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private String getPathFromUri(Uri uri) {
        String path = "";
        String[] pro = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(uri, pro, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
    /**
     * 发送消息
     *
     * @param what
     * @param arg1
     * @param param
     */
    private void sendMessage(int what, int arg1, String param) {
        if (this.handler != null) {
            Message msg = new Message();
            msg.what = what;
            msg.arg1 = arg1;
            msg.obj = param;
            this.handler.sendMessage(msg);
        }
    }

    /**
     * 发送消息
     *
     * @param what
     * @param arg1
     */
    private void sendMessage(int what, int arg1) {
        if (this.handler != null) {
            Message msg = new Message();
            msg.what = what;
            msg.arg1 = arg1;
            this.handler.sendMessage(msg);
        }
    }


    public interface WebViewCallback {
        public void callback(String flag);
    }
}
