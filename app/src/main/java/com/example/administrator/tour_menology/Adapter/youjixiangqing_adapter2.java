package com.example.administrator.tour_menology.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.tour_menology.Activity.youji_pinglun;
import com.example.administrator.tour_menology.Bean.youjixinagqing_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * name：yhyan
 * date：2016/9/13 16:06
 */
public class youjixiangqing_adapter2 extends BaseAdapter {
    private Activity mContext;
    private List<youjixinagqing_list> mList;

    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;

    public youjixiangqing_adapter2(Activity context, List<youjixinagqing_list> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        utils = new PreferenceUtils(mContext);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();


        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bianjiyj, null);
            viewHolder = new ViewHolder();
            viewHolder.mWebView = (ImageView) convertView.findViewById(R.id.yjxq2_img);
            viewHolder.title = (TextView) convertView.findViewById(R.id.yjxq2_text);
            viewHolder.date = (TextView) convertView.findViewById(R.id.yjxq2_date);
            viewHolder.date2 = (TextView) convertView.findViewById(R.id.yjxq2_date2);
            viewHolder.address = (TextView) convertView.findViewById(R.id.yjxq2_address);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.date.setText(mList.get(position).getDays());
        viewHolder.date2.setText(mList.get(position).getTraveldate());
        if (viewHolder.address != null) {
            viewHolder.address.setText(mList.get(position).getAddress());
        }
        String F_URL = mList.get(position).getCont();

        if (mList.get(position).getTravelType().equals("图片")) {
            Glide.with(mContext).load("http://bobtrip.com/tripcal" + F_URL).into(viewHolder.mWebView);
            viewHolder.title.setText(mList.get(position).getImgDesc());
        } else
            viewHolder.title.setText(mList.get(position).getCont());


        return convertView;

    }


    private class ViewHolder {
        ImageView mWebView;
        TextView date, date2;
        TextView title;
        TextView address;


    }

}
