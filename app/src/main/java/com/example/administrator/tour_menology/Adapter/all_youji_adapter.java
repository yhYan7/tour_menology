package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Activity.youjixiangqing;
import com.example.administrator.tour_menology.Bean.all_youji_list;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/9/21 14:42
 */
public class all_youji_adapter extends BaseAdapter {

    private List<all_youji_list.list> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public all_youji_adapter(List<all_youji_list.list> list, Context context) {
        this.mList = list;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_grid,parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.grid_title);
            viewHolder.img2 = (ImageView) convertView.findViewById(R.id.grid_img2);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.grid_img);
            viewHolder.name = (TextView) convertView.findViewById(R.id.grid_name);

            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.name.setText(mList.get(position).getNickName());
        Glide.with(mContext).load(mList.get(position).getLogo()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img);
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(mContext, youjixiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getTravelId());
                mIntent.putExtras(bundle);
                mContext.startActivity(mIntent);
            }
        });
        Glide.with(mContext).load(mList.get(position).getHeadIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img2);


        viewHolder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Toast.makeText(mContext,"为服务分为软而二哥",Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }


    private class ViewHolder {

        TextView title;
        ImageView img2;
        ImageView img;
        TextView name;


    }

}
