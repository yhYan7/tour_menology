package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Bean.shijian_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18 0018.
 */
public class act_adapter extends BaseAdapter {

    private List<shijian_list.listAct> mList;
    private Context mContext;

    public act_adapter(List<shijian_list.listAct> list, Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_list1,parent,false);
            holder =new ViewHolder(convertView) ;
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.title.setText(mList.get(position).getActName());
        holder.fabu.setText(mList.get(position).getAddress());
        holder.date.setText(mList.get(position).getActDateStr());
        Glide.with(mContext).load(mList.get(position).getPoster()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img);



        return convertView;
    }

    class ViewHolder {
        private TextView title;
        private TextView fabu;
        private TextView gaunzhu;
        private ImageView img,imageView5;
        private TextView date;



        public ViewHolder(View convertView) {
            title = (TextView) convertView.findViewById(R.id.main_list1_title);
            fabu = (TextView) convertView.findViewById(R.id.main_list1_address);
            gaunzhu = (TextView) convertView.findViewById(R.id.main_list1_guanzhu);
            img = (ImageView) convertView.findViewById(R.id.main_List1_img);
            date = (TextView) convertView.findViewById(R.id.main_list1_date);
            imageView5 = (ImageView) convertView.findViewById(R.id.imageView5);
            imageView5.setVisibility(convertView.GONE);
            gaunzhu.setVisibility(convertView.GONE);


        }


    }
}
