package com.sicnu.cheer.leaugebar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.sicnu.cheer.leaugebar.R;

/**
 * Created by cheer on 2016/7/6.
 */
public class GuideFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle=this.getArguments();
        int layoutId=bundle.getInt("layout");//获取布局文件Id
        View view=inflater.inflate(layoutId,null);
        Button start= (Button) view.findViewById(R.id.start_btn);
        if (start!=null){
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //跳转到主界面
                    Toast.makeText(getActivity(), "开始主界面", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }
}
