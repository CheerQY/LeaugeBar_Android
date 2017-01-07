package com.sicnu.cheer.generalmodule.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sctf.mobile.generalmodule.R;

import java.io.File;
import java.io.IOException;
import java.util.Map;


/**
 * 应用程序UI工具包：封装UI相关的一些操作
 *
 * @author wangkun
 * @version 1.0
 * @created 2015-9-11
 */
public class UIHelper {

    public static final int CAMERA_REQUEST_CODE = 1001;
    public static final int ALBUM_REQUEST_CODE = 1002;
    public static final int CUT_IMAGE_REQUEST_CODE = 1003;

    /**
     * 弹出Toast消息
     *
     * @param cont
     * @param msg
     */
    public static void ToastMessage(Context cont, String msg) {
        Toast toast = Toast.makeText(cont, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 100);
        toast.show();
    }

    public static void ToastMessage(Context cont, int msg) {
        Toast toast = Toast.makeText(cont, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 100);
        toast.show();
    }

    public static void ToastMessage(Context cont, String msg, int time) {
        Toast toast = Toast.makeText(cont, msg, time);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 100);
        toast.show();
    }

    public static void DialogMessage(Context cont, String title, String message, final ConfirmCallBack confirmCallBack) {
        // 用于子线程与主线程通信的Handler
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //super.handleMessage(msg);
                // 将返回值回调到callBack的参数中
                if (msg.what == 0) {
                    //确定按钮的事件
                    confirmCallBack.confirmCallBack();
                } else if (msg.what == 1) {
                    //返回按钮的事件
                    confirmCallBack.cancelCallBack();
                }

            }

        };
        new AlertDialog.Builder(cont).setTitle(title)//设置对话框标题
                //.setIcon(android.R.drawable.ic_dialog_info)//设置图标
                .setMessage(message)//设置显示的内容
                .setPositiveButton(cont.getString(R.string.ok), new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        mHandler.sendMessage(mHandler.obtainMessage(0, null));
                        //finish();
                    }
                })
                .setNegativeButton(cont.getString(R.string.back), new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        mHandler.sendMessage(mHandler.obtainMessage(1, null));
                        //Log.i("alertdialog"," 请保存数据！");
                    }
                })
                .show();//在按键响应事件中显示此对话框
    }

    /**
     * 点击返回监听事件,用于关闭当前Activity
     *
     * @param activity
     * @return
     */
    public static View.OnClickListener finish(final Activity activity) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                activity.finish();
            }
        };
    }

    /**
     * 点击“确定”返回接口
     */
    public interface ConfirmCallBack {
        public void confirmCallBack();

        public void cancelCallBack();
    }

    /**
     * 带输入框的对话框
     *
     * @param activity     上下文
     * @param title        标题
     * @param defaultValue 默认内容
     * @param hint         提示内容
     * @param callback     点击按钮的回调
     */
    public static void inputDialog(Activity activity, String title, String defaultValue, String hint, InputDialogCallback callback) {
        inputDialog(activity, title, defaultValue, hint, InputType.TYPE_CLASS_TEXT, callback);
    }

    /**
     * 带输入框的对话框
     *
     * @param activity     上下文
     * @param title        标题
     * @param defaultValue 默认内容
     * @param hint         提示内容
     * @param inputType    输入类型
     * @param callback     点击按钮的回调
     */
    public static void inputDialog(Activity activity, String title, String defaultValue, String hint, int inputType, InputDialogCallback callback) {
        inputDialog(activity, title, defaultValue, hint, inputType, false, callback);
    }

    /**
     * 带输入框的对话框
     *
     * @param activity     上下文
     * @param title        标题
     * @param defaultValue 默认内容
     * @param hint         提示内容
     * @param inputType    输入类型
     * @param isNull       是否允许为空,默认不允许
     * @param callback     点击按钮回调
     */
    public static void inputDialog(final Activity activity, String title, String defaultValue, String hint, int inputType, final boolean isNull, final InputDialogCallback callback) {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_input_dialog, null);
        final EditText editText = (EditText) view.findViewById(R.id.input);
        if (defaultValue == null) {
            defaultValue = "";
        }
        final String defaultV = defaultValue;
        editText.setInputType(inputType);
        editText.setHint(hint);
        editText.setText(defaultValue);
        editText.setSelection(defaultValue.length());
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(title);
        final Dialog dialog = new Dialog(activity);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable());
        window.requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        window.getAttributes().width = displayMetrics.widthPixels / 5 * 4;
        dialog.show();
        Button submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = editText.getText().toString().trim();
                if (StringUtils.isEmpty(value) && !isNull) {
                    ToastMessage(activity, "不能为空");
                } else {
                    if (callback != null) {
                        callback.confirmCallBack(defaultV, value);
                    }
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            }
        });
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.cancelCallBack(defaultV);
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
    }

    /**
     * 自定义提示信息对话框(默认确定和取消按钮，默认可取消)
     *
     * @param context
     * @param title
     * @param message
     * @param callback
     */
    public static void showDialog(Context context, String title, String message, final DialogCallback callback) {
        showDialog(context, title, message, true, callback);
    }

    /**
     * 自定义提示信息对话框(默认确定和取消按钮，可设置是否能取消)
     *
     * @param context
     * @param title
     * @param message
     * @param cancelEnable
     * @param callback
     */
    public static void showDialog(Context context, String title, String message, boolean cancelEnable, final DialogCallback callback) {
        showDialog(context, title, message, "确定", "取消", cancelEnable, callback);
    }

    /**
     * 自定义提示信息对话框(可自定义确定、取消按钮文字，默认可取消)
     * 单按钮的只设置一个值，回调对应设置的按钮
     *
     * @param context
     * @param title
     * @param message
     * @param submitButton
     * @param cancelButton
     * @param callback
     */
    public static void showDialog(Context context, String title, String message, String submitButton, String cancelButton, final DialogCallback callback) {
        showDialog(context, title, message, submitButton, cancelButton, true, callback);
    }


    /**
     * 自定义提示信息对话框(可自定义确定、取消按钮文字，可设置是否能取消)
     * 单按钮的只设置一个值，回调对应设置的按钮
     *
     * @param context
     * @param title
     * @param message
     * @param submitButton
     * @param cancelButton
     * @param cancelEnable
     * @param callback
     */
    public static void showDialog(Context context, String title, String message, String submitButton, String cancelButton, boolean cancelEnable, final DialogCallback callback) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null);
        TextView titleText = (TextView) view.findViewById(R.id.title);
        if (StringUtils.isEmpty(title)) {
            ((View) titleText.getParent()).setVisibility(View.GONE);
        } else {
            titleText.setText(title);
        }
        TextView messageText = (TextView) view.findViewById(R.id.message);
        if (StringUtils.isEmpty(message)) {
            messageText.setVisibility(View.GONE);
        } else {
            messageText.setText(message);
        }

        final Dialog dialog = new Dialog(context);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.requestFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.setCancelable(cancelEnable);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        window.getAttributes().width = wm.getDefaultDisplay().getWidth() / 5 * 4;
        dialog.show();

        Button submit = (Button) view.findViewById(R.id.submit);
        if (StringUtils.isEmpty(submitButton)) {
            submit.setVisibility(View.GONE);
        } else {
            submit.setText(submitButton);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.confirmCallBack();
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        Button cancel = (Button) view.findViewById(R.id.cancel);
        if (StringUtils.isEmpty(cancelButton)) {
            cancel.setVisibility(View.GONE);
        } else {
            cancel.setText(cancelButton);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.cancelCallBack();
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

    }


    /**
     * 自定义提示信息对话框(可自定义确定、取消按钮文字，可设置是否能取消)
     * 单按钮的只设置一个值，回调对应设置的按钮
     *
     * @param context
     * @param title
     * @param message
     * @param submitButton
     * @param cancelButton
     * @param cancelEnable
     * @param callback
     */
    public static void showDialog4Update(Context context, String title, String message, String submitButton, String cancelButton, boolean cancelEnable, final DialogCallback callback) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_update, null);
        TextView titleText = (TextView) view.findViewById(R.id.title);
        if (StringUtils.isEmpty(title)) {
            ((View) titleText.getParent()).setVisibility(View.GONE);
        } else {
            titleText.setText(title);
        }
        TextView messageText = (TextView) view.findViewById(R.id.message);
