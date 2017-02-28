package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.tour_menology.Bean.tahuodong_list;

import java.util.List;

/**
 * name：yhyan
 * date：2016/10/31 10:37
 */

public class tahuodong_adapter extends BaseAdapter {

    private Context mContext;
    private List<tahuodong_list>mList;

    public tahuodong_adapter(Context context, List<tahuodong_list> list) {
        mContext = context;
        mList = list;
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
        return null;
    }
}
