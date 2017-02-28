package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.Adapter.main_zhaohuodong_Adapter;
import com.example.administrator.tour_menology.Bean.zhaohuodong_list;
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

public class sousuo_huodong extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener{
    private ImageButton toolbarback;
    private TextView toolbartext;
    private PullLayout mPullLayout;
    private zhaohuodong_list mbean;
    private List<zhaohuodong_list.Listdate> mListDataTemp = new ArrayList<>();
    private List<zhaohuodong_list.Listdate> mListData = new ArrayList<>();
    private ListView mListView;
    private main_zhaohuodong_Adapter mAdapter;
    private int yema=1;
    private EditText mEditText;
    private Button chaxun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo_huodong);

        toolbarback = (ImageButton) findViewById(R.id.toolbar_back);
        toolbarback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbartext = (TextView) findViewById(R.id.toolbar_title);
        toolbartext.setText("找活动");


        mPullLayout = (PullLayout) findViewById(R.id.zhaohuodong_pull);
        mPullLayout.setOnPullListener(this);
        mListView = (ListView) findViewById(R.id.zhaohuodong_list);
        mAdapter = new main_zhaohuodong_Adapter(mListData, this);
        mListView.setAdapter(mAdapter);
        initData2(yema,"", true);
        mEditText = (EditText) findViewById(R.id.chaxun_edt);
        chaxun = (Button) findViewById(R.id.chaxun_btn);

        chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData2(1,mEditText.getText().toString(), true);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(sousuo_huodong.this, ain_list_xiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mListData.get(position).getActivityId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });

    }


    private void initData2(int current, String keyword, final boolean isRefresh) {
        String url = "http://bobtrip.com/tripcal/api/activity/search.action?currentPage=" +
                current +"&keyword=" + keyword;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

                mPullLayout.finishPull();
                mListData.clear();
//                Toast.makeText(main_zhaohuodong_Activity.this, "暂无数据", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(String response) {
                mbean = new Gson().fromJson(response, zhaohuodong_list.class);
                mListDataTemp = mbean.getList();
                int length = mListDataTemp.size();

                if (length > 0) {
                    if (isRefresh) {
                        mPullLayout.finishPull();
                        mListData.clear();
                        Iterator<zhaohuodong_list.Listdate> iter = mListDataTemp.iterator();
                        while (iter.hasNext())
                            mListData.add(iter.next());
                        mAdapter.notifyDataSetChanged();
                        mListView.setSelection(0);
                    } else {

                        //   mListData.addAll(mListDataTemp);
                        //     mAdapter.notifyDataSetChanged();
                        mPullLayout.finishPull();
                        int size = mListView.getFirstVisiblePosition();
                        View view = mListView.getChildAt(0);
                        int top = view == null ? 0 : view.getTop();
                        mListData.addAll(mListDataTemp);
                        mAdapter.notifyDataSetChanged();
                        mListView.setSelectionFromTop(size, top -TRIM_MEMORY_COMPLETE);
                    }
                    mPullLayout.finishPull();
                } else {
                    mPullLayout.finishPull();
                    Toast.makeText(sousuo_huodong.this, "暂无数据", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onRefresh() {
        initData2(++yema,mEditText.getText().toString(), true);
    }

    @Override
    public void onLoad() {
        initData2(++yema,mEditText.getText().toString(), false);
    }
}
