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
import com.example.administrator.tour_menology.Adapter.gonglue_adapter;
import com.example.administrator.tour_menology.Bean.gonglue_list;
import com.example.administrator.tour_menology.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class tadeyouji extends AppCompatActivity {

    private ImageButton toolbar;
    private TextView toolbart;
    private RequestQueue mRequestQueue;

    private List<gonglue_list> mList2;
    private ListView mListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tadeyouji);

        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbart = (TextView) findViewById(R.id.toolbar_title);
        toolbart.setText("TA收藏的游记");
        mListView = (ListView) findViewById(R.id.tayouji_listview);

        String id =getIntent().getExtras().getString("id");

        String url ="http://bobtrip.com/tripcal/api/travel/listTaStore.action?currentPage=1&memberId="+id;

        mRequestQueue = Volley.newRequestQueue(tadeyouji.this);
        JsonObjectRequest jsonObject = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                mList2 = new ArrayList<>();

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String title = jsonObject1.getString("title");
                        String name = jsonObject1.getString("area");
                        String date = jsonObject1.getString("createDateStr");
                        String date2 = jsonObject1.getString("days");
                        String img2 = jsonObject1.getString("imgPath");
                        String id = jsonObject1.getString("travelId");
                        mList2.add(new gonglue_list("http://bobtrip.com/tripcal"+img2, title, name, date2, date, id));

                    }
                    gonglue_adapter adapter = new gonglue_adapter(tadeyouji.this, mList2);

                    mListView.setAdapter(adapter);


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


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(tadeyouji.this, youjixiangqing.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", mList2.get(position).getStrategyId());

                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });


    }
}
