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

import com.example.administrator.tour_menology.Activity.youjixiangqing;
import com.example.administrator.tour_menology.Adapter.myyouji_adapter;
import com.example.administrator.tour_menology.Adapter.shoucanghuodong_adapter;
import com.example.administrator.tour_menology.Bean.myyouji_list;
import com.example.administrator.tour_menology.Bean.shoucanghuodong_list;
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
 * Created by Administrator on 2016/10/18 0018.
 */
public class myallyouji extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener{

    private ImageButton toolbar_back;
    private TextView toolbar_title;

    private String memberId;
    private PullLayout mPullLayout;
    private ListView mListView3;
    private int pa = 1;
    private myyouji_adapter mAdapter;
    private myyouji_list mbean;
    private List<myyouji_list.LIstdate> mlistdateTemp = new ArrayList<>();
    private List<myyouji_list.LIstdate> mlistdate = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myallyouji);
        //获取上个页面的传值
        Bundle extras = getIntent().getExtras();
        memberId = extras.getString("memberId");
        init();
    }

    public void init() {

        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("我的游记");

        mPullLayout = (PullLayout) findViewById(R.id.myallyouji_pulllayout);
        mPullLayout.setOnPullListener(this);
        mListView3 = (ListView) findViewById(R.id.myallyouji_list);
        mAdapter = new myyouji_adapter(mlistdate, this);
        mListView3.setAdapter(mAdapter);

        initDate(pa, "", "", memberId, false);

        toolbar_back.setOnClickListener(mListener);

        mListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(myallyouji.this, youjixiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mlistdate.get(position).getTravelId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });

    }


    private void initDate(int pager, String unitType, String keyword, String memberId, final boolean isRefresh) {
        // String URL = "http://bobtrip.com/tripcal/api/personCenter/listMyFocus.action?currentPage=" + pager + "&memberId=" + memberId;
      //  String URL = "http://bobtrip.com/tripcal/api/act/listTaStore.action?memberId=" + memberId;
        String URL = "http://bobtrip.com/tripcal/api/travel/my.action?currentPage=" +
                pager + "&memberId=" + memberId;
        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(myallyouji.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mbean = gson.fromJson(response, myyouji_list.class);
                mlistdateTemp = mbean.getList();
                if(mlistdateTemp==null){
                    Toast.makeText(myallyouji.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                int length = mlistdateTemp.size();
                if (length > 0) {
                    if (isRefresh) {
                        mlistdate.clear();
                        Iterator<myyouji_list.LIstdate> iter = mlistdateTemp.iterator();
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
                    Toast.makeText(myallyouji.this, "暂无数据", Toast.LENGTH_SHORT).show();

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
