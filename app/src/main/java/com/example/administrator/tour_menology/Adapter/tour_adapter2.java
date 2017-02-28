package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.tour_menology.Bean.tour_List2;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/9/5 10:14
 */
public class tour_adapter2 extends BaseAdapter {

    private Context mContext;
    private List<tour_List2> mList;

    public tour_adapter2(Context context, List<tour_List2> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sour_bottom,null );
            viewHolder =new ViewHolder();

            viewHolder.title = (TextView) convertView.findViewById(R.id.item_tour_title);
            viewHolder.date = (TextView) convertView.findViewById(R.id.item_tour_date);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.item_tour_img);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.title.setText(mList.get(position).getActName());
            viewHolder.date.setText(mList.get(position).getEndTime());
            if (mList.get(position).getPoster() != null) {
                Glide.with(mContext).load(mList.get(position).getPoster()).into(viewHolder.img);
            }

        return convertView;
    }

    private class ViewHolder{
        TextView title;
        ImageView img;
        TextView date;
    }

}
