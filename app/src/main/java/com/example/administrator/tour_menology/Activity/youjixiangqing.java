package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Adapter.youjixiangqing_adapter;
import com.example.administrator.tour_menology.Bean.main_list1_list;
import com.example.administrator.tour_menology.Bean.youjixinagqing_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.PositiveDialong;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.example.administrator.tour_menology.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class youjixiangqing extends AppCompatActivity {
    private ImageButton guanzhu, weiguanzhu;
    private ImageView mImg_img2, mimg1;
    private TextView guanzhucs, name, title, date, date2, address;

    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;

    private RequestQueue mRequestQueue;
    private TextView toolbart;
    private ImageButton toolbar;
    private List<youjixinagqing_list> mList = new ArrayList<youjixinagqing_list>();
    private ListView mListView;
    private String mIdd;
    private String mId2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youjixiangqing);
        utils = new PreferenceUtils(youjixiangqing.this);
        initview();

        toolbart = (TextView) findViewById(R.id.toolbar_title);
        toolbart.setText("游记详情");

        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initdate();

        guanzhuclick();
    }

    private void guanzhuclick() {

        guanzhu.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String urlid = getIntent().getExtras().getString("id");
                String url = "http://bobtrip.com/tripcal/api/travel/store.action";
                final Map<String, String> params = new HashMap<String, String>();
                params.put("travelId", urlid);
                params.put("memberId", memberId);

                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            mIdd = jsonObject.getString("travelStoreId");


                            String url1 = "http://bobtrip.com/tripcal/api/travel/canclestore.action";
                            final Map<String, String> params2 = new HashMap<String, String>();
                            params2.put("travelStoreId", mIdd);

                            OkHttpUtils.post().url(url1).params(params2).build().execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e) {
                                    Toast.makeText(youjixiangqing.this, "取消失败了亲", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(youjixiangqing.this, "取消成功", Toast.LENGTH_SHORT).show();
                                    initdate();

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        initdate();

                    }
                });


            }
        });


        weiguanzhu.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String urlid = getIntent().getExtras().getString("id");
                String url = "http://bobtrip.com/tripcal/api/travel/store.action";
                final Map<String, String> params = new HashMap<String, String>();
                params.put("travelId", urlid);
                params.put("memberId", memberId);

                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(youjixiangqing.this, "添加失败了亲", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(youjixiangqing.this, "添加成功", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("recordId")) {
                                final String recordId = jsonObject.getString("travelStoreId");


                                PositiveDialong dialog = new PositiveDialong(youjixiangqing.this, "",
                                        new PositiveDialong.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                switch (v.getId()) {
                                                    case R.id.dialog_btn_lq:
                                                        String url = "http://bobtrip.com/tripcal/api/getRewords.action?recordId=" + recordId;
                                                        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                                                                                                       @Override
                                                                                                       public void onError(Call call, Exception e) {

                                                                                                       }

                                                                                                       @Override
                                                                                                       public void onResponse(String response) {
                                                                                                           try {
                                                                                                               JSONObject jsonObject1 = new JSONObject(response);
                                                                                                               String fen = jsonObject1.getString("point");
                                                                                                               Toast.makeText(youjixiangqing.this, "成功领取" + fen + "分",
                                                                                                                       Toast.LENGTH_SHORT).show();
                                                                                                           } catch (JSONException e) {
                                                                                                               e.printStackTrace();
                                                                                                           }

                                                                                                       }
                                                                                                   }
                                                        );


                                                        break;
                                                    case R.id.dialog_btn:
                                                        break;
                                                }

                                            }
                                        });
                                dialog.show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        initdate();

                    }
                });
            }
        });
    }


    private void initdate() {

        String urlid = getIntent().getExtras().getString("id");
        //获取登录用户信息
        utils = new PreferenceUtils(youjixiangqing.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        if (map == null || map.size() < 1) {
            Toast.makeText(youjixiangqing.this, "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(youjixiangqing.this,
                    denglu.class);
            startActivity(intent);

        } else
            memberId = map.get("memberId").toString();
        String url = "http://bobtrip.com/tripcal/api/travel/detail.action?travelId=" + urlid + "&memberId=" + memberId;


        mRequestQueue = Volley.newRequestQueue(youjixiangqing.this);


        JsonObjectRequest objectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("detail");
                    title.setText(jsonObject1.getString("title"));
                    guanzhucs.setText(jsonObject1.getString("countStore"));
                    date.setText(jsonObject1.getString("startDate"));
                    date2.setText(jsonObject1.getString("days"));
                    address.setText(jsonObject1.getString("cityName"));
                    name.setText(jsonObject1.getString("nickName"));
                    mId2 = jsonObject1.getString("memberId");
                    Glide.with(youjixiangqing.this).load(jsonObject1.getString("logo")).diskCacheStrategy(DiskCacheStrategy.ALL).into(mimg1);
                    Glide.with(youjixiangqing.this).load(jsonObject1.getString("headIcon")).diskCacheStrategy(DiskCacheStrategy.ALL).into(mImg_img2);


                    JSONArray jsonArray = jsonObject1.getJSONArray("travel_cont");
                    //  for (int i =1;i<jsonArray.length();i++){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                        String shoucangId = jsonObject2.getString("travelEnjoyId");
                        Log.d("ssss", String.valueOf(StringUtil.isBlank(shoucangId)));
                        Log.d("ssss", String.valueOf(shoucangId.equals("null")));
                        if (shoucangId.equals("null")) {
                            Log.d("ssss", "11");
                            shoucangId = "";
                        }
                        String id = jsonObject2.getString("travelContId");
                        if (id.equals("null")) {
                            Log.d("ssss", "11");
                            id = "";
                        }
                        String address = jsonObject2.getString("address");
                        if (address.equals("null")) {
                            Log.d("ssss", "11");
                            address = "";
                        }
                        String shoucang = jsonObject2.getString("countEnjoy");
                        if (shoucang.equals("null")) {
                            Log.d("ssss", "11");
                            shoucang = "";
                        }
                        String cont = jsonObject2.getString("cont");

                        String beizhu = jsonObject2.getString("imgDesc");
                        if (beizhu.equals("null")) {
                            Log.d("ssss", "11");
                            beizhu = "";
                        }
                        String tape = jsonObject2.getString("travelType");
                        String travelId =jsonObject1.getString("travelId");

                        mList.add(new youjixinagqing_list(shoucangId, id, address, shoucang, cont, beizhu, tape,travelId));
                    }
                    youjixiangqing_adapter adapter = new youjixiangqing_adapter(youjixiangqing.this, mList);
//                    View headerView = View.inflate(youjixiangqing.this, R.layout.activity_youjixiangqing,null);
//                    mListView.addHeaderView(headerView);
                    mListView.setAdapter(adapter);


                    if (jsonObject1.getBoolean("storeBoolean") == true) {
                        guanzhu.setVisibility(View.VISIBLE);
                        weiguanzhu.setVisibility(View.GONE);
                    } else {

                        guanzhu.setVisibility(View.GONE);
                        weiguanzhu.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mRequestQueue.add(objectRequest);
    }

    private void initview() {
        mListView = (ListView) findViewById(R.id.youjixq_list);

        LinearLayout Listviewhead = (LinearLayout) getLayoutInflater().inflate(R.layout.listhead,mListView,false);

        mListView.addHeaderView(Listviewhead);
        mImg_img2 = (ImageView) findViewById(R.id.youjixq_img_2);
        guanzhu = (ImageButton) findViewById(R.id.youjixq_star_t);
        weiguanzhu = (ImageButton) findViewById(R.id.youjixq_star_f);
        mimg1 = (ImageView) findViewById(R.id.youjixq_img);
        guanzhucs = (TextView) findViewById(R.id.youjixq_shoucang);
        name = (TextView) findViewById(R.id.youjixq_name);
        title = (TextView) findViewById(R.id.youjixq_title);
        date = (TextView) findViewById(R.id.youjixq_date);
        date2 = (TextView) findViewById(R.id.youjixq_date2);
        address = (TextView) findViewById(R.id.youjixq_address);




        mImg_img2 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(youjixiangqing.this, zhuye.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mId2);
                intent.putExtras(bundle);
                youjixiangqing.this.startActivity(intent);
            }
        });


    }

}
