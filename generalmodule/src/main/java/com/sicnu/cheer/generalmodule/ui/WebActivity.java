package com.sicnu.cheer.generalmodule.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.sicnu.cheer.generalmodule.R;
import com.sicnu.cheer.generalmodule.widget.WebView;

/**
 * html页面
 * Created by cheer on 2016/12/7.
 */

public class WebActivity extends Activity implements View.OnClickListener {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.webView);
        TextView titleView = (TextView) findViewById(R.id.title);
        findViewById(R.id.back).setOnClickListener(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        titleView.setText(title);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            onBackPressed();
        }
    }
}
