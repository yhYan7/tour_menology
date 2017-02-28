package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Activity.tadeyouji;
import com.example.administrator.tour_menology.Activity.youji_pinglun;
import com.example.administrator.tour_menology.Activity.zhuye;
import com.example.administrator.tour_menology.Bean.pl_youji_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/11/14 14:51
 */

public class pl_youji_adapter extends BaseAdapter {

    private Context mContext;
    private List<pl_youji_list> mList;

    public pl_youji_adapter(Context context, List<pl_youji_list> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pl_youji, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.pl_youji_name);
            viewHolder.date = (TextView) convertView.findViewById(R.id.pl_youji_date);
            viewHolder.address = (TextView) convertView.findViewById(R.id.pl_youji_address);
            viewHolder.title = (TextView) convertView.findViewById(R.id.pl_youji_title);
            viewHolder.date2 = (TextView) convertView.findViewById(R.id.pl_youji_date2);
            viewHolder.neirong = (TextView) convertView.findViewById(R.id.pl_youji_neirong);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.pl_youji_img);
            viewHolder.img2 = (ImageView) convertView.findViewById(R.id.pl_youji_img2);
            viewHolder.r1 = (RelativeLayout) convertView.findViewById(R.id.pl_R1);
            viewHolder.r2 = (RelativeLayout) convertView.findViewById(R.id.pl_R2);

            convertView.setTag(viewHolder);

        }
        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.name.setText(mList.get(position).getNickName());
        viewHolder.date.setText(mList.get(position).getStartDate());
        viewHolder.date2.setText(mList.get(position).getDays());
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.address.setText(mList.get(position).getAreaName());
        viewHolder.neirong.setText(mList.get(position).getContent());


        Glide.with(mContext).load(mList.get(position).getImgPath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img);
        Glide.with(mContext).load(mList.get(position).getPosterPath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img2);

        viewHolder.r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext, zhuye.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getMemberId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        viewHolder.r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, youji_pinglun.class);
                Bundle bundle = new Bundle();
                bundle.putString("travelContId", mList.get(position).getTravelContId());
              //  bundle.putString("travelId", mList.get(position).getTravelId());
                mIntent.putExtras(bundle);
                mContext.startActivity(mIntent);
            }
        });




        return convertView;
    }


    private class ViewHolder {

        TextView title;
        TextView date;
        TextView date2;
        TextView address;
        TextView name;
        ImageView img;
        ImageView img2;
        TextView neirong;
        RelativeLayout r1, r2;


    }

}
