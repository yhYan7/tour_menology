package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.tour_menology.Bean.fabu_huodong_list;
import com.example.administrator.tour_menology.Fragment.fabu_huodong_Fragment;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/8/22 10:21
 */
public class fabu_huodong_adapter extends BaseAdapter {

    private List<fabu_huodong_list.list>mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public fabu_huodong_adapter(List<fabu_huodong_list.list> list, Context context) {
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
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
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_main_list1, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.main_list1_title);
            holder.img = (ImageView) convertView.findViewById(R.id.main_List1_img);
            holder.address = (TextView) convertView.findViewById(R.id.main_list1_address);
            holder.date = (TextView) convertView.findViewById(R.id.main_list1_date);
            holder.star = (TextView) convertView.findViewById(R.id.main_list1_guanzhu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(mList.get(position).getActName());
        holder.address.setText(mList.get(position).getAddress());
        holder.date.setText(mList.get(position).getActDateStr());
   //     holder.star.setText(mList.get(position).getCollect_count());

        Glide.with(mContext).load(mList.get(position).getPoster()).into(holder.img);
        return convertView;
    }

    class ViewHolder {
        private TextView title;
        private TextView address;
        private TextView date;
        private ImageView img;
        TextView star;
    }
}
