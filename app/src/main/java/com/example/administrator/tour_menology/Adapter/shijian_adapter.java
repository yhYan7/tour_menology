package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.tour_menology.Bean.shijian_list;
import com.example.administrator.tour_menology.Bean.task_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class shijian_adapter extends BaseAdapter {

    private List<shijian_list.listEvent> mList;
    private Context mContext;

    public shijian_adapter(List<shijian_list.listEvent> list, Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shijian,parent,false);
            holder =new ViewHolder(convertView) ;
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        //    Glide.with(mContext).load(mList.get(position).getImgPath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.mypinglun_img);
        holder.shijian_time.setText(mList.get(position).getStartTime());
        holder.shijian_name.setText(mList.get(position).getTitle());

        return convertView;
    }

    class ViewHolder {
        //   private ImageView mypinglun_img;//我的头像
        private TextView shijian_time;//时间
        private TextView shijian_name;//标题



        public ViewHolder(View convertView) {
            shijian_time = (TextView) convertView.findViewById(R.id.shijian_time);
            shijian_name = (TextView) convertView.findViewById(R.id.shijian_name);


        }


    }
}