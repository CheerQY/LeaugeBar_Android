package com.sicnu.cheer.leaugebar.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sicnu.cheer.leaugebar.R;
import com.sicnu.cheer.leaugebar.fragment.GuideFragment;
import com.sicnu.cheer.leaugebar.fragment.GuideTraslatorPageTransform;

public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int[] layouts = {
            R.layout.guide1,
            R.layout.guide2,
            R.layout.guide3
    };
    private GuideActivity mThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        mThis = this;
        initView();
        WelcomePagerAdapter adapter = new WelcomePagerAdapter(getSupportFragmentManager());
        //设置viewPager同时缓存的界面数量
        viewPager.setPageTransformer(true, new GuideTraslatorPageTransform());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
    }

    public void initView() {
        viewPager = ((ViewPager) findViewById(R.id.viewPager));
        findViewById(R.id.jump_over_guide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mThis, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private class WelcomePagerAdapter extends FragmentPagerAdapter {
        public WelcomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //获取每个页面视图
        @Override
        public Fragment getItem(int position) {
            //每个页面是一个fragment
            Fragment f = new GuideFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("layout", layouts[position]);
            f.setArguments(bundle);
            return f;
        }

        //决定viewpager有多少页
        @Override
        public int getCount() {
            return layouts.length;
        }
    }
}
