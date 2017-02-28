package com.example.administrator.tour_menology.my_fragment_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.Adapter.mydongtai_adapter;
import com.example.administrator.tour_menology.Bean.mydongtai_list;
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
 * Created by Administrator on 2016/9/29 0029.
 */
public class haoyoudongtai extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener{

    private ImageButton toolbar_back;
    private TextView toolbar_title;

    private String memberId;
    private PullLayout mPullLayout;
    private ListView mListView3;
    private mydongtai_list mbean;
    private int pa = 1;
    private mydongtai_adapter mAdapter;
    private List<mydongtai_list.LIstdate> mlistdateTemp = new ArrayList<>();
    private List<mydongtai_list.LIstdate> mlistdate = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haoyoudongtai);
        //获取上个页面的传值
        Bundle extras = getIntent().getExtras();
        memberId = extras.getString("memberId");
        init();
    }

    public void init(){
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("好友动态");
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);


        mPullLayout = (PullLayout) findViewById(R.id.mydongtai_pulllayout);
        mPullLayout.setOnPullListener(this);
        mListView3 = (ListView) findViewById(R.id.mydongtai_list);
        mAdapter = new mydongtai_adapter(mlistdate, this);
        mListView3.setAdapter(mAdapter);

        initDate(pa, "", "", memberId, false);

        toolbar_back.setOnClickListener(mListener);


    }


    private void initDate(int pager, String unitType, String keyword, String memberId, final boolean isRefresh) {
        String URL = "http://bobtrip.com/tripcal/api/dynamic/list.action?memberId=" + memberId;

        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(haoyoudongtai.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mbean = gson.fromJson(response, mydongtai_list.class);
                mlistdateTemp = mbean.getList();
                int length = mlistdateTemp.size();
                if (length > 0) {
                    if (isRefresh) {
                        mlistdate.clear();
                        Iterator<mydongtai_list.LIstdate> iter = mlistdateTemp.iterator();
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
                    Toast.makeText(haoyoudongtai.this, "暂无数据", Toast.LENGTH_SHORT).show();

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
        initDate(pa++, "", "", memberId, true);
    }

    @Override
    public void onLoad() {
        pa++;
        initDate(pa, "", "", memberId, false);
    }

}
