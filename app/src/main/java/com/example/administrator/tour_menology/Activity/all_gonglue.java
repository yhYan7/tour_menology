package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.Adapter.gonglue_adapter_ok;
import com.example.administrator.tour_menology.Bean.gonglue_list_ok;
import com.example.administrator.tour_menology.Bean.main_fabu_list;
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

public class all_gonglue extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener {


    private PullLayout mPullLayout;
    private ListView mListView;
    private TextView toolbar_text;
    private ImageButton toolbar;
    private gonglue_adapter_ok mAdapter;
    private gonglue_list_ok mbean;
    private int pa = 1;
    private List<gonglue_list_ok.LIstdate>mlistdateTemp = new ArrayList<>();
    private List<gonglue_list_ok.LIstdate>mlistdate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_gonglue);
        toolbar_text = (TextView) findViewById(R.id.toolbar_title);
        toolbar_text.setText("原创攻略");
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPullLayout = (PullLayout) findViewById(R.id.gonglue_polllayout);
        mPullLayout.setOnPullListener(this);
        mListView = (ListView) findViewById(R.id.gonglue_listview);
        mAdapter =new gonglue_adapter_ok(mlistdate,this);
        mListView.setAdapter(mAdapter);


        initData(pa,true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(all_gonglue.this, gonglue_xiangqing.class);
                Bundle bundle = new Bundle();

                bundle.putString("id",mlistdate.get(position).getStrategyId());

                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });


    }

    private void initData(int keyword, final boolean isRefresh) {

        String url = "http://bobtrip.com/tripcal/api/strategy/list.action?currentPage="+keyword;


        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mbean = gson.fromJson(response, gonglue_list_ok.class);
                mlistdateTemp = mbean.getList();
                int length = mlistdateTemp.size();
                if (length > 0) {
                    if (isRefresh) {
//                        mlistdate.clear();
                        Iterator<gonglue_list_ok.LIstdate> iter = mlistdateTemp.iterator();
                        while (iter.hasNext())
                            mlistdate.add(iter.next());

                        mAdapter.notifyDataSetChanged();
                        mListView.setSelection(0);
                        mPullLayout.finishPull();

                    } else {
                        int size = mListView.getFirstVisiblePosition();
                        View view = mListView.getChildAt(0);
                        int top = view == null ? 0 : view.getTop();
                        mlistdate.addAll(mlistdateTemp);
                        mAdapter.notifyDataSetChanged();
                        mListView.setSelectionFromTop(size, top - TRIM_MEMORY_COMPLETE);
                        mPullLayout.finishPull();
                    }
                } else
                    Toast.makeText(all_gonglue.this, "暂无数据", Toast.LENGTH_SHORT).show();

                mPullLayout.finishPull();
            }
        });
    }

    @Override
    public void onRefresh() {
        initData(++pa,true);
    }

    @Override
    public void onLoad() {
        initData(++pa,true);
    }
}
