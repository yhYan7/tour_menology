package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.administrator.tour_menology.Adapter.main_list1_adapter;
import com.example.administrator.tour_menology.Adapter.pinglunAdapter;
import com.example.administrator.tour_menology.Adapter.zhoubian_adapter;
import com.example.administrator.tour_menology.Bean.main_list1_list;
import com.example.administrator.tour_menology.Bean.pinglun_list;
import com.example.administrator.tour_menology.Bean.zhoubian_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.PositiveDialong;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
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

public class main_list1_xiangqing2 extends AppCompatActivity {
    private ImageButton toolbar;
    private TextView toolbartitle, title, shoucangcishu, address, date, fabufang, guan, call, xiangqing;
    private ImageView top, bottom;
    private String url = "http://bobtrip.com/tripcal/api/activity/detail.action?activityId=";

    private RelativeLayout daoru, weodaoru, shoucang, weishoucang;
    private boolean guanzhu2, daoru1;

    private String quxiao = null;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private ListView mListView;
    private List<pinglun_list> mList;

    private RequestQueue mRequestQueue, mRequestQueue2;
    private TextView mTextView;
    private String shoucID;
    private Button mButton, mButton1;
    private RelativeLayout mLayout_fabu, call_R;
    private String mUnitId;
    private List<zhoubian_list> mList2;
    private GridView mListView2;
    private PositiveDialong dialog;

