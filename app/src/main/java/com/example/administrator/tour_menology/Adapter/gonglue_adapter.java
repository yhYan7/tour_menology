package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Bean.gonglue_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/9/10 8:50
 */
public class gonglue_adapter extends BaseAdapter {

    private Context mContext;
    private List<gonglue_list> mList;

    public gonglue_adapter(Context context, List<gonglue_list> list) {
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

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gonglue, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.gonglue_title);
            viewHolder.date = (TextView) convertView.findViewById(R.id.gonglue_date);
            viewHolder.date2 = (TextView) convertView.findViewById(R.id.gonglue_date2);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.gonglue_img);
            viewHolder.address = (TextView) convertView.findViewById(R.id.gonglue_address);

            convertView.setTag(viewHolder);

        }

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.date.setText(mList.get(position).getPublishDate());
        viewHolder.date2.setText(mList.get(position).getDays());
        viewHolder.address.setText(mList.get(position).getAreaName());
        Glide.with(mContext).load(mList.get(position).getLogo()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img);

        return convertView;
    }


    private class ViewHolder {

        TextView title;
        TextView date;
        ImageView img;
        TextView date2;
        TextView address;

    }

}
