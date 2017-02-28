package com.example.administrator.tour_menology.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.tour_menology.Adapter.all_youji_adapter;
import com.example.administrator.tour_menology.Adapter.jingcaiyouji_adapter;
import com.example.administrator.tour_menology.Bean.all_youji_list;
import com.example.administrator.tour_menology.Bean.jingcaiyouji_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.MyAsyncTask;
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

public class all_youji extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener {
    private TextView toolbart;
    private ImageButton toolbar;
    private List<jingcaiyouji_list> mlist1;
    private RequestQueue mRequestQueue;
    private GridView mGridView;
    private PullLayout mPullLayout;
    private List<all_youji_list.list> mListDataTemp = new ArrayList<>();
    private List<all_youji_list.list> mListData = new ArrayList<>();
    private all_youji_adapter mAdapter;
    private all_youji_list mBean;

    private int couent = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_youji);

        mGridView = (GridView) findViewById(R.id.all_gridView);
        mPullLayout = (PullLayout) findViewById(R.id.all_youji_pull);
        toolbart = (TextView) findViewById(R.id.toolbar_title);
        toolbart.setText("精彩游记");
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter = new all_youji_adapter(mListData, this);
        mGridView.setAdapter(mAdapter);
        mPullLayout.setOnPullListener(this);
        initvolley(1, "", true);

    }

    private void initvolley(int currentPage, String keyword, final boolean isRefresh) {
        String url = "http://bobtrip.com/tripcal/api/travel/recommend.action?currentPage=" + currentPage + "&keyword=" + keyword;
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(all_youji.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                mBean = new Gson().fromJson(response, all_youji_list.class);
                mListDataTemp = mBean.getList();
                int length = mListDataTemp.size();
                if (length > 0) {
                    if (isRefresh) {
//                        mListData.clear();
                        Iterator<all_youji_list.list> iter = mListDataTemp.iterator();
                        while (iter.hasNext())
                            mListData.add(iter.next());
                        mAdapter.notifyDataSetChanged();
                        mGridView.setSelection(0);
                    } else {
                        int size = mGridView.getFirstVisiblePosition();
                        View view = mGridView.getChildAt(0);
                        int top = view == null ? 0 : view.getTop();
                        mListData.addAll(mListDataTemp);
                        mAdapter.notifyDataSetChanged();
                        mGridView.setSelectionFromTop(size, top - TRIM_MEMORY_COMPLETE);
                    }
                } else {
                    Toast.makeText(all_youji.this, "暂无数据", Toast.LENGTH_SHORT).show();

                }
                mPullLayout.finishPull();
            }
        });
    }

    @Override
    public void onRefresh() {
        initvolley(++couent, "", true);
    }

    @Override
    public void onLoad() {

        initvolley(couent++, "", true);
    }
}
