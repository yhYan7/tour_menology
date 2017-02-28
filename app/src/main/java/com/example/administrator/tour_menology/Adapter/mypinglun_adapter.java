package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Activity.zhuye;
import com.example.administrator.tour_menology.Bean.myfans_list;
import com.example.administrator.tour_menology.Bean.mypinglun_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class mypinglun_adapter extends BaseAdapter {

    private List<mypinglun_list.LIstdate> mList;
    private Context mContext;

    public mypinglun_adapter(List<mypinglun_list.LIstdate> list, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_mypinglun,parent,false);
            holder =new ViewHolder(convertView) ;
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        Glide.with(mContext).load(mList.get(position).getImgPath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.mypinglun_img);
        holder.my_name.setText(mList.get(position).getNickName());
        holder.pinglun_neirong.setText(mList.get(position).getContent());
        Glide.with(mContext).load(mList.get(position).getPosterPath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.huodong_img);
        holder.pinglun_huodongname.setText(mList.get(position).getActName());
        holder.pinglun_date.setText(mList.get(position).getActDateStr());

        holder.huodong_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext, zhuye.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getMemberId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }

    class ViewHolder {
        private ImageView mypinglun_img;//我的头像
        private TextView my_name;//我的名字
        private TextView pinglun_neirong;//评论内容
        private ImageView huodong_img;//活动图片
        private TextView pinglun_huodongname;//活动名称
        private TextView pinglun_date;//活动时间


        public ViewHolder(View convertView) {
            mypinglun_img = (ImageView) convertView.findViewById(R.id.mypinglun_img);
            my_name = (TextView) convertView.findViewById(R.id.my_name);
            pinglun_neirong = (TextView) convertView.findViewById(R.id.pinglun_neirong);
            huodong_img = (ImageView) convertView.findViewById(R.id.huodong_img);
            pinglun_huodongname = (TextView) convertView.findViewById(R.id.pinglun_huodongname);
            pinglun_date = (TextView) convertView.findViewById(R.id.pinglun_date);

        }


    }
}
