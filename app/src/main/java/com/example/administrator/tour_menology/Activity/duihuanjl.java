package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.tour_menology.Adapter.duihuan_adapter;
import com.example.administrator.tour_menology.Adapter.main_list1_adapter;
import com.example.administrator.tour_menology.Bean.duihuan_list;
import com.example.administrator.tour_menology.Bean.main_list1_list;
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

public class duihuanjl extends AppCompatActivity {

    private ImageView toolbar;
    private TextView mTextView;
    private List<duihuan_list> mList;
    private ListView mListView;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;

    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duihuanjl);

        toolbar = (ImageView) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //获取登录用户信息
        utils = new PreferenceUtils(duihuanjl.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();


        mTextView = (TextView) findViewById(R.id.toolbar_title);
        mTextView.setText("兑换记录");
        mListView = (ListView) findViewById(R.id.duihuan_list);

        initdata();


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(duihuanjl.this,duihuanxinagqing.class);
                Bundle bundle = new Bundle();

                bundle.putString("Id",mList.get(position).getRecordId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    private void initdata() {
        String url = "http://bobtrip.com/tripcal/api/listExchange.action?memberId=" + memberId;
        mRequestQueue = Volley.newRequestQueue(duihuanjl.this);


        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    mList = new ArrayList<duihuan_list>();
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String jifen = jsonObject1.getString("integral");
                        String name = jsonObject1.getString("name");
                        String poster = jsonObject1.getString("poster");
                        String recordId = jsonObject1.getString("recordId");
                        String date = jsonObject1.getString("date");

                        mList.add(new duihuan_list(jifen, name, poster, recordId, date));

                    }

                    duihuan_adapter adapter = new duihuan_adapter(duihuanjl.this, mList);
                    mListView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
