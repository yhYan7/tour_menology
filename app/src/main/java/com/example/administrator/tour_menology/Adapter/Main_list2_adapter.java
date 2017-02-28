package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.tour_menology.Bean.main_list2_List;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 * 创建人：yhyan
 * 创建时间：2016/7/26 11:58
 */
public class Main_list2_adapter extends BaseAdapter {
    private Context mContext;
    private List<main_list2_List> mList;

    public Main_list2_adapter(Context context, List<main_list2_List> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_hangye, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_main_hangye_title);
          /*  viewHolder.address = (TextView) convertView.findViewById(R.id.tv_main_hangye_laiyuan);
            viewHolder.date = (TextView) convertView.findViewById(R.id.tv_main_hangye_date);*/
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.title.setText(mList.get(position).getTitle());
     /*   viewHolder.address.setText(mList.get(position).getSource());
        viewHolder.date.setText(mList.get(position).getPubTime());*/


        return convertView;
    }


    private class ViewHolder {
        TextView date;
        TextView title;
        TextView address;
    }
}
