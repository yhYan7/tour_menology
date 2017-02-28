package com.example.administrator.tour_menology.my_fragment_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.Adapter.mypinglun_adapter;
import com.example.administrator.tour_menology.Adapter.task_adapter;
import com.example.administrator.tour_menology.Bean.mypinglun_list;
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
public class pcOne extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener{

    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private String type;


    private String memberId;
    private PullLayout mPullLayout;
    private ListView mListView1;
    private task_list tbean;
    private int pa = 1;
    private task_adapter tAdapter;
    private List<task_list.list> tlistdateTemp = new ArrayList<>();
    private List<task_list.list> tlistdate = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcone);

        Bundle extras = getIntent().getExtras();
        memberId = extras.getString("memberId");
        init();
    }

    public void init(){

        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        toolbar_back.setOnClickListener(mListener);

        Intent intent = getIntent();
        type = intent.getStringExtra("type").toString().trim();
        if(null!=type) {
             if ("1".equals(type)) {
                toolbar_title.setText("待办任务");
            }
           else if ("2".equals(type)) {
                toolbar_title.setText("已完成任务");
            }

        }

        mPullLayout = (PullLayout) findViewById(R.id.renwu_pulllayout);
        mPullLayout.setOnPullListener(this);
        mListView1 = (ListView) findViewById(R.id.renwu_list);
        tAdapter = new task_adapter(tlistdate, this);
        mListView1.setAdapter(tAdapter);

        initDate(pa, "", "", memberId, false);



    }


    //待办及已完成接口请求

    private void initDate(int pager, String unitType, String keyword, String memberId, final boolean isRefresh) {

        String URL= "";

        if ("1".equals(type)) {
          URL = "http://bobtrip.com/tripcal/api/selfcal/todo.action?memberId=" + memberId;
        }else {
          URL = "http://bobtrip.com/tripcal/api/selfcal/finish.action?memberId=" + memberId;
        }

        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(pcOne.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                tbean = gson.fromJson(response, task_list.class);
                tlistdateTemp = tbean.getList();
                int length = tlistdateTemp.size();
                if (length > 0) {
                    if (isRefresh) {
                        tlistdate.clear();
                        Iterator<task_list.list> iter = tlistdateTemp.iterator();
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
                    Toast.makeText(pcOne.this, "暂无数据", Toast.LENGTH_SHORT).show();

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
        pa++;
        initDate(pa, "", "", memberId, false);
    }

}
