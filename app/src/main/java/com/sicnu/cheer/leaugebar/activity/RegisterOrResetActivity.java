package com.sicnu.cheer.leaugebar.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
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
import com.sicnu.cheer.leaugebar.R;
import com.sicnu.cheer.leaugebar.constants.Constants;
import com.sicnu.cheer.leaugebar.utils.CountDownTimerUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterOrResetActivity extends AppCompatActivity {
    private static final String COUNTRY_CODE="86";//默认中国区号
    private final String TAG="RegisterOrResetActivity";
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
                    //提交验证码成功
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    Log.d(TAG, "afterEvent: "+data);
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                    Log.d(TAG, "afterEvent: result:"+result);
                    Log.d(TAG, "afterEvent: "+data);
                }
            }else{
                ((Throwable)data).printStackTrace();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_or_reset);
        mThis = this;
        SMSSDK.initSDK(getApplicationContext(), Constants.appkey,Constants.appSecret);//初始化短信验证服务类
        SMSSDK.registerEventHandler(eh);//注册短信验证回调接收器
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
        intData();
        afterViews();
    }


    private void intData() {
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
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(sendVerify, 60000, 1000);
                mCountDownTimerUtils.start();
                String phone = phoneET.getText().toString().trim().replaceAll("\\s*", "");

               SMSSDK.getVerificationCode(COUNTRY_CODE,phone);
                break;
            case R.id.bt_go:
                //手动隐藏键盘
                ScreenUtils.hideKeyboard(mThis);
                //检测输入合法性
                checkValid();
                break;
            default:
                break;
        }
    }

    private void checkValid() {
        //手机号和验证码验证
        String phone = phoneET.getText().toString().trim().replaceAll("\\s*", "");
        String code = verifyCodeET.getText().toString().trim();
        if (!StringUtils.isEmpty(phone)&& !StringUtils.isEmpty(code)) {
            SMSSDK.submitVerificationCode(COUNTRY_CODE,phone,code);
        }
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
