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
import com.example.administrator.tour_menology.Bean.mypinglun_list;
import com.example.administrator.tour_menology.Bean.task_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * 任务适配器
 * Created by Administrator on 2016/11/16 0016.
 */
public class task_adapter extends BaseAdapter {

    private List<task_list.list> mList;
    private Context mContext;

    public task_adapter(List<task_list.list> list, Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task,parent,false);
            holder =new ViewHolder(convertView) ;
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

    //    Glide.with(mContext).load(mList.get(position).getImgPath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.mypinglun_img);
        holder.renwu_time.setText(mList.get(position).getRemindTime());
        holder.renwu_name.setText(mList.get(position).getTitle());

        return convertView;
    }

    class ViewHolder {
     //   private ImageView mypinglun_img;//我的头像
        private TextView renwu_time;//时间
        private TextView renwu_name;//标题



        public ViewHolder(View convertView) {
            renwu_time = (TextView) convertView.findViewById(R.id.renwu_time);
            renwu_name = (TextView) convertView.findViewById(R.id.renwu_name);


        }


    }
}