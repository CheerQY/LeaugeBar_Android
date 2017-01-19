package com.sicnu.cheer.leaugebar.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sicnu.cheer.generalmodule.util.ScreenUtils;
import com.sicnu.cheer.generalmodule.util.StringUtils;
import com.sicnu.cheer.generalmodule.util.UIHelper;
import com.sicnu.cheer.leaugebar.R;
import com.sicnu.cheer.leaugebar.constants.Constants;
import com.sicnu.cheer.leaugebar.utils.CountDownTimerUtils;
import com.sicnu.cheer.leaugebar.utils.ValidateUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterOrResetActivity extends AppCompatActivity {
    private static final String COUNTRY_CODE="86";//默认中国区号
    private final String TAG="RegisterOrResetActivity";
    private static final int VERIFICATION_WRONG =2;
    private static final int VERIFICATION_RIGHT =3;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.cv_add)
    CardView cvAdd;
    @InjectView(R.id.send_verify_btn)
    Button sendVerify;
    @InjectView(R.id.register_reset_tv)
    TextView registerOrReset;
    @InjectView(R.id.et_phone)
    EditText phoneET;
    @InjectView(R.id.et_verify_code)
    EditText verifyCodeET;
    @InjectView(R.id.et_password)
    EditText passwordET;
    @InjectView(R.id.et_repeatpassword)
    EditText repeatPasswordET;

    private boolean isRegister;
    private RegisterOrResetActivity mThis;

    EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功之后提交用户信息到服务器
                    submitUserInfo();
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    Log.d(TAG, "afterEvent: "+data);
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                mHandler.sendEmptyMessage(VERIFICATION_WRONG);
                ((Throwable)data).printStackTrace();
            }
        }
    };
    private String phone;
    private String code;
    private String password;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what== VERIFICATION_WRONG){
                UIHelper.ToastMessage(mThis,"验证码错误或失效！");
            }else if (msg.what==VERIFICATION_RIGHT){
                UIHelper.ToastMessage(mThis,"提交服务器成功！");
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_or_reset);
        mThis = this;
        intData();
        afterViews();
    }


    private void intData() {
        //初始化短信验证服务类
        SMSSDK.initSDK(getApplicationContext(), Constants.appkey,Constants.appSecret);
        //注册短信验证回调接收器
        SMSSDK.registerEventHandler(eh);
        ButterKnife.inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });

        isRegister = getIntent().getBooleanExtra("isRegister",false);
    }

    private void afterViews() {
        registerOrReset.setText(getResources().getString(isRegister?R.string.register:R.string.password_reset));
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.mipmap.plus);
                RegisterOrResetActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @OnClick({R.id.send_verify_btn, R.id.bt_go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_verify_btn:
                sentVerificationCode();
                break;
            case R.id.bt_go:
                //手动隐藏键盘
                ScreenUtils.hideKeyboard(mThis);
                //检测输入合法性
                if (checkValid()) {
                    SMSSDK.submitVerificationCode(COUNTRY_CODE,phone,code);
                }
                break;
            default:
                break;
        }
    }

    private void sentVerificationCode() {
        //1、验证手机号合法性
        String phone = phoneET.getText().toString().trim().replaceAll("\\s*", "");
        if (ValidateUtil.isMobileNO(phone)){
            //2、发送验证码到手机
            SMSSDK.getVerificationCode(COUNTRY_CODE,phone);
            //3、发送验证码之后开始倒计时
            CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(sendVerify, 60000, 1000);
            mCountDownTimerUtils.start();
        }else {
            UIHelper.ToastMessage(mThis,"清输入正确的手机号！");
        }

    }

    private boolean checkValid() {
        //1、手机号和验证码验证
        phone = phoneET.getText().toString().trim().replaceAll("\\s*", "");
        code = verifyCodeET.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            UIHelper.ToastMessage(mThis,"请输入手机号！");
            return false;
        }
        if (StringUtils.isEmpty(code)){
            UIHelper.ToastMessage(mThis,"请输入验证码！");
            return false;
        }
        //2、密码和确认密码验证
        password = passwordET.getText().toString();
        String repeatPassword=repeatPasswordET.getText().toString();
        if (StringUtils.isEmpty(password)){
            UIHelper.ToastMessage(mThis,"请输入密码！");
            return false;
        }
        if (StringUtils.isEmpty(repeatPassword)){
            UIHelper.ToastMessage(mThis,"请输入确认密码！");
            return false;
        }
        if (!password.equals(repeatPassword)){
            UIHelper.ToastMessage(mThis,"密码和确认密码不一致，请重置");
            passwordET.setText("");
            repeatPasswordET.setText("");
            return false;
        }
        return true;
    }

    private void submitUserInfo() {
        mHandler.sendEmptyMessage(VERIFICATION_RIGHT);
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}
