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
import com.example.administrator.tour_menology.Bean.gonglue_list_ok;
import com.example.administrator.tour_menology.Bean.shoucang_list;
import com.example.administrator.tour_menology.R;

import java.util.List;


/**
 * name：yhyan
 * date：2016/11/15 11:09
 */

public class shoucang_adapter extends BaseAdapter {

    private List<shoucang_list.listdata>mList;
    private Context mContext;

    public shoucang_adapter(List<shoucang_list.listdata> list, Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gonglue,parent,false);
            holder =new ViewHolder(convertView) ;
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.title.setText(mList.get(position).getTitle());
        holder.address.setText(mList.get(position).getArea());
        holder.date.setText(mList.get(position).getCreateDateStr());
        holder.date2.setText(mList.get(position).getDays());
        Glide.with(mContext).load("http://bobtrip.com/tripcal"+mList.get(position).getImgPath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img);

        return convertView;
    }



    class ViewHolder {
        private TextView title;
        private TextView address;
        private TextView date;
        private TextView date2;
        private ImageView img;


        public ViewHolder(View convertView) {
            title = (TextView) convertView.findViewById(R.id.gonglue_title);
            address = (TextView) convertView.findViewById(R.id.gonglue_address);
            date = (TextView) convertView.findViewById(R.id.gonglue_date);
            date2 = (TextView) convertView.findViewById(R.id.gonglue_date2);
            img = (ImageView) convertView.findViewById(R.id.gonglue_img);

        }


    }
}
