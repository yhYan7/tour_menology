package com.example.administrator.tour_menology.Bean;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/11/2 14:24
 */

public class rili_adapter extends BaseAdapter {

    private List<rili_list> mList;
    private Context mContext;

    public rili_adapter(List<rili_list> list, Context context) {
        mList = list;
        mContext = context;
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

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_rili, null);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView) convertView.findViewById(R.id.item_rili_date);
            viewHolder.date2 = (TextView) convertView.findViewById(R.id.item_rili_date2);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.item_rili_img);
            viewHolder.title = (TextView) convertView.findViewById(R.id.item_rili_title);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();


        viewHolder.title.setText(mList.get(position).getActName());
        viewHolder.date.setText(mList.get(position).getStartTime());
        viewHolder.date2.setText(mList.get(position).getEndTime());
        Glide.with(mContext).load(mList.get(position).getPoster()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img);


        return convertView;
    }

    private class ViewHolder {

        TextView title;
        TextView date;
        TextView date2;
        ImageView img;

    }


}
