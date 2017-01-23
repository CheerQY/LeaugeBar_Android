package com.sicnu.cheer.leaugebar.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sicnu.cheer.generalmodule.util.ScreenUtils;
import com.sicnu.cheer.leaugebar.R;
import com.sicnu.cheer.leaugebar.adapter.HomeLeftMenuAdapter;
import com.sicnu.cheer.leaugebar.bean.MenuBean;
import com.sicnu.cheer.leaugebar.fragment.ContactsFragment;
import com.sicnu.cheer.leaugebar.fragment.EventFragment;
import com.srx.widget.TabBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    private MainActivity mThis;
    private List<MenuBean> list;
    private HomeLeftMenuAdapter adapter;
    private EventFragment eventFragment;
    private ContactsFragment contactsFragment;

    //views
    @InjectView(R.id.home_fl)
    FrameLayout frameLayout;
    @InjectView(R.id.tabBarView)
    TabBarView tabBarView;//可折叠的底部导航按钮


    private ListView listView;
    private SlidingMenu slidingMenu;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThis = this;
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
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

        //设置底部导航按钮
//        tabBarView = ((TabBarView) findViewById(R.id.tabBarView));
        setupTabView();
    }

    /**
     * 切换Fragment操作
     *
     * @param tabSelection
     */
    public void setTabSelection(int tabSelection) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (tabSelection) {
            case 0:
                if (eventFragment == null) {
                    eventFragment = new EventFragment();
                    transaction.add(R.id.home_fl, eventFragment);
                } else {
                    transaction.show(eventFragment);
                }
                break;
            case 1:
                if (contactsFragment == null) {
                    contactsFragment = new ContactsFragment();
                    transaction.add(R.id.home_fl, contactsFragment);
                } else {
                    transaction.show(contactsFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏所有的fragments
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (eventFragment != null) {
            transaction.hide(eventFragment);
        }
        if (contactsFragment != null) {
            transaction.hide(contactsFragment);
        }
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
        slidingMenu.setBehindOffset(width / 4);
    }

    public void showMenu(View view) {
        if (slidingMenu != null) {
            slidingMenu.toggle();
        }
    }

    private void setupTabView() {
        //设置主按钮图标
        tabBarView.setMainBitmap(R.mipmap.icon_plus);

        //设置菜单对应位置按钮图标及两侧图标
        tabBarView.bindBtnsForPage(0, R.mipmap.icon_event, 0, 0);
        tabBarView.bindBtnsForPage(1, R.mipmap.icon_message, 0, 0);
        tabBarView.bindBtnsForPage(2, R.mipmap.icon_manage, 0, 0);
        tabBarView.bindBtnsForPage(3, R.mipmap.icon_contacts, 0, 0);

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
            setTabSelection(position);
        }

        @Override
        public void onMainBtnsClick(int position) {
            //点击菜单
            setTabSelection(position);
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

    @Override
    public void onBackPressed() {
        if (slidingMenu.isMenuShowing()) {
            slidingMenu.toggle();
        } else {
            super.onBackPressed();
        }
    }

}
