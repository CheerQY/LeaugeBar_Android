package com.sicnu.cheer.leaugebar.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sicnu.cheer.leaugebar.R;
import com.sicnu.cheer.leaugebar.bean.MenuBean;

import java.util.List;


/**
 * Created by cheer on 2016/6/23.
 */
public class HomeLeftMenuAdapter extends BaseAdapter {
    private List<MenuBean> list;
    public HomeLeftMenuAdapter(List<MenuBean> list) {
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view==null){
            vh=new ViewHolder();
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_item,null);
            vh.icon= (ImageView) view.findViewById(R.id.item_image);
            vh.text= (TextView) view.findViewById(R.id.item_text);
            view.setTag(vh);
        }else {
            vh= (ViewHolder) view.getTag();
        }

        vh.text.setText(list.get(i).getText());
        if (list.get(i).getImgId()==-1){
            vh.icon.setVisibility(View.GONE);
            vh.text.setGravity(Gravity.CENTER);
            view.setBackgroundColor(viewGroup.getContext().getResources().getColor(R.color.drawer_category_transparent));
        }else {
            vh.icon.setVisibility(View.VISIBLE);
            vh.icon.setImageResource(list.get(i).getImgId());
            vh.text.setGravity(Gravity.LEFT);
            view.setBackgroundColor(viewGroup.getContext().getResources().getColor(R.color.drawer_item_transparent));
        }
        return view;
    }
    class ViewHolder{
        ImageView icon;
        TextView text;
    }
}
