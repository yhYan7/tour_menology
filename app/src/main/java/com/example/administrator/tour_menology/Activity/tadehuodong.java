package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.tour_menology.Adapter.main_list1_adapter;
import com.example.administrator.tour_menology.Bean.main_list1_list;
import com.example.administrator.tour_menology.Bean.main_list2_List;
import com.example.administrator.tour_menology.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class tadehuodong extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private List<main_list1_list> mList;
    private ListView mView;
    private ImageButton toolbar;
    private TextView toolbart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tadehuodong);
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbart = (TextView) findViewById(R.id.toolbar_title);
        toolbart.setText("TA收藏的活动");

        mView = (ListView) findViewById(R.id.tahuodong_list);

        String id =getIntent().getExtras().getString("id");
        String url ="http://bobtrip.com/tripcal/api/act/listTaStore.action?currentPage=1&memberId="+id;


        mRequestQueue = Volley.newRequestQueue(tadehuodong.this);
        JsonObjectRequest jsonObject = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mList = new ArrayList<>();
                try {
                    JSONArray list2 = jsonObject.getJSONArray("list");
                    for (int i = 0; i < list2.length(); i++) {
                        JSONObject jsonObject11 = list2.getJSONObject(i);
                        String id = jsonObject11.getString("activityId");
                        String address = jsonObject11.getString("address");
                        String title = jsonObject11.getString("actName");
                        String date = jsonObject11.getString("actDate");
                        String img = jsonObject11.getString("imgPath");
                        String guanzhu = jsonObject11.getString("countCollect");
                        mList.add(new main_list1_list(id, date, address,"http://bobtrip.com/tripcal" +img, guanzhu, title));
                    }
                    main_list1_adapter adapter = new main_list1_adapter(tadehuodong.this, mList);
                    mView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mRequestQueue.add(jsonObject);


        mView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(tadehuodong.this, ain_list_xiangqing.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", mList.get(position).getActivityId());
                //      bundle.putBoolean("guanzhu", mList1.get(position).getCollect());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });



    }
}
