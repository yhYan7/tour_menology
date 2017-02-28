package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Bean.xihuan_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/10/27 15:33
 */

public class xihuan_adapter extends BaseAdapter {
    private Context mContext;
    private List<xihuan_list>mList;

    public xihuan_adapter(Context context, List<xihuan_list> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_img,null);
            viewHolder =new ViewHolder();
            viewHolder.img= (ImageView) convertView.findViewById(R.id.item_img);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();

        Glide.with(mContext).load(mList.get(position).getImgPath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img);

        return convertView;
    }


    private class ViewHolder {
        ImageView img;
    }
}
