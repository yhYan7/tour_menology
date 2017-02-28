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

import com.example.administrator.tour_menology.Activity.main_fabu_xiangqing;
import com.example.administrator.tour_menology.Adapter.main_fabufang_adapter;
import com.example.administrator.tour_menology.Adapter.shequdingyue_adapter;
import com.example.administrator.tour_menology.Bean.main_fabu_list;
import com.example.administrator.tour_menology.Bean.shequdingyue_list;
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
 * Created by Administrator on 2016/8/25 0025.
 */
public class myshequ extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener{

    private ImageButton toolbar_back;
    private Button bianji;
    private TextView toolbar_title;

    private String memberId;
    private PullLayout mPullLayout;
    private ListView mListView3;
    private shequdingyue_list mbean;
    private int pa = 1;
    private shequdingyue_adapter mAdapter;
    private List<shequdingyue_list.LIstdate> mlistdateTemp = new ArrayList<>();
    private List<shequdingyue_list.LIstdate> mlistdate = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myshequ);
        //获取上个页面的传值
        Bundle extras = getIntent().getExtras();
        memberId = extras.getString("memberId");
        init();
    }

    public void init(){
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("社区关注");

        mPullLayout = (PullLayout) findViewById(R.id.my_shequ_pulllayout);
        mPullLayout.setOnPullListener(this);
        mListView3 = (ListView) findViewById(R.id.myshequ_list);
        mAdapter = new shequdingyue_adapter(mlistdate, this);
        mListView3.setAdapter(mAdapter);

        initDate(pa, "", "", memberId, false);

        toolbar_back.setOnClickListener(mListener);


        mListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(myshequ.this, main_fabu_xiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mlistdate.get(position).getUnitId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
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
        String URL = "http://bobtrip.com/tripcal/api/personCenter/listMyFocus.action?memberId=" + memberId;

        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(myshequ.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mbean = gson.fromJson(response, shequdingyue_list.class);
                mlistdateTemp = mbean.getList();
                int length = mlistdateTemp.size();
                if (length > 0) {
                    if (isRefresh) {
                        mlistdate.clear();
                        Iterator<shequdingyue_list.LIstdate> iter = mlistdateTemp.iterator();
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
                    Toast.makeText(myshequ.this, "暂无数据", Toast.LENGTH_SHORT).show();

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
        pa++;
        initDate(1, "", "", memberId, false);
    }
}
