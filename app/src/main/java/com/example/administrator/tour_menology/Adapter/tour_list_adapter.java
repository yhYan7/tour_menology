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
import com.example.administrator.tour_menology.Bean.tour_list;
import com.example.administrator.tour_menology.Main_Fragment.CalendarActivity;
import com.example.administrator.tour_menology.R;

import java.util.List;

/**
 * name：yhyan
 * date：2016/8/10 14:17
 */
public class tour_list_adapter extends BaseAdapter{
    private List<tour_list.listAct> mList;
    private Context mContext;
    private  LayoutInflater mInflater;

    public tour_list_adapter( List<tour_list.listAct> list, Context context) {
        mContext = context;
        mList = list;
        mInflater =LayoutInflater.from(mContext);
    }



    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return  mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =null;
            if (convertView==null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sour_bottom,null);
                viewHolder =new ViewHolder(convertView);
                viewHolder.title = (TextView) convertView.findViewById(R.id.item_tour_title);
      //          viewHolder.date = (TextView) convertView.findViewById(R.id.item_tour_date);
                viewHolder.img = (ImageView) convertView.findViewById(R.id.item_tour_img);

                convertView.setTag(viewHolder);
            }else
            viewHolder = (ViewHolder) convertView.getTag();

            viewHolder.title.setText(mList.get(position).getActName());
        //    viewHolder.date.setText(mList.get(position).getActDateStr());
        Glide.with(mContext).load(mList.get(position).getPoster()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img);





        return convertView;
    }

    private class ViewHolder {

        TextView title;
        TextView date;
        ImageView img;

        public ViewHolder(View convetView){
     //       date = (TextView) convetView.findViewById(R.id.item_tour_date);
            img = (ImageView) convetView.findViewById(R.id.item_tour_img);
            title = (TextView) convetView.findViewById(R.id.item_tour_title);
        }
    }

}
