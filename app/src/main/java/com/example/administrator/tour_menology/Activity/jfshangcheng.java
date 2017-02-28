package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.tour_menology.Adapter.pinglunAdapter;
import com.example.administrator.tour_menology.Adapter.shangcheng_adapter;
import com.example.administrator.tour_menology.Bean.pinglun_list;
import com.example.administrator.tour_menology.Bean.shangcheng_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.administrator.tour_menology.R.id.toolbar_back;

public class jfshangcheng extends AppCompatActivity {
    private TextView toolbart;
    private ImageButton toolbar;
    private TextView jifen,gengduo;
    private Button duihuan;
    private GridView mGridView;
    private RequestQueue mRequestQueue;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private List<shangcheng_list>mList;
    private String mWdjifen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jfshangcheng);
        toolbart = (TextView) findViewById(R.id.toolbar_title);
        toolbart.setText("积分商城");
        toolbar = (ImageButton) findViewById(toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        utils = new PreferenceUtils(jfshangcheng.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();
        jifen = (TextView) findViewById(R.id.jifen_jinfen);
        gengduo = (TextView) findViewById(R.id.jifen_gengduo);
        duihuan = (Button) findViewById(R.id.jifen_duihuan);
        mGridView = (GridView) findViewById(R.id.jifen_gridView);

        initvolley();



        gengduo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(jfshangcheng.this,renwushuoming.class);
                startActivity(intent);
            }
        });


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(jfshangcheng.this, shangchengxq.class);
                Bundle bundle = new Bundle();

                bundle.putString("jifen", mList.get(position).getIntegral());
                bundle.putString("wodejifen", mWdjifen);
                bundle.putString("id", mList.get(position).getRemoteProId());
                bundle.putString("zs", mList.get(position).getRemainingCount());
                bundle.putString("guize", mList.get(position).getExchangeNote());
                bundle.putString("title", mList.get(position).getName());
                bundle.putString("img", mList.get(position).getPoster());
                bundle.putString("productId", mList.get(position).getProductId());

                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });



        duihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  =new Intent(jfshangcheng.this,duihuanjl.class);
                startActivity(intent);
            }
        });



    }

    private void initvolley() {
        mRequestQueue = Volley.newRequestQueue(jfshangcheng.this);
        String url ="http://bobtrip.com/tripcal/api/listProduct.action?memberId="+memberId+"&sortType=";
        JsonObjectRequest jsonObject = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mList = new ArrayList<>();
                try {
                    JSONArray data = jsonObject.getJSONArray("list");
                    mWdjifen = jsonObject.getString("integral");
                    jifen.setText(jsonObject.getString("integral"));
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        String integral = jsonObject1.getString("integral");
                        String name = jsonObject1.getString("name");
                        String poster = jsonObject1.getString("poster");
                        String remoteProId = jsonObject1.getString("remoteProId");
                        String remainingCount = jsonObject1.getString("remainingCount");
                        String exchangeNote = jsonObject1.getString("exchangeNote");
                        String productId =jsonObject1.getString("productId");
                        mList.add(new shangcheng_list(integral, name, poster, remoteProId,remainingCount,exchangeNote,productId));
                    }

                    shangcheng_adapter adapter = new shangcheng_adapter(jfshangcheng.this, mList);
                    mGridView.setAdapter(adapter);


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

    }
}
