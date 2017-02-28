package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Bean.add_friend_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * name：yhyan
 * date：2016/11/17 10:46
 */

public class add_friend_adapter extends BaseAdapter {
    private List<add_friend_list> mListl;
    private Context mContext;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;

    public add_friend_adapter(List<add_friend_list> listl, Context context) {
        mListl = listl;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mListl.size();
    }

    @Override
    public Object getItem(int position) {
        return mListl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        //获取登录用户信息
        utils = new PreferenceUtils(mContext);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();


        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_add_friend, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.item_add_friend_name);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.item_add_friend_img);
            viewHolder.mButton = (Button) convertView.findViewById(R.id.item_add_friend_btn);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.name.setText(mListl.get(position).getNickName());
        Glide.with(mContext).load(mListl.get(position).getHeadIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img);

        viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListl.get(position).getMemberId().toString().equals(memberId)){
                    Toast.makeText(mContext,"不能添加自己为好友哦",Toast.LENGTH_SHORT).show();
                }else {

                    String url ="http://bobtrip.com/tripcal/api/addressbook/add.action?memberId="+memberId+"&inviterId="+mListl.get(position).getMemberId();
                    OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(mContext,"申请成功",Toast.LENGTH_SHORT).show();

                        }
                    });


                }



            }
        });

        return convertView;
    }

    private class ViewHolder {
        Button mButton;
        TextView name;
        ImageView img;
    }


}
