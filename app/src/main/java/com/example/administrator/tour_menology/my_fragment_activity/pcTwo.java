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
import com.example.administrator.tour_menology.Adapter.act_adapter;
import com.example.administrator.tour_menology.Adapter.shijian_adapter;
import com.example.administrator.tour_menology.Adapter.task_adapter;
import com.example.administrator.tour_menology.Bean.shijian_list;
import com.example.administrator.tour_menology.Bean.task_list;
import com.example.administrator.tour_menology.R;
import com.google.gson.Gson;
import com.xiaosu.pulllayout.PullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class pcTwo extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener{

    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private Button button_event,button_act;
    private String type,memberId;


    private PullLayout mPullLayout,aPullLayout;
    private ListView mListView1,mListView2;
    private shijian_list tbean;
    private int pa = 1;
    private shijian_adapter tAdapter;
    private act_adapter aAdapter;
    private List<shijian_list.listEvent> tlistdateTemp = new ArrayList<>();
    private List<shijian_list.listEvent> tlistdate = new ArrayList<>();

    private List<shijian_list.listAct> alistdateTemp = new ArrayList<>();
    private List<shijian_list.listAct> alistdate = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pctwo);

        Bundle extras = getIntent().getExtras();
        memberId = extras.getString("memberId");
        init();
    }

    public void init(){

        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_back.setOnClickListener(mListener);

        button_act = (Button) findViewById(R.id.button_act);
        button_event = (Button) findViewById(R.id.button_event);
        button_event.setOnClickListener(mListener);
        button_act.setOnClickListener(mListener);

        button_event.setBackgroundColor(Color.parseColor("#6034bcec"));
        button_act.setBackgroundColor(Color.parseColor("#00000000"));

        Intent intent = getIntent();
        type = intent.getStringExtra("type").toString().trim();
        if(null!=type) {
            if ("1".equals(type)) {
                toolbar_title.setText("进行中");
            }
             else  if ("2".equals(type)) {
                toolbar_title.setText("往期");
            }
            else if ("3".equals(type)) {
                toolbar_title.setText("未开始");
            }
        }


        mPullLayout = (PullLayout) findViewById(R.id.shijian_pulllayout);
        mPullLayout.setOnPullListener(this);
        mListView1 = (ListView) findViewById(R.id.shijian_list);
        tAdapter = new shijian_adapter(tlistdate, this);
        mListView1.setAdapter(tAdapter);

        aPullLayout = (PullLayout) findViewById(R.id.act_pulllayout);
        aPullLayout.setOnPullListener(this);
        mListView2 = (ListView) findViewById(R.id.act_list);
        aAdapter = new act_adapter(alistdate, this);
        mListView2.setAdapter(aAdapter);

        initDate(pa, "", "", memberId, true);


        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(pcTwo.this, ain_list_xiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", alistdate.get(position).getActivityId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });

    }



    //事件接口请求

    private void initDate(int pager, String unitType, String keyword, String memberId, final boolean isRefresh) {

        String URL= "";

        if ("1".equals(type)) {
            URL = "http://bobtrip.com/tripcal/api/selfcal/underway.action?memberId=" + memberId;
        }
        if("2".equals(type)){
            URL = "http://bobtrip.com/tripcal/api/selfcal/ago.action?memberId=" + memberId;
        }
        if("3".equals(type)) {
            URL = "http://bobtrip.com/tripcal/api/selfcal/notstart.action?memberId=" + memberId;
        }

        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(pcTwo.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.i("", "====解析活动的接口-->>");
                Gson gson = new Gson();
                tbean = gson.fromJson(response, shijian_list.class);
                tlistdateTemp = tbean.getListEvent();
                int length = tlistdateTemp.size();
                if (length > 0) {
                    if (isRefresh) {
                        tlistdate.clear();
                        Iterator<shijian_list.listEvent> iter = tlistdateTemp.iterator();
                        while (iter.hasNext())
                            tlistdate.add(iter.next());

                        tAdapter.notifyDataSetChanged();
                        mListView1.setSelection(0);
                        mPullLayout.finishPull();

                    } else {
                        int size = mListView1.getFirstVisiblePosition();
                        View view = mListView1.getChildAt(0);
                        int top = view == null ? 0 : view.getTop();
                        tlistdate.addAll(tlistdateTemp);
                        tAdapter.notifyDataSetChanged();
                        mListView1.setSelectionFromTop(size, top - TRIM_MEMORY_COMPLETE);
                        mPullLayout.finishPull();
                    }
                } else
                    Toast.makeText(pcTwo.this, "暂无数据", Toast.LENGTH_SHORT).show();

                mPullLayout.finishPull();
            }

        });
    }


    //活动接口请求

    private void initAct(int pager, String unitType, String keyword, String memberId, final boolean isRefresh) {

        Log.i("", "====活动按钮点击完成-->>");
        String URL= "";

        if ("1".equals(type)) {
            URL = "http://bobtrip.com/tripcal/api/selfcal/underway.action?memberId=" + memberId;
        }
        if("2".equals(type)){
            URL = "http://bobtrip.com/tripcal/api/selfcal/ago.action?memberId=" + memberId;
        }
        if("3".equals(type)) {
            URL = "http://bobtrip.com/tripcal/api/selfcal/notstart.action?memberId=" + memberId;
        }
        Log.i("", "====活动按钮点击完成2-->>");
        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                aPullLayout.finishPull();
                Toast.makeText(pcTwo.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                tbean = gson.fromJson(response, shijian_list.class);
                alistdateTemp = tbean.getListAct();
                int length = alistdateTemp.size();
                if (length > 0) {
                    if (isRefresh) {
                        alistdate.clear();
                        Iterator<shijian_list.listAct> iter = alistdateTemp.iterator();
                        while (iter.hasNext())
                            alistdate.add(iter.next());

                        aAdapter.notifyDataSetChanged();
                        mListView2.setSelection(0);
                        aPullLayout.finishPull();

                    } else {
                        int size = mListView2.getFirstVisiblePosition();
                        View view = mListView2.getChildAt(0);
                        int top = view == null ? 0 : view.getTop();
                        alistdate.addAll(alistdateTemp);
                        aAdapter.notifyDataSetChanged();
                        mListView2.setSelectionFromTop(size, top - TRIM_MEMORY_COMPLETE);
                        aPullLayout.finishPull();
                    }
                } else
                    Toast.makeText(pcTwo.this, "暂无数据", Toast.LENGTH_SHORT).show();

                aPullLayout.finishPull();
            }

        });
    }





    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_act:
                    mListView1.setVisibility(mListView1.GONE);
                    mListView2.setVisibility(mListView2.VISIBLE);
                    button_act.setBackgroundColor(Color.parseColor("#6034bcec"));
                    button_event.setBackgroundColor(Color.parseColor("#00000000"));
                    Log.i("", "====活动按钮点击-->>");
                    initAct(1, "", "", memberId, true);

                    break;
                case R.id.button_event:
                    mListView1.setVisibility(mListView1.VISIBLE);
                    mListView2.setVisibility(mListView2.GONE);
                    button_event.setBackgroundColor(Color.parseColor("#6034bcec"));
                    button_act.setBackgroundColor(Color.parseColor("#00000000"));
                    initDate(1, "", "", memberId, true);
                    break;

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
        initDate(pa, "", "", memberId, false);
    }

}
