package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.administrator.tour_menology.Bean.zhoubian_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/10/12 8:48
 */

public class zhoubian_adapter extends BaseAdapter {

    private Context mContext;
    private List<zhoubian_list> mList;

    public zhoubian_adapter(Context context, List<zhoubian_list> list) {
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
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_zhoubian,null);
            viewHolder =new ViewHolder();
            viewHolder.img= (ImageView) convertView.findViewById(R.id.zhoubian_img);
            viewHolder.title= (TextView) convertView.findViewById(R.id.zhoubian_title);
            viewHolder.yuan= (TextView) convertView.findViewById(R.id.zhoubian_yuan);
            viewHolder.xian= (TextView) convertView.findViewById(R.id.zhoubian_xian);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();

        if (mList.get(position).getDefaulrPhoto() != null) {
            Glide.with(mContext).load(mList.get(position).getDefaulrPhoto()).into(viewHolder.img);
        }
        viewHolder.title.setText(mList.get(position).getNameCn());
        viewHolder.yuan.setText(mList.get(position).getMarketPrice());
        viewHolder.xian.setText(mList.get(position).getMinPrice());


        return convertView;
    }


    private class ViewHolder{
        TextView title;
        ImageView img;
        TextView yuan;
        TextView xian;
    }

}
