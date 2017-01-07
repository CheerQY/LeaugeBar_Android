package com.sicnu.cheer.im.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.sicnu.cheer.im.R;


/**
 * Created by cheer on 2016/12/19.
 */

public class ConversationActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        String title = getIntent().getData().getQueryParameter("title");
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(title);
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            onBackPressed();
        }
    }
}
