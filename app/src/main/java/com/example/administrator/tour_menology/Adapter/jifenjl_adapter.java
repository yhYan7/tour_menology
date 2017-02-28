package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.tour_menology.Bean.jinfenjl_list;
import com.example.administrator.tour_menology.R;

import java.util.List;


/**
 * name：yhyan
 * date：2016/11/11 16:08
 */

public class jifenjl_adapter extends BaseAdapter {

    private List<jinfenjl_list> mList;
    private Context mContext;

    public jifenjl_adapter(List<jinfenjl_list> list, Context context) {
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
        ViewHolder viewHolder =null;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.jifenjl_item,null);
            viewHolder =new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.item_jifen_title);
            viewHolder.date = (TextView) convertView.findViewById(R.id.item_jifen_date);
            viewHolder.fen = (TextView) convertView.findViewById(R.id.item_jifen_jifen);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();

        viewHolder.title.setText(mList.get(position).getModuleName());
        viewHolder.date.setText(mList.get(position).getCreateDateStr());
        viewHolder.fen.setText(mList.get(position).getPoint());


        return convertView;
    }
    private class ViewHolder {

        TextView title;
        TextView date;
        TextView fen;

    }



}
