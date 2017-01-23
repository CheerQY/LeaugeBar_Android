package com.sicnu.cheer.leaugebar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sicnu.cheer.leaugebar.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cheer on 2017/1/23.
 * 联系人布局页面，分为常用联系人和全部联系人两项内容
 */

public class ContactsFragment extends MainBaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int setImgId() {
        return R.mipmap.contacts;
    }

    @Override
    protected Map<String, Fragment> setTitlesAndFragments() {
        Map map=new HashMap();
        map.put("常用",new FrequentContactsFragment());
        map.put("全部",new AllContactsFragment());
        return map;
    }
}
