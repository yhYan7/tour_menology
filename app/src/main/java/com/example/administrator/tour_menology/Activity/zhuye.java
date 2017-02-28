package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Adapter.gonglue_adapter;
import com.example.administrator.tour_menology.Adapter.xihuan_adapter;
import com.example.administrator.tour_menology.Bean.gonglue_list;
import com.example.administrator.tour_menology.Bean.xihuan_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.PositiveDialong;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.example.administrator.tour_menology.R.id.zhuye_city;

public class zhuye extends AppCompatActivity {

    private ImageButton toolbar;
    private TextView toolbart;
    private ImageView img,nan,nv;
    private TextView title,jifen,city,xintiao,dingyue,guanzhuderen,fensi,dengji;
    private Button guanzhu,weiguanzhu,youji,xihuan;
    private ImageButton shudian;

    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private String mId;
    private RelativeLayout r1,r2,r3;
    private ListView mListView;
    private GridView mGridView;
    private RequestQueue mRequestQueue,mRequestQueue2;

    private List<gonglue_list> mList2;
    private List<xihuan_list>mList;


    private PopupWindow mPopupWindow;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuye);

        initview();
//获取登录用户信息
        utils = new PreferenceUtils(zhuye.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();

        shudian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupwindow();
            }
        });


        initdata();




        weiguanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url  ="http://bobtrip.com/tripcal/api/attention/setAttention.action";
                Map<String, String> params = new HashMap<>();
                params.put("memberId",getIntent().getExtras().getString("id"));
                params.put("loginMemberId",memberId);


                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(zhuye.this,"关注成功",Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            if (TextUtils.isEmpty(jsonObject.getString("recordId"))){
                                final String recordId = jsonObject.getString("recordId");


                                PositiveDialong dialog = new PositiveDialong(zhuye.this, "",
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
                                                                                                               Toast.makeText(zhuye.this, "成功领取" + fen + "分",
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

                            initdata();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });



            }
        });


        guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url  ="http://bobtrip.com/tripcal/api/attention/cancel.action";
                Map<String, String> params = new HashMap<>();
                params.put("attentionId",mId);
                params.put("memberId",memberId);


                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(zhuye.this,"取消关注成功",Toast.LENGTH_SHORT).show();
                        initdata();

                    }
                });



            }
        });


        initdata2();



        initdata3();


        youji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridView.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
                youji.setBackgroundColor(Color.parseColor("#6034bcec"));
                xihuan.setBackgroundColor(Color.parseColor("#00000000"));
                initdata2();
            }
        });

        xihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListView.setVisibility(View.GONE);
                mGridView.setVisibility(View.VISIBLE);
                xihuan.setBackgroundColor(Color.parseColor("#6034bcec"));
                youji.setBackgroundColor(Color.parseColor("#00000000"));
                initdata3();
            }
        });




    }

    private void showPopupwindow() {

        View contentView = LayoutInflater.from(zhuye.this).inflate(R.layout.zhuye_menu,null);

        mPopupWindow = new PopupWindow(contentView);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        Button tv1 = (Button) contentView.findViewById(R.id.zhuye_menu_huodong);
        Button tv2 = (Button) contentView.findViewById(R.id.zhuye_menu_youji);

        mPopupWindow.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
//
//        mPopupWindow.showAsDropDown(shudian);
        mPopupWindow.showAtLocation(shudian,Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置


        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
                Intent intent =new Intent( zhuye.this,tadehuodong.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", getIntent().getExtras().getString("id"));

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
                Intent intent =new Intent( zhuye.this,tadeyouji.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", getIntent().getExtras().getString("id"));

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }

    private void initdata3() {

        String url ="http://bobtrip.com/tripcal/api/travelCont/listLikePic.action?memberId="+getIntent().getExtras().getString("id");
        mRequestQueue2 =Volley.newRequestQueue(zhuye.this);
        JsonObjectRequest objectRequest =new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mList = new ArrayList<>();

                try {
                    JSONArray jsonObject1=jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonObject1.length(); i++) {
                        JSONObject jsonObject2 = jsonObject1.getJSONObject(i);

                        String travelContId = jsonObject2.getString("travelContId");
                        String imgPath = jsonObject2.getString("imgPath");
                        String travelId = jsonObject2.getString("travelId");

                        mList.add(new xihuan_list(travelContId,"http://bobtrip.com/tripcal"+imgPath, travelId));

                    }

                    xihuan_adapter adapter = new xihuan_adapter(zhuye.this, mList);

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


        mRequestQueue2.add(objectRequest);



        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(zhuye.this, youji_pinglun.class);
                Bundle bundle = new Bundle();
                bundle.putString("travelContId", mList.get(position).getTravelContId());
                bundle.putString("travelId", mList.get(position).getTravelId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });


    }

    private void initdata2() {

        String url ="http://bobtrip.com/tripcal/api/travel/taTravel.action?memberId="+getIntent().getExtras().getString("id");

        mRequestQueue = Volley.newRequestQueue(zhuye.this);
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
                        String date = jsonObject1.getString("startDate");
                        String date2 = jsonObject1.getString("days");
                        String img2 = jsonObject1.getString("imgPath");
                        String id = jsonObject1.getString("travelId");
                        mList2.add(new gonglue_list("http://bobtrip.com/tripcal"+img2, title, name, date2, date, id));

                    }
                    gonglue_adapter adapter = new gonglue_adapter(zhuye.this, mList2);

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
                Intent mIntent = new Intent(zhuye.this, youjixiangqing.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", mList2.get(position).getStrategyId());

                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });


    }

    private void initdata() {
        String id =getIntent().getExtras().getString("id");


        String url = "http://bobtrip.com/tripcal/api/member/mainPage.action?memberId="+id+"&loginMemberId="+memberId;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONObject jsonObject1 =jsonObject.getJSONObject("detail");
                    guanzhuderen.setText(jsonObject1.getString("attentionCount"));
                    jifen.setText(jsonObject1.getString("integral"));
                    dengji.setText(jsonObject1.getString("rank"));
                    xintiao.setText(jsonObject1.getString("creed"));
                    city.setText(jsonObject1.getString("areaname"));
                    title.setText(jsonObject1.getString("nickName"));
                    dingyue.setText(jsonObject1.getString("countFocus"));
                    fensi.setText(jsonObject1.getString("fansCount"));


                    if (jsonObject1.getString("sex").equals("男")){
                        nv.setVisibility(View.GONE);
                    }else {
                        nan.setVisibility(View.GONE);
                    }



                    if(TextUtils.isEmpty(jsonObject1.getString("attentionId"))){
                        guanzhu.setVisibility(View.GONE);
                        weiguanzhu.setVisibility(View.VISIBLE);

                    }else {
                        weiguanzhu.setVisibility(View.GONE);
                        mId = jsonObject1.getString("attentionId");
                        guanzhu.setVisibility(View.VISIBLE);

                    }

                    Glide.with(zhuye.this).load(jsonObject1.getString("headIcon")).diskCacheStrategy(DiskCacheStrategy.ALL).into(img);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    private void initview() {
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbart = (TextView) findViewById(R.id.toolbar_title);
        toolbart.setText("个人主页");

        img = (ImageView) findViewById(R.id.zhuye_img);
        nan = (ImageView) findViewById(R.id.zhuye_nan);
        nv = (ImageView) findViewById(R.id.zhuye_nv);
        title = (TextView) findViewById(R.id.zhuye_title);
        jifen = (TextView) findViewById(R.id.zhuye_jifen);
        city = (TextView) findViewById(R.id.zhuye_city);
        xintiao = (TextView) findViewById(R.id.zhuye_xintiao);
        dingyue = (TextView) findViewById(R.id.zhuye_dingyue);
        guanzhuderen = (TextView) findViewById(R.id.zhuye_guanzhuren);
        fensi = (TextView) findViewById(R.id.zhuye_fensi);
        dengji = (TextView) findViewById(R.id.zhuye_dengji);
        guanzhu = (Button) findViewById(R.id.zhuye_yiguanzhu);
        weiguanzhu = (Button) findViewById(R.id.zhuye_weiguanzhu);
        youji = (Button) findViewById(R.id.zhuye_youji);
        xihuan = (Button) findViewById(R.id.zhuye_xihuan);
        shudian = (ImageButton) findViewById(R.id.zhuye_dian);


        r1 = (RelativeLayout) findViewById(R.id.zhuye_r1);
        r2 = (RelativeLayout) findViewById(R.id.zhuye_r2);
        r3 = (RelativeLayout) findViewById(R.id.zhuye_r3);
        mListView = (ListView) findViewById(R.id.zhuye_listview);
        mGridView = (GridView) findViewById(R.id.zhuye_gridview);


        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( zhuye.this,shequ.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", getIntent().getExtras().getString("id"));

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( zhuye.this,guanzhu.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", getIntent().getExtras().getString("id"));

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( zhuye.this,fensi.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", getIntent().getExtras().getString("id"));

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



    }
}
