package com.sicnu.cheer.leaugebar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sicnu.cheer.leaugebar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by cheer on 2017/1/23.
 * 主页面的基类Fragment
 */

public abstract class MainBaseFragment extends BaseFragment {
    @InjectView(R.id.header)
    ImageView header;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapseToolbar;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;

    private AppCompatActivity context;
    private Map<String, Fragment> titlesAndFragments;
    private int headerImgId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity){
            this.context= (AppCompatActivity) context;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.inject(this,mRootView);
        setupToolbar();
        setupViewPager();
        setupCollapsingToolbar();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_base;
    }

    private void setupCollapsingToolbar() {
        collapseToolbar.setTitleEnabled(false);
    }

    private void setupViewPager() {
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);
    }

    private void setupToolbar() {
        context.setSupportActionBar(toolbar);
        ActionBar actionBar = context.getSupportActionBar();
        actionBar.setTitle("");
//        context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        headerImgId = setImgId();
        header.setImageResource(headerImgId);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(context.getSupportFragmentManager());
        titlesAndFragments = setTitlesAndFragments();

        //设置标题和绑定fragment
        String title;
        Fragment frag;
        for (Map.Entry entry : titlesAndFragments.entrySet()) {
            title = (String) entry.getKey();
            frag = (Fragment) entry.getValue();
            adapter.addFrag(frag, title);
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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

    /**
     * 设置可折叠图片的资源id
     *
     * @return
     */
    protected abstract int setImgId();

    /**
     * 设置可折叠图片的资源id
     *
     * @return
     */
    protected abstract Map<String, Fragment> setTitlesAndFragments();
}
