package com.example.administrator.tour_menology.my_fragment_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.Activity.zhuye;
import com.example.administrator.tour_menology.Adapter.myfans_adapter;
import com.example.administrator.tour_menology.Bean.myfans_list;
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
 * Created by Administrator on 2016/9/28 0028.
 */
public class myguanzhu extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener{

    private ImageButton toolbar_back;
    private Button bianji;
    private TextView toolbar_title;

    private String memberId;
    private PullLayout mPullLayout;
    private ListView mListView3;
    private myfans_list mbean;
    private int pa = 1;
    private myfans_adapter mAdapter;
    private List<myfans_list.LIstdate> mlistdateTemp = new ArrayList<>();
    private List<myfans_list.LIstdate> mlistdate = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myguanzhu);
        //获取上个页面的传值
        Bundle extras = getIntent().getExtras();
        memberId = extras.getString("memberId");
        init();
    }

    public void init(){
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("我的关注");
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
//        bianji = (Button) findViewById(R.id.hd_bianji);

        mPullLayout = (PullLayout) findViewById(R.id.my_guanzhu_pulllayout);
        mPullLayout.setOnPullListener(this);
        mListView3 = (ListView) findViewById(R.id.myguanzhu_list);
        mAdapter = new myfans_adapter(mlistdate, this);
        mListView3.setAdapter(mAdapter);

        initDate(pa, "", "", memberId, false);

        toolbar_back.setOnClickListener(mListener);
//        bianji.setOnClickListener(mListener);



        mListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(myguanzhu.this, zhuye.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mlistdate.get(position).getMemberId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



    }


    private void initDate(int pager, String unitType, String keyword, String memberId, final boolean isRefresh) {
        String URL = "http://bobtrip.com/tripcal/api/personCenter/listMyAttention.action?memberId=" + memberId;

        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(myguanzhu.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mbean = gson.fromJson(response, myfans_list.class);
                mlistdateTemp = mbean.getList();
                int length = mlistdateTemp.size();
                if (length > 0) {
                    if (isRefresh) {
                        mlistdate.clear();
                        Iterator<myfans_list.LIstdate> iter = mlistdateTemp.iterator();
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
                    Toast.makeText(myguanzhu.this, "暂无数据", Toast.LENGTH_SHORT).show();

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
        initDate(1, "", "", memberId, false);
    }
}
