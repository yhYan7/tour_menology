package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.tour_menology.Bean.shangcheng_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/10/12 14:59
 */

public class shangcheng_adapter extends BaseAdapter {

    private Context mContext;
    private List<shangcheng_list>mList;

    public shangcheng_adapter(Context context, List<shangcheng_list> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menpiao,null);
            viewHolder =new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.item_mp_title);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.item_mp_img);
            viewHolder.shengyu = (TextView) convertView.findViewById(R.id.item_mp_zhangshu);
            viewHolder.jifen = (TextView) convertView.findViewById(R.id.item_mp_jifen);
            convertView.setTag(viewHolder);
        }else
        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.shengyu.setText(mList.get(position).getRemainingCount());

        viewHolder.title.setText(mList.get(position).getName());

        viewHolder.jifen.setText(mList.get(position).getIntegral());

        if (mList.get(position).getPoster() != null) {
            Glide.with(mContext).load("http://bobtrip.com/tripcal"+mList.get(position).getPoster()).into(viewHolder.img);
        }


        return convertView;
    }


    private class ViewHolder{
        TextView title;
        ImageView img;
        TextView shengyu;
        TextView jifen;
    }

}
