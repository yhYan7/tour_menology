package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.tour_menology.Bean.pinglun_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/8/23 14:37
 */
public class pinglunAdapter extends BaseAdapter {
    private Context mContext;
    private List <pinglun_list> mList;

    public pinglunAdapter(Context context, List<pinglun_list> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pinglun,null);

            viewHolder =new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.pinglun_name);
            viewHolder.content = (TextView) convertView.findViewById(R.id.pinglun_neirong);
            viewHolder.date = (TextView) convertView.findViewById(R.id.pinglun_date);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.pinglun_img);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.title.setText(mList.get(position).getNickName());
            viewHolder.content.setText(mList.get(position).getContent());
            viewHolder.date.setText(mList.get(position).getTime());
            Glide.with(mContext).load(mList.get(position).getHeadIcon()).into(viewHolder.img);

        }

        return convertView;
    }

    private class ViewHolder{
        ImageView img;
        TextView title;
        TextView date;
        TextView content;
    }
}
