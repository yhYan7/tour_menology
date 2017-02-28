package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.tour_menology.Bean.zan_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/9/20 16:03
 */
public class zan_adapter extends BaseAdapter {

    private Context mContext;
    private List<zan_list>mList;

    public zan_adapter(Context context, List<zan_list> list) {
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

        ViewHolder viewHolder =null;

        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_yjpl,null);
            viewHolder =new ViewHolder();
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.item_yjpl_img);
            convertView.setTag(viewHolder);
        }else
        viewHolder = (ViewHolder) convertView.getTag();

        Glide.with(mContext).load("http://bobtrip.com/tripcal"+mList.get(position).getHeadIcon()).into(viewHolder.mImageView);
        return convertView;
    }


    private class ViewHolder {
        ImageView mImageView;
    }
}
