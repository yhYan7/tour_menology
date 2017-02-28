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
import com.example.administrator.tour_menology.Bean.myfans_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class myfans_adapter extends BaseAdapter {

    private List<myfans_list.LIstdate> mList;
    private Context mContext;

    public myfans_adapter(List<myfans_list.LIstdate> list, Context context) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_myfans,parent,false);
            holder =new ViewHolder(convertView) ;
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.myfans_name.setText(mList.get(position).getNickName());
        holder.myfans_text1.setText(mList.get(position).getFanscount());
        holder.myfans_text2.setText(mList.get(position).getAttentionCount());
        Glide.with(mContext).load(mList.get(position).getLogo()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img);


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
