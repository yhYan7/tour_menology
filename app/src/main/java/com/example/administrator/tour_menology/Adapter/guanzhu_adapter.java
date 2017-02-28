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
import com.example.administrator.tour_menology.Bean.guanzhu_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/10/28 13:17
 */

public class guanzhu_adapter extends BaseAdapter {

    private List<guanzhu_list>mList;
    private Context mContext;

    public guanzhu_adapter(List<guanzhu_list> list, Context context) {
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
        if (convertView ==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_myfans,null);
            viewHolder =new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else
        viewHolder = (ViewHolder) convertView.getTag();



        viewHolder.myfans_name.setText(mList.get(position).getNickName());
        viewHolder.myfans_text1.setText(mList.get(position).getCountFans());
        viewHolder.myfans_text2.setText(mList.get(position).getCountAttention());
        Glide.with(mContext).load("http://bobtrip.com/tripcal"+mList.get(position).getHeadIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img);


        return convertView;
    }


    class ViewHolder {
        private TextView myfans_name;
        private TextView myfans_text1;//粉丝数
        private TextView myfans_text2;//关注数
        private ImageView img;


        public ViewHolder(View convertView) {
            myfans_name = (TextView) convertView.findViewById(R.id.myfans_name);
            myfans_text1 = (TextView) convertView.findViewById(R.id.myfans_text1);
            myfans_text2 = (TextView) convertView.findViewById(R.id.myfans_text2);
            img = (ImageView) convertView.findViewById(R.id.myfans_img);

        }


    }

}
