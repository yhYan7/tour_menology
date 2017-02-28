package com.example.administrator.tour_menology.my_fragment_activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.Activity.ain_list_xiangqing;
import com.example.administrator.tour_menology.Activity.main_zhaohuodong_Activity;
import com.example.administrator.tour_menology.Adapter.myfans_adapter;
import com.example.administrator.tour_menology.Adapter.mypinglun_adapter;
import com.example.administrator.tour_menology.Adapter.pl_shequ_adapter;
import com.example.administrator.tour_menology.Adapter.pl_youji_adapter;
import com.example.administrator.tour_menology.Bean.myfans_list;
import com.example.administrator.tour_menology.Bean.mypinglun_list;
import com.example.administrator.tour_menology.Bean.pl_shequ_list;
import com.example.administrator.tour_menology.Bean.pl_youji_list;
import com.example.administrator.tour_menology.R;
import com.google.gson.Gson;
import com.xiaosu.pulllayout.PullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class mypinglu extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener {

    private ImageButton toolbar_back;
    private TextView toolbar_title;


    private String memberId;
    private PullLayout mPullLayout;
    private ListView mListView3;
    private mypinglun_list mbean;
    private mypinglun_adapter mAdapter;
    private List<mypinglun_list.LIstdate> mlistdateTemp = new ArrayList<>();
    private List<mypinglun_list.LIstdate> mlistdate = new ArrayList<>();
    private Button mButton, mButton2, mButton3;
    private ListView listviewyouji, listviewshequ;
    private List<pl_shequ_list> mList;
    private List<pl_youji_list> mList2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypl);
        //获取上个页面的传值
        Bundle extras = getIntent().getExtras();
        memberId = extras.getString("memberId");
        init();
    }


    public void init() {

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("我的评论");
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_back.setOnClickListener(mListener);

        mPullLayout = (PullLayout) findViewById(R.id.my_huodongpinglun_pulllayout);
        mPullLayout.setOnPullListener(this);
        mListView3 = (ListView) findViewById(R.id.myhuodongpinglun_list);
        mAdapter = new mypinglun_adapter(mlistdate, this);
        mListView3.setAdapter(mAdapter);
        initDate(1, "", "", memberId, false);
        mButton = (Button) findViewById(R.id.pinglun_btn1);
        mButton2 = (Button) findViewById(R.id.pinglun_btn2);
        mButton3 = (Button) findViewById(R.id.pinglun_btn3);
        listviewyouji = (ListView) findViewById(R.id.pinglun_list_youji);
        listviewshequ = (ListView) findViewById(R.id.pinglun_list_shequ);

        mListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(mypinglu.this, ain_list_xiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mlistdate.get(position).getActivityId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton.setBackgroundColor(Color.parseColor("#6034bcec"));
                mButton2.setBackgroundColor(Color.parseColor("#00000000"));
                mButton3.setBackgroundColor(Color.parseColor("#00000000"));

                mPullLayout.setVisibility(View.VISIBLE);
                listviewshequ.setVisibility(View.GONE);
                listviewyouji.setVisibility(View.GONE);

            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton2.setBackgroundColor(Color.parseColor("#6034bcec"));
                mButton.setBackgroundColor(Color.parseColor("#00000000"));
                mButton3.setBackgroundColor(Color.parseColor("#00000000"));
                mPullLayout.setVisibility(View.GONE);
                listviewshequ.setVisibility(View.VISIBLE);
                listviewyouji.setVisibility(View.GONE);
                initdata2();

            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton3.setBackgroundColor(Color.parseColor("#6034bcec"));
                mButton2.setBackgroundColor(Color.parseColor("#00000000"));
                mButton.setBackgroundColor(Color.parseColor("#00000000"));
                mPullLayout.setVisibility(View.GONE);
                listviewshequ.setVisibility(View.GONE);
                listviewyouji.setVisibility(View.VISIBLE);
                initdata3();

            }
        });


    }

    private void initdata3() {
        String url = "http://bobtrip.com/tripcal/api/travelCont/listMyReview.action?memberId=" + memberId;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                mList2 = new ArrayList<pl_youji_list>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String content = jsonObject1.getString("content");
                        String startDate = jsonObject1.getString("startDate");
                        String title = jsonObject1.getString("title");
                        String travelContId = jsonObject1.getString("travelContId");
                        String areaName = jsonObject1.getString("areaName");
                        String days = jsonObject1.getString("days");
                        String nickName = jsonObject1.getString("nickName");
                        String posterPath = jsonObject1.getString("posterPath");
                        String imgPath = jsonObject1.getString("imgPath");
                        String memberId = jsonObject1.getString("memberId");

                        mList2.add(new pl_youji_list(content, startDate, title, travelContId, areaName, days, nickName, posterPath, imgPath, memberId));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pl_youji_adapter madapter = new pl_youji_adapter(mypinglu.this, mList2);
                listviewyouji.setAdapter(madapter);

            }
        });
    }

    private void initdata2() {

        String url = "http://bobtrip.com/tripcal/api/discussion/mylist.action?memberId=" + memberId;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                mList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String neirong = jsonObject1.getString("content");
                        String nickName = jsonObject1.getString("nickName");
                        String unitName = jsonObject1.getString("unitName");
                        String posterPath = jsonObject1.getString("posterPath");
                        String imgPath = jsonObject1.getString("imgPath");
                        String memberId = jsonObject1.getString("memberId");
                        String unitId = jsonObject1.getString("unitId");

                        mList.add(new pl_shequ_list(neirong, nickName, posterPath, imgPath, unitName, memberId, unitId));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pl_shequ_adapter madapter = new pl_shequ_adapter(mypinglu.this, mList);
                listviewshequ.setAdapter(madapter);

            }
        });

    }


    private void initDate(int pager, String unitType, String keyword, String memberId, final boolean isRefresh) {
        String URL = "http://bobtrip.com/tripcal/api/comment/myComment.action?memberId=" + memberId;

        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(mypinglu.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mbean = gson.fromJson(response, mypinglun_list.class);
                mlistdateTemp = mbean.getList();
                int length = mlistdateTemp.size();
                if (length > 0) {
                    if (isRefresh) {
                        mlistdate.clear();
                        Iterator<mypinglun_list.LIstdate> iter = mlistdateTemp.iterator();
                        while (iter.hasNext())
                            mlistdate.add(iter.next());

                        mAdapter.notifyDataSetChanged();
                        mListView3.setSelection(0);
                        mPullLayout.finishPull();

                    } else {
                        int size = mListView3.getFirstVisiblePosition();
                        View view = mListView3.getChildAt(0);
                        int top = view == null ? 0 : view.getTop();
                        mlistdate.addAll(mlistdateTemp);
                        mAdapter.notifyDataSetChanged();
                        mListView3.setSelectionFromTop(size, top - TRIM_MEMORY_COMPLETE);
                        mPullLayout.finishPull();
                    }
                } else
                    Toast.makeText(mypinglu.this, "暂无数据", Toast.LENGTH_SHORT).show();

                mPullLayout.finishPull();
            }

        });
    }


    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.toolbar_back:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        initDate(1, "", "", memberId, true);
    }

    @Override
    public void onLoad() {

        initDate(1, "", "", memberId, true);
    }

}
