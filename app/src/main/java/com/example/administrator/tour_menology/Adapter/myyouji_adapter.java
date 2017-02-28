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
import com.example.administrator.tour_menology.Bean.myyouji_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class myyouji_adapter extends BaseAdapter {

    private List<myyouji_list.LIstdate> mList;
    private Context mContext;

    public myyouji_adapter(List<myyouji_list.LIstdate> list, Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_myyouji,parent,false);
            holder =new ViewHolder(convertView) ;
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        Glide.with(mContext).load(mList.get(position).getLogo()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.myyouji_img);
        holder.myyouji_name.setText(mList.get(position).getTitle());
        holder.myyouji_date.setText(mList.get(position).getStartDate());
        holder.myyouji_tian.setText(mList.get(position).getDays()+"天");
        holder.myyouji_area.setText(mList.get(position).getAreaName());
        holder.myyouji_chakan.setText(mList.get(position).getBrowseCount());
        holder.myyouji_shoucang.setText(mList.get(position).getStoreCount());



        return convertView;
    }

    class ViewHolder {
        private ImageView myyouji_img;//游记图片
        private TextView myyouji_name;//游记名字
        private TextView myyouji_date;//时间
        private TextView myyouji_tian;//天数
        private TextView myyouji_area;//地点
        private TextView myyouji_chakan;//查看数
        private TextView myyouji_shoucang;//收藏数


        public ViewHolder(View convertView) {
            myyouji_img = (ImageView) convertView.findViewById(R.id.myyouji_img);
            myyouji_name = (TextView) convertView.findViewById(R.id.myyouji_name);
            myyouji_date = (TextView) convertView.findViewById(R.id.myyouji_date);
            myyouji_tian = (TextView) convertView.findViewById(R.id.myyouji_tian);
            myyouji_area = (TextView) convertView.findViewById(R.id.myyouji_area);
            myyouji_chakan = (TextView) convertView.findViewById(R.id.myyouji_chakan);
            myyouji_shoucang = (TextView) convertView.findViewById(R.id.myyouji_shoucang);

        }


    }
}
