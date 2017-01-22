package com.sicnu.cheer.leaugebar.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sicnu.cheer.generalmodule.util.StringUtils;
import com.sicnu.cheer.generalmodule.util.UIHelper;
import com.sicnu.cheer.leaugebar.R;
import com.sicnu.cheer.leaugebar.utils.ValidateUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.bt_go)
    Button btGo;
    @InjectView(R.id.cv)
    CardView cv;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.forget_password_tv)
    TextView fpt;

    private Intent intent;
    private LoginActivity mThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mThis = this;
        initData();
    }

    private void initData() {
        ButterKnife.inject(this);
        intent = new Intent(this, RegisterOrResetActivity.class);
    }

    @OnClick({R.id.bt_go, R.id.fab,R.id.forget_password_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_password_tv:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                intent.putExtra("isRegister",false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
                break;
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                intent.putExtra("isRegister",true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
                break;
            case R.id.bt_go:
                doLogin();
                break;
        }
    }

    private void doLogin() {
//        if (!ValidateUtil.isMobileNO(etUsername.getText().toString())) {
//            UIHelper.ToastMessage(mThis,"请输入正确的手机号！");
//            return;
//        }
//        if (StringUtils.isEmpty(etPassword.getText().toString())){
//            UIHelper.ToastMessage(mThis,"请输入用户名密码！");
//            return;
//        }

        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        Intent i2 = new Intent(this,MainActivity.class);
        startActivity(i2, oc2.toBundle());

    }
}