    private Button m1, m2;
    private String mContact;
    private String mMDaoru1;
    private int mLength;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list1_xiangqing2);
        mButton = (Button) findViewById(R.id.btn_more);
        mButton = (Button) findViewById(R.id.btn_more);
        mListView2 = (GridView) findViewById(R.id.more_list);
        initView();
        initData();

        initclick();
        initVolley();

        initVolley2();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_list1_xiangqing2.this, zhaohuodong_pinglun.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", getIntent().getExtras().getString("id"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(main_list1_xiangqing2.this, Viewxiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", "http://m.bobtrip.com/bang-trip/scenery/detail.jsp?unitCode=" + mList2.get(position).getInitCode());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });


        daoru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://bobtrip.com/tripcal/api/activity/selfDel.action?selfActId=" + mMDaoru1;

                OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(main_list1_xiangqing2.this, "取消成功", Toast.LENGTH_SHORT).show();
                        initData();
                    }
                });
            }
        });


    }

    private void initVolley2() {
        String url = getIntent().getExtras().getString("id");
        String URL_list1 = "http://bobtrip.com/tripcal/api/listNearProduct.action?actId=" + url;

        mRequestQueue2 = Volley.newRequestQueue(main_list1_xiangqing2.this);
        JsonObjectRequest jsonObject = new JsonObjectRequest(URL_list1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mList2 = new ArrayList<>();
                try {
                    JSONArray list2 = jsonObject.getJSONArray("list");
                    mLength = list2.length();
                    for (int i = 0; i < list2.length(); i++) {
                        JSONObject jsonObject11 = list2.getJSONObject(i);
                        String id = jsonObject11.getString("unitCode");
                        String title = jsonObject11.getString("nameCn");
                        String img = jsonObject11.getString("defaultPhoto");
                        String yuan = jsonObject11.getString("marketPrice");
                        String xian = jsonObject11.getString("minPrice");

                        mList2.add(new zhoubian_list(yuan, title, img, xian, id));
                    }
                    zhoubian_adapter adapter = new zhoubian_adapter(main_list1_xiangqing2.this, mList2);
                    mListView2.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(mListView2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mRequestQueue2.add(jsonObject);
    }

    private void initVolley() {
        mRequestQueue = Volley.newRequestQueue(main_list1_xiangqing2.this);
        Log.d("123", "3" + shoucID);
        String url2 = getIntent().getExtras().getString("id");
        String Url = "http://bobtrip.com/tripcal/api/comment/list.action?activityId=" + url2 + "&currentPage=1";

        JsonObjectRequest jsonObject = new JsonObjectRequest(Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mList = new ArrayList<>();
                try {
                    JSONArray data = jsonObject.getJSONArray("list");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        String content = jsonObject1.getString("content");
                        String img = jsonObject1.getString("headIcon");
                        String title = jsonObject1.getString("nickName");
                        String date = jsonObject1.getString("time");
                        mList.add(new pinglun_list(content, date, img, title));
                    }

                    pinglunAdapter adapter = new pinglunAdapter(main_list1_xiangqing2.this, mList);
                    mListView.setAdapter(adapter);

                    mListView.setEmptyView(mTextView);
                    //     setListViewHeightBasedOnChildren(mListView);


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


    private void initclick() {
        //取消收藏
        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://bobtrip.com/tripcal/api/activity/canclecollect.action";
                Map<String, String> params = new HashMap<String, String>();
                params.put("memberId", memberId);
                Log.d("123", memberId);
                params.put("activityIds", shoucID);

                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(main_list1_xiangqing2.this, "取消失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(main_list1_xiangqing2.this, "取消成功", Toast.LENGTH_SHORT).show();

                        initData();
                    }
                });
            }
        });

        //添加收藏

        weishoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://bobtrip.com/tripcal/api/activity/collect.action";

                Map<String, String> params = new HashMap<String, String>();
                params.put("memberId", memberId);
                Log.d("guanzhu", shoucID);
                params.put("activityIds", shoucID);

                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(main_list1_xiangqing2.this, "收藏失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(main_list1_xiangqing2.this, "收藏成功", Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("recordId")) {
                                final String recordId = jsonObject.getString("recordId");
                                dialog = new PositiveDialong(main_list1_xiangqing2.this, "",
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
                                                                                                               Toast.makeText(main_list1_xiangqing2.this, "成功领取" + fen + "分",
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
                        initData();
                    }
                });

            }
        });


        weodaoru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://bobtrip.com/tripcal/api/activity/selfAdd.action";

                Map<String, String> params = new HashMap<String, String>();
                params.put("memberId", memberId);
                Log.d("guanzhu", shoucID);
                params.put("activityIds", shoucID);

                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(main_list1_xiangqing2.this, "导入失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(main_list1_xiangqing2.this, "导入成功", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("recordId")) {
                                final String recordId = jsonObject.getString("recordId");
                                dialog = new PositiveDialong(main_list1_xiangqing2.this, "",
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
                                                                                                               Toast.makeText(main_list1_xiangqing2.this, "成功领取" + fen + "分",
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
                        initData();
                    }
                });

            }
        });

        mLayout_fabu = (RelativeLayout) findViewById(R.id.fabufang_R);
        call_R = (RelativeLayout) findViewById(R.id.huodong_call);

        call_R.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mContact));
                startActivity(intent);
            }
        });

        mLayout_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(main_list1_xiangqing2.this, main_fabu_xiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mUnitId);
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });


    }


    private void initData() {
        String url2 = getIntent().getExtras().getString("id");
//        boolean guanzhu = getIntent().getExtras().getBoolean("guanzhu");
//        if (guanzhu == true) {
//            guan.setText("已关注");
//        } else if (guanzhu == false) {
//            guan.setText("未关注");
//        }
        String okurl = url + url2 + "&memberId=" + memberId;

        OkHttpUtils.get().url(okurl).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject detail = jsonObject.getJSONObject("detail");
                    title.setText(detail.getString("actName"));
                    date.setText(detail.getString("actDateStr"));
                    address.setText(detail.getString("address"));
                    shoucangcishu.setText(detail.getString("collect_count"));
                    fabufang.setText(detail.getString("unitName"));
                    xiangqing.setText(detail.getString("actCont"));
                    mContact = detail.getString("contact");
                    call.setText(mContact);
                    Glide.with(main_list1_xiangqing2.this).load(detail.getString("poster")).into(top);
                    Glide.with(main_list1_xiangqing2.this).load(detail.getString("logo")).into(bottom);
                    shoucID = detail.getString("activityId");
                    Log.d("123", "2" + shoucID);
                    mUnitId = detail.getString("unitId");
                    quxiao = detail.getString("selfActId");
                    guanzhu2 = detail.getBoolean("collect");
                    mMDaoru1 = detail.getString("selfActId");
                    if (guanzhu2 == true) {
                        weishoucang.setVisibility(View.GONE);
                        shoucang.setVisibility(View.VISIBLE);
                    } else {
                        shoucang.setVisibility(View.GONE);
                        weishoucang.setVisibility(View.VISIBLE);
                    }
                    if (detail.getString("selfActId").equals("")) {

                        daoru.setVisibility(View.GONE);
                        weodaoru.setVisibility(View.VISIBLE);
                    } else {
                        weodaoru.setVisibility(View.GONE);
                        daoru.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbartitle = (TextView) findViewById(R.id.toolbar_title);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbartitle.setText("活动详情");
        title = (TextView) findViewById(R.id.main_list1_xiangqing_title);
        shoucangcishu = (TextView) findViewById(R.id.main_list1_xiangqing_shoucang);
        address = (TextView) findViewById(R.id.main_list1_xiangqing_address);
        date = (TextView) findViewById(R.id.main_list1_xiangqing_date);
        fabufang = (TextView) findViewById(R.id.main_list1_xiangqing_laiyuan);
        //   guan = (TextView) findViewById(R.id.main_list1_xiangqing_guanzhu);
        call = (TextView) findViewById(R.id.main_list1_xiangqing_call);
        xiangqing = (TextView) findViewById(R.id.main_list1_xiangqing_xiangqing);
        top = (ImageView) findViewById(R.id.main_list_top_img);
        bottom = (ImageView) findViewById(R.id.main_list_bottom_img);

        shoucang = (RelativeLayout) findViewById(R.id.yishoucang);
        weishoucang = (RelativeLayout) findViewById(R.id.weishoucang);
        weodaoru = (RelativeLayout) findViewById(R.id.weidaoru);
        daoru = (RelativeLayout) findViewById(R.id.yidaoru);

        mListView = (ListView) findViewById(R.id.list_pinglun);
        mTextView = (TextView) findViewById(R.id.pinglun_text);
        //获取登录用户信息
        utils = new PreferenceUtils(main_list1_xiangqing2.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        if (map == null || map.size() < 1) {
            Toast.makeText(main_list1_xiangqing2.this, "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(main_list1_xiangqing2.this,
                    denglu.class);
            startActivity(intent);

        } else
            memberId = map.get("memberId").toString();
        mButton1 = (Button) findViewById(R.id.btn_chakan);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_list1_xiangqing2.this, chakanxiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", getIntent().getExtras().getString("id"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        m1 = (Button) findViewById(R.id.main_list1_xiangguan);
        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        m2 = (Button) findViewById(R.id.main_list1_zhoubian);

        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_list1_xiangqing2.this, ain_list_xiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", getIntent().getExtras().getString("id"));
                bundle.putBoolean("guanzhu", getIntent().getExtras().getBoolean("guanzhu"));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

    }


    public void setListViewHeightBasedOnChildren(GridView gridView) {

        // 获取ListView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, gridView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }


        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        if (mLength % 2 == 0)
            params.height = (totalHeight + (gridView.getHeight() * (listAdapter.getCount() - 1))) / 2+3;
        else
            params.height = (totalHeight + (gridView.getHeight() * (listAdapter.getCount() - 1))) / 2 + 110;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gridView.setLayoutParams(params);
    }

}
