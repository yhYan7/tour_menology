package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.tour_menology.Adapter.guanzhu_adapter;
import com.example.administrator.tour_menology.Bean.guanzhu_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class fensi extends AppCompatActivity {


    private ImageButton toolbar;
    private TextView toolbart;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private List<guanzhu_list> mlist;
    private ListView mlistview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fensi);
        //获取登录用户信息
        utils = new PreferenceUtils(fensi.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbart = (TextView) findViewById(R.id.toolbar_title);
        toolbart.setText("TA的粉丝");

        mlistview = (ListView) findViewById(R.id.fensi_list);

        initdata();



        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(fensi.this, zhuye.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mlist.get(position).getMemberId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    private void initdata() {

        String id =getIntent().getExtras().getString("id");
        String url ="http://bobtrip.com/tripcal/api/member/listTaFans.action?memberId="+id+"&loginMemberId="+
                memberId;
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("list");

                    mlist =new ArrayList<guanzhu_list>();
                    for (int i =0;i<jsonArray.length();i++){

                        JSONObject j =jsonArray.getJSONObject(i);
                        String countAttention =j.getString("countAttention");
                        String countFans =j.getString("countFans");
                        String headIcon =j.getString("headIcon");
                        String memberId =j.getString("memberId");
                        String nickName =j.getString("nickName");
                        mlist.add(new guanzhu_list(countAttention,countFans,headIcon,memberId,nickName));

                    }

                    guanzhu_adapter adapter =new guanzhu_adapter(mlist,fensi.this);

                    mlistview.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



    }



}
