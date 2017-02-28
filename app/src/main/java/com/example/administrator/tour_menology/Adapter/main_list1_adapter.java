package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.tour_menology.Bean.main_list1_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/7/27 10:28
 */
public class main_list1_adapter extends BaseAdapter{

    private Context mContext;
    private List<main_list1_list> mList;

    public main_list1_adapter(Context context, List<main_list1_list> list) {
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
        if ( convertView ==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_list1,null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.main_list1_title);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.main_List1_img);
            viewHolder.address = (TextView) convertView.findViewById(R.id.main_list1_address);
            viewHolder.date = (TextView) convertView.findViewById(R.id.main_list1_date);
            viewHolder.star = (TextView) convertView.findViewById(R.id.main_list1_guanzhu);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.title.setText(mList.get(position).getActName());
        viewHolder.address.setText(mList.get(position).getAddress());
        viewHolder.date.setText(mList.get(position).getActDateStr());
        viewHolder.star.setText(mList.get(position).getCollect_count());
        if (mList.get(position).getPoster() != null) {
            Glide.with(mContext).load(mList.get(position).getPoster()).into(viewHolder.img);
        }
        return convertView;
    }

    private class ViewHolder{
        TextView title;
        ImageView img;
        TextView address;
        TextView date;
        TextView star;
    }
}
