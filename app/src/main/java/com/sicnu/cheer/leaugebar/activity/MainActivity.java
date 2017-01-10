package com.sicnu.cheer.leaugebar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sicnu.cheer.generalmodule.util.ScreenUtils;
import com.sicnu.cheer.im.activity.ConversationActivity;
import com.sicnu.cheer.leaugebar.R;
import com.sicnu.cheer.leaugebar.adapter.HomeLeftMenuAdapter;
import com.sicnu.cheer.leaugebar.bean.MenuBean;
import com.sicnu.cheer.leaugebar.fragment.HomeFragment;
import com.sicnu.cheer.leaugebar.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    private MainActivity mThis;
    private List<MenuBean> list;
    private HomeLeftMenuAdapter adapter;
    private HomeFragment homeFragment;

    //views
    @InjectView(R.id.home_fl)
    FrameLayout frameLayout;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;


    private ListView listView;
    private SlidingMenu slidingMenu;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThis = this;
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        fragmentManager=getSupportFragmentManager();
        setTabSelection(0);
    }

    public void initView() {
        fillListData();
        View view = LayoutInflater.from(this).inflate(R.layout.home_menu, null);
        listView = (ListView) view.findViewById(R.id.menu_lv);
        adapter = new HomeLeftMenuAdapter(list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {

                }
            }

        });
        setSlidingMenu(view);
    }
    /**
     * 切换Fragment操作
     * @param tabSelection
     */
    public void setTabSelection(int tabSelection) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (tabSelection) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.home_fl, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
//                if (teamHistory == null) {
//                    teamHistory = new TeamHistoryFragment();
//                    transaction.add(R.id.fragment_content, teamHistory);
//                } else {
//                    transaction.show(teamHistory);
//                }
                break;
            default:
                break;
        }
        transaction.commit();
    }
    /**
     * 隐藏所有的fragments
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
//        if (teamHistory != null) {
//            transaction.hide(teamHistory);
//        }
    }
    //填充菜单ListView中的list
    private void fillListData() {
        list = new ArrayList<>();
        int imageIds[] = {-1, R.mipmap.share_set, R.mipmap.switch_org,
                -1, R.mipmap.suggesstion, R.mipmap.version_set, R.mipmap.about_set,};
        String content[] = {"分享", "推荐", "切换社团",
                "联系与反馈", "意见建议", "检查更新", "关于我们"};
        for (int i = 0; i < content.length; i++) {
            MenuBean drawerBean = new MenuBean();
            drawerBean.setImgId(imageIds[i]);
            drawerBean.setText(content[i]);
            list.add(drawerBean);
        }
    }

    /**
     * 设置slidingmenu的一些属性
     */
    public void setSlidingMenu(View view) {
        int width = ScreenUtils.getScreenWidth(mThis);

        slidingMenu = new SlidingMenu(this);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(view);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(width / 3);
    }

    public void showMenu(View view) {
        if (slidingMenu != null) {
            slidingMenu.toggle();
        }
    }

    @Override
    public void onBackPressed() {
        if (slidingMenu.isMenuShowing()) {
            slidingMenu.toggle();
        } else {
            super.onBackPressed();
        }
    }
}
