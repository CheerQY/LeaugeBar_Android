package com.sicnu.cheer.generalmodule.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cheer on 2016/12/24.
 */
public class ItemDecoration extends RecyclerView.ItemDecoration {


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View item = parent.getChildAt(i);
            ViewGroup.LayoutParams layoutParams = item.getLayoutParams();
            layoutParams.height = parent.getHeight();
            item.setLayoutParams(layoutParams);
        }
    }
}
