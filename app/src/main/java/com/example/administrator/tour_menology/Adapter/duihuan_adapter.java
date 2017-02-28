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
import com.example.administrator.tour_menology.Bean.duihuan_list;
import com.example.administrator.tour_menology.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * name：yhyan
 * date：2016/10/20 9:54
 */

public class duihuan_adapter extends BaseAdapter {

    private Context mContext;
    private List<duihuan_list> mList;

    public duihuan_adapter(Context context, List<duihuan_list> list) {
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

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.duihaunjl_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.item_duihuan_title);
            viewHolder.date = (TextView) convertView.findViewById(R.id.item_duihuan_date);
            viewHolder.jifen = (TextView) convertView.findViewById(R.id.item_duihuan_jifen);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.item_duihuan_img);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.title.setText(mList.get(position).getName());


        viewHolder.date.setText(mList.get(position).getDate());
        viewHolder.jifen.setText(mList.get(position).getIntegral());

        Glide.with(mContext).load("http://bobtrip.com/tripcal" + mList.get(position).getPoster()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img);


        return convertView;
    }


    private class ViewHolder {

        TextView title;
        TextView date;
        ImageView img;
        TextView jifen;


    }

}
