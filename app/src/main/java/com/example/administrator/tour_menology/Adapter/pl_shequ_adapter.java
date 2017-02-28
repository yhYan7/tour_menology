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
import com.example.administrator.tour_menology.Activity.main_fabu_xiangqing;
import com.example.administrator.tour_menology.Activity.main_fabufang;
import com.example.administrator.tour_menology.Activity.zhuye;
import com.example.administrator.tour_menology.Bean.pl_shequ_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.my_fragment_activity.myguanzhu;

import java.util.List;

/**
 * name：yhyan
 * date：2016/11/14 11:17
 */

public class pl_shequ_adapter extends BaseAdapter {
    private Context mContext;
    private List<pl_shequ_list>mList;

    public pl_shequ_adapter(Context context, List<pl_shequ_list> list) {
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

        ViewHolder viewHolder =null;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_pl_shequ,null);
            viewHolder =new ViewHolder();

            viewHolder.name = (TextView) convertView.findViewById(R.id.pl_name);
            viewHolder.neirong = (TextView) convertView.findViewById(R.id.pl_neirong);
            viewHolder.title = (TextView) convertView.findViewById(R.id.pl_title);

            viewHolder.r1= (RelativeLayout) convertView.findViewById(R.id.pl_RelativeLayout);
            viewHolder.r2= (RelativeLayout) convertView.findViewById(R.id.pl_RelativeLayout2);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.pl_img);
            viewHolder.img2 = (ImageView) convertView.findViewById(R.id.pl_img_fabu);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.title.setText(mList.get(position).getUnitName());
        viewHolder.neirong.setText(mList.get(position).getContent());
        viewHolder.name.setText(mList.get(position).getNickName());
        Glide.with(mContext).load( mList.get(position).getImgPath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img);
        Glide.with(mContext).load( mList.get(position).getPosterPath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img2);

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
                Intent mIntent = new Intent(mContext, main_fabu_xiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getUnitId());
                mIntent.putExtras(bundle);
                mContext.startActivity(mIntent);
            }
        });







        return convertView;
    }



    private class ViewHolder {

        TextView title;
        TextView name;
        ImageView img;
        ImageView img2;
        TextView neirong;
        RelativeLayout r1 ,r2;


    }


}
