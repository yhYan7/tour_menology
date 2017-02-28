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
import com.example.administrator.tour_menology.Bean.main_fabu_list;
import com.example.administrator.tour_menology.Bean.shequdingyue_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class shequdingyue_adapter  extends BaseAdapter {
    private List<shequdingyue_list.LIstdate> mList;
    private Context mContext;

    public shequdingyue_adapter(List<shequdingyue_list.LIstdate> list, Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_fabu,parent,false);
            holder =new ViewHolder(convertView) ;
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.title.setText(mList.get(position).getUnitName());
        holder.gaunzhu.setText(mList.get(position).getCountFocus());
        holder.fabu.setText(mList.get(position).getCountPublish());
        Glide.with(mContext).load(mList.get(position).getLogo()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img);


        return convertView;
    }

    class ViewHolder {
        private TextView title;
        private TextView fabu;
        private TextView gaunzhu;
        private ImageView img;


        public ViewHolder(View convertView) {
            title = (TextView) convertView.findViewById(R.id.main_fabu_title);
            fabu = (TextView) convertView.findViewById(R.id.main_fabu_fabucs);
            gaunzhu = (TextView) convertView.findViewById(R.id.main_fabu_guanzhucs);
            img = (ImageView) convertView.findViewById(R.id.main_fabufang_img);

        }


    }


}