//        message = message.replaceAll("\t","");
        if (StringUtils.isEmpty(message)) {
            messageText.setVisibility(View.GONE);
        } else {
            messageText.setText(message);
        }

        final Dialog dialog = new Dialog(context);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.requestFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.setCancelable(cancelEnable);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        window.getAttributes().width = wm.getDefaultDisplay().getWidth() / 5 * 4;
        dialog.show();

        Button submit = (Button) view.findViewById(R.id.submit);
        if (StringUtils.isEmpty(submitButton)) {
            submit.setVisibility(View.GONE);
        } else {
            submit.setText(submitButton);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.confirmCallBack();
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        Button cancel = (Button) view.findViewById(R.id.cancel);
        if (StringUtils.isEmpty(cancelButton)) {
            cancel.setVisibility(View.GONE);
        } else {
            cancel.setText(cancelButton);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.cancelCallBack();
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

    }

    /**
     * 带输入框对话框点击按钮回调接口
     */
    public interface InputDialogCallback {
        /**
         * 确定按钮回调方法
         *
         * @param text  传入输入框的原始值
         * @param value 输入框新输入的值
         */
        public void confirmCallBack(String text, String value);

        /**
         * 取消按钮的回调方法
         *
         * @param text 传入输入框的原始值
         */
        public void cancelCallBack(String text);

    }

    /**
     * 对话框点击按钮回调接口
     */
    public interface DialogCallback {
        /**
         * 确定按钮回调方法
         */
        public void confirmCallBack();

        /**
         * 取消按钮的回调方法
         */
        public void cancelCallBack();

    }

    /**
     * 获取头像
     *
     * @param activity
     */
    public static void getPhoto(final Activity activity, boolean isCamera, final PhotoCallback callback) {
        final PopupWindow popupWindow = new PopupWindow(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_popuwindow_photo, null);
        View cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        View camera = view.findViewById(R.id.camera);
        if (!isCamera) {
            camera.setVisibility(View.GONE);
        }
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                File file = activity.getExternalFilesDir("image");
                try {
                    file = File.createTempFile("temp", ".png", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                activity.startActivityForResult(intent, UIHelper.CAMERA_REQUEST_CODE);
                if (callback != null) {
                    callback.cameraUri(uri);
                }
            }
        });
        View album = view.findViewById(R.id.album);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activity.startActivityForResult(intent, UIHelper.ALBUM_REQUEST_CODE);
            }
        });
        popupWindow.setContentView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 获取头像回调接口
     */
    public interface PhotoCallback {
        /**
         * 相机照片uri
         *
         * @param uri
         */
        public void cameraUri(Uri uri);
    }

    /**
     * 设置列表项的是否可点击
     *
     * @param view
     * @param clicked
     */
    public static void setItemClicked(View view, boolean clicked) {
        if (view != null) {
            try {
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                View v = viewGroup.getChildAt(2);
                if (clicked) {
                    v.setVisibility(View.VISIBLE);
                } else {
                    v.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取列表项是否可点击
     *
     * @param view
     * @return
     */
    public static boolean getItemClicked(View view) {
        boolean clicked = false;
        try {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup != null) {
                View v = viewGroup.getChildAt(2);
                if (v.getVisibility() == View.VISIBLE) {
                    clicked = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clicked;
    }

    /**
     * 设置未读消息是否显示
     *
     * @param result
     */
    public static void setNotRedCountDisplay(Activity activity, Map result) {
        Resources res = activity.getResources();
        if (!result.isEmpty()) {
            for (Object key : result.keySet()) {
                String keyStr = String.valueOf(key);
                for (String[] config : MsgMenuCodeConfig.MSG_MENU_CODE_CONFIG) {
                    if (config[1].equals(keyStr)) {
                        TextView view = (TextView) activity.findViewById(res.getIdentifier(config[2], "id", activity.getPackageName()));
                        if (view != null) {
                            int count = (Integer) result.get(keyStr);
                            if (count > 0) {
                                view.setVisibility(View.VISIBLE);
                                if (count > 99) {
                                    view.setText(99 + "+");
                                } else {
                                    view.setText(count + "");
                                }
                            } else {
                                view.setVisibility(View.GONE);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * 设置未读消息隐藏
     *
     * @param activity
     */
    public static void setNotRedCountdismiss(Activity activity) {
        Resources res = activity.getResources();
        for (String[] config : MsgMenuCodeConfig.MSG_MENU_CODE_CONFIG) {
            TextView view = (TextView) activity.findViewById(res.getIdentifier(config[2], "id", activity.getPackageName()));
            if (view != null) {
                view.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置输入框的小数输入限制
     *
     * @param editText
     * @param decimalLength 最大小数位数
     * @param maxValue      最大值
     */
    public static void setEditTextStyle4Decimal(final Context context, final EditText editText, final int decimalLength, final int maxValue) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > decimalLength) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + decimalLength + 1);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(1, 2));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!"".equals(s.toString()) && !".".equals(s.toString())) {

                    try {
                        if (Double.valueOf(s.toString()) > maxValue) {
                            UIHelper.ToastMessage(context, "超出了最大输入限制");
                            editText.setText("");
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


    /**
     * 对话框提示（适用于提示消息 ，只有确定操作）
     *
     * @param cont
     * @param title
     * @param message
     * @param confirmTipCallBack
     */
    public static void DialogTip(Context cont, String title, String message, final ConfirmTipCallBack confirmTipCallBack) {
        // 用于子线程与主线程通信的Handler
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //super.handleMessage(msg);
                // 将返回值回调到callBack的参数中
                if (msg.what == 0) {
                    //确定按钮的事件
                    confirmTipCallBack.confirmCallBack();
                }
            }

        };
        new AlertDialog.Builder(cont).setTitle(title)//设置对话框标题
                //.setIcon(android.R.drawable.ic_dialog_info)//设置图标
                .setMessage(message)//设置显示的内容
                .setPositiveButton(cont.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        mHandler.sendMessage(mHandler.obtainMessage(0, null));
                        //finish();
                    }
                })

                .show();//在按键响应事件中显示此对话框

    }

    /**
     * 验证失败后的对话框提示
     *
     * @param context
     * @param message
     * @return
     */
    public static boolean validateFalseSubmitWithDialogTip(Context context, String message) {

        UIHelper.DialogTip(context, context.getResources().getString(R.string.tip), message, new UIHelper.ConfirmTipCallBack() {
            @Override
            public void confirmCallBack() {
            }
        });
        return false;
    }

    /**
     * 点击“确定”返回接口
     */
    public interface ConfirmTipCallBack {
        void confirmCallBack();
    }

    /**
     * 代码中设置view的margin(针对viewGroup.Layoutparams有效)
     *
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

}
