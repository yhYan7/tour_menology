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
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/9/10 13:49
 */
public class gonglue_adapter_ok extends BaseAdapter {

    private List<gonglue_list_ok.LIstdate>mList;
    private Context mContext;

    public gonglue_adapter_ok(List<gonglue_list_ok.LIstdate> list, Context context) {
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
        holder.address.setText(mList.get(position).getAreaName());
        holder.date.setText(mList.get(position).getPublishDate());
        holder.date2.setText(mList.get(position).getDays());
        Glide.with(mContext).load(mList.get(position).getLogo()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img);


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
