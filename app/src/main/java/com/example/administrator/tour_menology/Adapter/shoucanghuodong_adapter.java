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
import com.example.administrator.tour_menology.Bean.shoucanghuodong_list;
import com.example.administrator.tour_menology.Bean.zhaohuodong_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class shoucanghuodong_adapter extends BaseAdapter {

    private List<shoucanghuodong_list.LIstdate> mList;
    private Context mContext;

    public shoucanghuodong_adapter(List<shoucanghuodong_list.LIstdate> list, Context context) {
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
        holder.gaunzhu.setText(mList.get(position).getCountCollect());
        holder.fabu.setText(mList.get(position).getAddress());
        holder.date.setText(mList.get(position).getActDate());
        Glide.with(mContext).load("http://bobtrip.com/tripcal"+mList.get(position).getImgPath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img);
        Log.i("", "====我的收藏图片-->>" + mList.get(position).getImgPath());


        return convertView;
    }

    class ViewHolder {
        private TextView title;
        private TextView fabu;
        private TextView gaunzhu;
        private ImageView img;
        private TextView date;


        public ViewHolder(View convertView) {
            title = (TextView) convertView.findViewById(R.id.main_list1_title);
            fabu = (TextView) convertView.findViewById(R.id.main_list1_address);
            gaunzhu = (TextView) convertView.findViewById(R.id.main_list1_guanzhu);
            img = (ImageView) convertView.findViewById(R.id.main_List1_img);
            date = (TextView) convertView.findViewById(R.id.main_list1_date);


        }


    }
}
