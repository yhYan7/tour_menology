package com.example.administrator.tour_menology.my_fragment_activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.Activity.ain_list_xiangqing;
import com.example.administrator.tour_menology.Activity.gonglue_xiangqing;
import com.example.administrator.tour_menology.Activity.main_fabu_xiangqing;
import com.example.administrator.tour_menology.Activity.youjixiangqing;
import com.example.administrator.tour_menology.Adapter.main_zhaohuodong_Adapter;
import com.example.administrator.tour_menology.Adapter.shequdingyue_adapter;
import com.example.administrator.tour_menology.Adapter.shoucang_adapter;
import com.example.administrator.tour_menology.Adapter.shoucanghuodong_adapter;
import com.example.administrator.tour_menology.Bean.shequdingyue_list;
import com.example.administrator.tour_menology.Bean.shoucang_list;
import com.example.administrator.tour_menology.Bean.shoucanghuodong_list;
import com.example.administrator.tour_menology.Bean.zhaohuodong_list;
import com.example.administrator.tour_menology.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.xiaosu.pulllayout.PullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;

import static com.example.administrator.tour_menology.R.attr.position;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class myshoucang extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener{

    private ImageButton toolbar_back;
    private Button bianji;
    private TextView toolbar_title;

    private String memberId;
    private PullLayout mPullLayout,mPullLayout2;
    private ListView mListView3,mListView;
    private shoucanghuodong_list mbean;
    private shoucang_list mbean2;
    private int pa = 1;
    private shoucanghuodong_adapter mAdapter;
    private shoucang_adapter mAdapter2;
    private List<shoucang_list.listdata> mlistdateTemp2 = new ArrayList<>();
    private List<shoucanghuodong_list.LIstdate> mlistdateTemp = new ArrayList<>();
    private List<shoucang_list.listdata> mlistdate2 = new ArrayList<>();
    private List<shoucanghuodong_list.LIstdate> mlistdate = new ArrayList<>();
    private Button mButton,mButton2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myshoucang);
        //获取上个页面的传值
        Bundle extras = getIntent().getExtras();
        memberId = extras.getString("memberId");
        init();
    }

    public void init(){
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("我的收藏");

        mPullLayout = (PullLayout) findViewById(R.id.myhuodong_pulllayout);
        mPullLayout2 = (PullLayout) findViewById(R.id.myhuodong_pulllayout2);
        mPullLayout.setOnPullListener(this);
        mPullLayout2.setOnPullListener(this);
        mListView3 = (ListView) findViewById(R.id.myhuodong_list);
        mListView = (ListView) findViewById(R.id.shoucang_list);
        mAdapter = new shoucanghuodong_adapter(mlistdate, this);
        mAdapter2 = new shoucang_adapter(mlistdate2, this);
        mListView3.setAdapter(mAdapter);
        mListView.setAdapter(mAdapter2);

        initDate(pa, "", "", memberId, false);

        toolbar_back.setOnClickListener(mListener);

        mButton = (Button) findViewById(R.id.shoucang_btn1);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton.setBackgroundColor(Color.parseColor("#6034bcec"));
                mButton2.setBackgroundColor(Color.parseColor("#00000000"));
                mPullLayout.setVisibility(View.VISIBLE);
                mPullLayout2.setVisibility(View.GONE);
                initDate(1, "", "", memberId, true);

            }
        });
        mButton2 = (Button) findViewById(R.id.shoucang_btn2);

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton2.setBackgroundColor(Color.parseColor("#6034bcec"));
                mButton.setBackgroundColor(Color.parseColor("#00000000"));
                mPullLayout.setVisibility(View.GONE);
                mPullLayout2.setVisibility(View.VISIBLE);
                initdata(1,true);

            }
        });

        mListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(myshoucang.this, ain_list_xiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mlistdate.get(position).getActivityId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);

            }
        });



        mListView3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(myshoucang.this, youjixiangqing.class);
                Bundle bundle = new Bundle();

                bundle.putString("id",mlistdateTemp2.get(position).getTravelId());

                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });


    }

    private void initdata(int c, final boolean isRefresh) {

        String url ="http://bobtrip.com/tripcal/api/travel/listTaStore.action?memberId="+memberId+"&currentPage="+c;


        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(myshoucang.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mbean2 = gson.fromJson(response, shoucang_list.class);
                mlistdateTemp2 = mbean2.getList();
                if(mlistdateTemp2==null){
                    Toast.makeText(myshoucang.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                int length = mlistdateTemp2.size();
                if (length > 0) {
                    if (isRefresh) {
                        mlistdate2.clear();
                        Iterator<shoucang_list.listdata> iter = mlistdateTemp2.iterator();
                        while (iter.hasNext())
                            mlistdate2.add(iter.next());

                        mAdapter2.notifyDataSetChanged();
                        mListView.setSelection(0);
                        mPullLayout.finishPull();

                    } else {
                        mlistdate2.clear();
                        int size = mListView.getFirstVisiblePosition();
                        View view = mListView.getChildAt(0);
                        int top = view == null ? 0 : view.getTop();
                        mlistdate2.addAll(mlistdateTemp2);
                        mAdapter2.notifyDataSetChanged();
                        mListView.setSelectionFromTop(size, top - TRIM_MEMORY_COMPLETE);
                        mPullLayout2.finishPull();
                    }
                } else
                    Toast.makeText(myshoucang.this, "暂无数据", Toast.LENGTH_SHORT).show();

                mPullLayout2.finishPull();
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


    private void initDate(int pager, String unitType, String keyword, String memberId, final boolean isRefresh) {
        // String URL = "http://bobtrip.com/tripcal/api/personCenter/listMyFocus.action?currentPage=" + pager + "&memberId=" + memberId;
        String URL = "http://bobtrip.com/tripcal/api/act/listTaStore.action?memberId=" + memberId;

        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(myshoucang.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mbean = gson.fromJson(response, shoucanghuodong_list.class);
                mlistdateTemp = mbean.getList();
                if(mlistdateTemp==null){
                    Toast.makeText(myshoucang.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                int length = mlistdateTemp.size();
                if (length > 0) {
                    if (isRefresh) {
                        mlistdate.clear();
                        Iterator<shoucanghuodong_list.LIstdate> iter = mlistdateTemp.iterator();
                        while (iter.hasNext())
                            mlistdate.add(iter.next());

                        mAdapter.notifyDataSetChanged();
                        mListView3.setSelection(0);
                        mPullLayout.finishPull();

                    } else {
                        mlistdate.clear();
                        int size = mListView3.getFirstVisiblePosition();
                        View view = mListView3.getChildAt(0);
                        int top = view == null ? 0 : view.getTop();
                        mlistdate.addAll(mlistdateTemp);
                        mAdapter.notifyDataSetChanged();
                        mListView3.setSelectionFromTop(size, top - TRIM_MEMORY_COMPLETE);
                        mPullLayout.finishPull();
                    }
                } else
                    Toast.makeText(myshoucang.this, "暂无数据", Toast.LENGTH_SHORT).show();

                mPullLayout.finishPull();
            }

        });
    }



    @Override
    public void onRefresh() {
        initDate(1, "", "", memberId, true);
    }

    @Override
    public void onLoad() {

        initDate(1, "", "", memberId, false);
    }
}
