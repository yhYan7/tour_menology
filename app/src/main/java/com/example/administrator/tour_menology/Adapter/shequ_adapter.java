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
import com.example.administrator.tour_menology.Bean.shequ_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/10/28 10:24
 */

public class shequ_adapter extends BaseAdapter {
    private Context mContext;
    private List<shequ_list>mList;


    public shequ_adapter(Context context, List<shequ_list> list) {
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

        if (convertView ==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_fabu,null);
            viewHolder =new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.main_fabu_title);
            viewHolder.huodong = (TextView) convertView.findViewById(R.id.main_fabu_fabucs);
            viewHolder.dingyue = (TextView) convertView.findViewById(R.id.main_fabu_guanzhucs);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.main_fabufang_img);


            convertView.setTag(viewHolder);

        }

        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.title.setText(mList.get(position).getUnitName());
        viewHolder.dingyue.setText(mList.get(position).getCountFocus());
        viewHolder.huodong.setText(mList.get(position).getCountPublish());
        Glide.with(mContext).load(mList.get(position).getLogo()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img);


        return convertView;

    }


    private class ViewHolder {

        TextView title;
        TextView huodong;
        ImageView img;
        TextView dingyue;

    }

}
