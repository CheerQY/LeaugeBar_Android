package com.sicnu.cheer.leaugebar.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.sicnu.cheer.leaugebar.R;

/**
 * Created by cheer on 2016/7/6.
 */
public class GuideTraslatorPageTransform implements ViewPager.PageTransformer {
    /**
     * 抽象方法
     * @param page 当前页面
     * @param position (-1~1)
     */
    @Override
    public void transformPage(View page, float position) {
        //监听页面的滑动事件，实现视差动画
        if (position<1&&position>-1){
            //1、实现视差动画,白色手机框框背景，top_phone_container
            //2、部分内部的图标进行位移动画
            ViewGroup rl= (ViewGroup) page.findViewById(R.id.rl);
            for (int i=0;i<rl.getChildCount();i++){
                //遍历子控件，进行内部图标动画，进行位移动画
                View child=rl.getChildAt(i);
                //设置左右位移的距离
                float factor= (float) (Math.random()*2);
                //随机数只随机一次
                if (child.getTag()==null){
                    //说明还没有随机数
                    child.setTag(factor);
                }else {
                    factor= (float) child.getTag();
                }
                if (child.getId()==R.id.top_phone_container){
                    //说明是白色的框框
                    continue;
                }
                //进行位移动画
                child.setTranslationX(position*factor*child.getWidth());
            }
            //白色框框进行动画,进行缩小动画
            ViewGroup top_phone_container= (ViewGroup) page.findViewById(R.id.top_phone_container);
            //position 0~1
            top_phone_container.setScaleX(Math.max(0.6f,1- Math.abs(position)));
            top_phone_container.setScaleY(Math.max(0.6f,1- Math.abs(position)));
            for (int i=0;i<top_phone_container.getChildCount();i++){
                //框框里面的位移动画
                View child=top_phone_container.getChildAt(i);
                float factor= (float) (1+ Math.random());
                if (child.getTag()==null){
                    child.setTag(factor);
                }else {
                    factor= (float) child.getTag();
                }
                if (i%2==0) {
                    child.setTranslationX(position * factor * child.getWidth());
                }else {
                    child.setTranslationX(-position*factor*child.getWidth());
                }

            }
            //实现3D翻转效果
//            page.setPivotX(position<0?page.getWidth():0);
//            page.setPivotY(page.getHeight()*0.5f);
//            page.setRotation(position*90f);
//            page.setRotationX(position*90f);
//            page.setRotationY(position*90f);
//            page.setRotationY(-position*60f);
        }
    }
}
