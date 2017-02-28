package com.example.administrator.tour_menology.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Bean.friend_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * name：yhyan
 * date：2016/11/16 13:59
 */

public class friend_adapter extends BaseAdapter {

    private List<friend_list> mList;
    private Context mContext;

    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private ArrayAdapter mAdapter;


    public friend_adapter(List<friend_list> list, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        utils = new PreferenceUtils(mContext);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();

//        mAdapter=new ArrayAdapter()

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_haoyou, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.item_haoyou_name);
            viewHolder.deng = (TextView) convertView.findViewById(R.id.item_haoyou_deng);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.item_haoyou_img);
            viewHolder.right = (ImageView) convertView.findViewById(R.id.item_haoyou_right);
            viewHolder.mButton = (Button) convertView.findViewById(R.id.item_haoyou_tongyi);

            convertView.setTag(viewHolder);

        }
        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.name.setText(mList.get(position).getNickName());
        Glide.with(mContext).load(mList.get(position).getHeadIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.img);

        if (String.valueOf(mList.get(position).getAddStatus()).equals("成功")) {
            viewHolder.deng.setVisibility(View.GONE);
            viewHolder.right.setVisibility(View.VISIBLE);
            viewHolder.mButton.setVisibility(View.GONE);
        } else if (String.valueOf(mList.get(position).getAddStatus()).equals("请求")) {
            viewHolder.right.setVisibility(View.GONE);
            viewHolder.deng.setVisibility(View.VISIBLE);
            viewHolder.mButton.setVisibility(View.GONE);
        } else {
            viewHolder.right.setVisibility(View.GONE);
            viewHolder.deng.setVisibility(View.GONE);
            viewHolder.mButton.setVisibility(View.VISIBLE);
        }

        viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://bobtrip.com/tripcal/api/addressbook/agree.action?memberId=" + memberId + "&inviterId=" + mList.get(position).getInviterId();

                OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();

                    }
                });

                mAdapter.notifyDataSetChanged();

            }
        });


        return convertView;
    }

    private class ViewHolder {
        Button mButton;
        TextView name;
        TextView deng;
        ImageView img;
        ImageView right;

    }



}
