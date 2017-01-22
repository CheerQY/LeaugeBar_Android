package com.sicnu.cheer.leaugebar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sicnu.cheer.leaugebar.R;
import com.srx.widget.TabBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheer on 2017/1/10.
 * 主页面的主模块
 */

public class HomeFragment extends Fragment{
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppCompatActivity context;
    private TabBarView tabBarView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,null);
        context = ((AppCompatActivity) getActivity());
        initView(view);
        setupToolbar();
        setupViewPager();
        setupCollapsingToolbar();
        setupTabView();
        return view;
    }

    private void initView(View view) {
        viewPager = ((ViewPager) view.findViewById(R.id.viewpager));
        tabLayout = ((TabLayout) view.findViewById(R.id.tabs));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        collapsingToolbar = ((CollapsingToolbarLayout) view.findViewById(R.id.collapse_toolbar));

        //可折叠的底部导航按钮
        tabBarView = ((TabBarView) view.findViewById(R.id.tabBarView));
    }
    private void setupCollapsingToolbar() {
        collapsingToolbar.setTitleEnabled(false);
    }

    private void setupViewPager() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupToolbar() {
        context.setSupportActionBar(toolbar);
        context.getSupportActionBar().setTitle("");
//        context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(context.getSupportFragmentManager());
        adapter.addFrag(new TabFragment(), "最新活动");
        adapter.addFrag(new TabFragment(), "热门活动");

        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void setupTabView() {
        //设置主按钮图标
        tabBarView.setMainBitmap(R.mipmap.icon_plus);

        //设置菜单对应位置按钮图标及两侧图标
        tabBarView.bindBtnsForPage(0, R.mipmap.icon_event,0, 0);
        tabBarView.bindBtnsForPage(1, R.mipmap.icon_message,0, 0);
        tabBarView.bindBtnsForPage(2, R.mipmap.icon_manage,0, 0);
        tabBarView.bindBtnsForPage(3, R.mipmap.icon_contacts,0, 0);

        //设置初始默认选中
        tabBarView.initializePage(0);

        //添加监听
        tabBarView.setOnTabBarClickListener(onTabBarClickListener);
    }

    //监听回调
    private TabBarView.OnTabBarClickListener onTabBarClickListener = new TabBarView.OnTabBarClickListener() {

        @Override
        public void onMainBtnsClick(int position, int[] clickLocation) {
            //点击菜单
        }

        @Override
        public void onMainBtnsClick(int position) {
            //点击菜单
        }

        @Override
        public void onLeftBtnClick(int page) {
            //点击对应菜单的左侧按钮
        }

        @Override
        public void onRightBtnClick(int page) {
            //点击对应菜单的右侧按钮
        }

    };
}
