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
public class youjixiangqing_adapter extends BaseAdapter {
    private Activity mContext;
    private List<youjixinagqing_list> mList;
    private youjixiangqing_adapter mAdapter;

    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;

    public youjixiangqing_adapter(Activity context, List<youjixinagqing_list> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_yjxq, null);
            viewHolder = new ViewHolder();
            viewHolder.mWebView = (ImageView) convertView.findViewById(R.id.grid_xq_webview);
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.grid_xq_text);
            viewHolder.mweb = (TextView) convertView.findViewById(R.id.grid_xq_text1);
            viewHolder.address = (TextView) convertView.findViewById(R.id.grid_xq_address);
            viewHolder.guanzhu = (TextView) convertView.findViewById(R.id.grid_xq_guanzhu);
            viewHolder.love_k = (ImageButton) convertView.findViewById(R.id.grid_xq_love_k);
            viewHolder.love_s = (ImageButton) convertView.findViewById(R.id.grid_xq_love_s);
            viewHolder.pinlun = (ImageButton) convertView.findViewById(R.id.grid_xq_pinlun);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (mList.get(position).getTravelEnjoyId().equals("")) {
            viewHolder.love_k.setVisibility(View.VISIBLE);
            viewHolder.love_s.setVisibility(View.GONE);
        } else {
            viewHolder.love_s.setVisibility(View.VISIBLE);
            viewHolder.love_k.setVisibility(View.GONE);
        }


        viewHolder.address.setText(mList.get(position).getAddress());
        viewHolder.guanzhu.setText(mList.get(position).getCountEnjoy());

        String F_URL = mList.get(position).getCont();

        viewHolder.pinlun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, youji_pinglun.class);
                Bundle bundle = new Bundle();
                bundle.putString("travelContId", mList.get(position).getTravelContId());
                bundle.putString("travelId", mList.get(position).getTravelId());
                mIntent.putExtras(bundle);
                mContext.startActivity(mIntent);
            }
        });

        if (mList.get(position).getTravelType().equals("图片")) {
            Glide.with(mContext).load("http://bobtrip.com/tripcal" + F_URL).into(viewHolder.mWebView);
            viewHolder.mTextView.setText(mList.get(position).getImgDesc());
        }else
            viewHolder.mTextView.setText(mList.get(position).getCont());

        viewHolder.love_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://bobtrip.com/tripcal/api/travel/enjoy.action";
                final Map<String, String> params = new HashMap<String, String>();
                params.put("travelId", mList.get(position).getTravelId());
                params.put("memberId", memberId);
                params.put("travelContId", mList.get(position).getTravelContId());

                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(mContext, "关注成功", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = new JSONObject(response);
                            String travelStoreId = jsonObject.getString("travelStoreId");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });


        viewHolder.love_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://bobtrip.com/tripcal/api/travel/enjoy.action";
                final Map<String, String> params = new HashMap<String, String>();
                params.put("travelId", mList.get(position).getTravelId());
                params.put("memberId", memberId);
                params.put("travelContId", mList.get(position).getTravelContId());
                //   params.put("travelEnjoyId", mList.get(position).getTravelEnjoyId());


                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(mContext, "取消成功", Toast.LENGTH_SHORT).show();

                    }
                });

                final Map<String, String> params1 = new HashMap<String, String>();
                params1.put("travelId", mList.get(position).getTravelId());
                params1.put("memberId", memberId);
                params1.put("travelContId", mList.get(position).getTravelContId());
                params1.put("travelEnjoyId", mList.get(position).getTravelEnjoyId());


                OkHttpUtils.post().url(url).params(params1).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(mContext, "取消成功", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        notifyDataSetChanged();
        return convertView;

    }


    private class ViewHolder {
        ImageView mWebView;
        TextView mweb;
        TextView mTextView;
        TextView address;
        TextView guanzhu;
        ImageButton love_k, love_s;
        ImageButton pinlun;
    }

}
