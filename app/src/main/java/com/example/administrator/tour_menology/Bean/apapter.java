package com.example.administrator.tour_menology.Bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/11/8 10:05
 */

public class apapter extends BaseAdapter {

    private Context mContext;
    private List<list> mList;

    public apapter(Context context, List<list> list) {
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
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder =null;
        if (view ==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item,null);
            viewHolder =new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.item_tour_title);
            viewHolder.date = (TextView) view.findViewById(R.id.item_tour_date);
            viewHolder.date2 = (TextView) view.findViewById(R.id.item_tour_date2);
            viewHolder.img = (ImageView) view.findViewById(R.id.item_tour_img);

            view.setTag(viewHolder);

        }else
        viewHolder = (ViewHolder) view.getTag();
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.date.setText(mList.get(position).getData());
        viewHolder.date2.setText(mList.get(position).getData2());
        if (mList.get(position).getImg()!=null){

            Glide.with(mContext).load(mList.get(position).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img);

        }

        if (mList.get(position).getType()=="0"){

        }






        return view;
    }

    private class ViewHolder {

        TextView title;
        TextView date;
        TextView date2;
        ImageView img;

    }


}
