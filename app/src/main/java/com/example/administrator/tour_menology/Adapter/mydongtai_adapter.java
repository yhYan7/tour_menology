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
import com.example.administrator.tour_menology.Bean.mydongtai_list;
import com.example.administrator.tour_menology.Bean.myfans_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class mydongtai_adapter extends BaseAdapter {

    private List<mydongtai_list.LIstdate> mList;
    private Context mContext;

    public mydongtai_adapter(List<mydongtai_list.LIstdate> list, Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dongtai,parent,false);
            holder =new ViewHolder(convertView) ;
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.mydongtai_nickname.setText(mList.get(position).getName());
        holder.mydongtai_huodongname.setText(mList.get(position).getTitle());
        holder.mydongtai_date.setText(mList.get(position).getPublishDate());
        Glide.with(mContext).load(mList.get(position).getDefaultImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img);


        return convertView;
    }

    class ViewHolder {
        private TextView mydongtai_nickname;
        private TextView mydongtai_huodongname;//粉丝数
        private TextView mydongtai_date;//关注数
        private ImageView img;


        public ViewHolder(View convertView) {
            mydongtai_nickname = (TextView) convertView.findViewById(R.id.mydongtai_nickname);
            mydongtai_huodongname = (TextView) convertView.findViewById(R.id.mydongtai_huodongname);
            mydongtai_date = (TextView) convertView.findViewById(R.id.mydongtai_date);
            img = (ImageView) convertView.findViewById(R.id.mydongtai_img);

        }


    }
}
