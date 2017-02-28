package com.example.administrator.tour_menology.my_fragment_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.administrator.tour_menology.Adapter.haoyouyj_adapter;
import com.example.administrator.tour_menology.Bean.haoyouyj_list;
import com.example.administrator.tour_menology.Bean.jingcaiyouji_list;
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
public class allhaoyouyj extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener{


    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private String memberId;
    private PullLayout mPullLayout;


    private GridView mGridView;
    private RequestQueue mRequestQueue;
    private List<jingcaiyouji_list> mlist1;
    private ListView mListView3;
    private haoyouyj_list mbean;
    private int pa = 1;
    private haoyouyj_adapter mAdapter;
    private List<haoyouyj_list.LIstdate> mlistdateTemp = new ArrayList<>();
    private List<haoyouyj_list.LIstdate> mlistdate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allhaoyouyj);
        //获取上个页面的传值
        Bundle extras = getIntent().getExtras();
        memberId = extras.getString("memberId");
        init();
    }


    public void init(){
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("好友游记");

        mPullLayout = (PullLayout) findViewById(R.id.allhaoyouyj_pulllayout);
        mPullLayout.setOnPullListener(this);
        mListView3 = (ListView) findViewById(R.id.allhaoyouyj_list);
        mAdapter = new haoyouyj_adapter(mlistdate, this);
        mListView3.setAdapter(mAdapter);

        toolbar_back.setOnClickListener(mListener);

        initdata(pa, memberId, false);


    }

    //listview方法展示
    private void initdata(int pager, String memberId, final boolean isRefresh) {
        // String URL = "http://bobtrip.com/tripcal/api/personCenter/listMyFocus.action?currentPage=" + pager + "&memberId=" + memberId;
        String URL = "http://bobtrip.com/tripcal/api/travel/friend.action?currentPage="+pager+"&memberId="+memberId;

        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(allhaoyouyj.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mbean = gson.fromJson(response, haoyouyj_list.class);
                mlistdateTemp = mbean.getList();
                if(mlistdateTemp==null){
                    Toast.makeText(allhaoyouyj.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                int length = mlistdateTemp.size();
                if (length > 0) {
                    if (isRefresh) {
                        mlistdate.clear();
                        Iterator<haoyouyj_list.LIstdate> iter = mlistdateTemp.iterator();
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
                    Toast.makeText(allhaoyouyj.this, "暂无数据", Toast.LENGTH_SHORT).show();

                mPullLayout.finishPull();
            }

        });
    }










//gridview方法展示
  /*  private void initdata(int pager,String memberId,final boolean isRefresh) {
        String url = "http://bobtrip.com/tripcal/api/travel/friend.action?currentPage="+pager+"&memberId="+memberId;
        mRequestQueue = Volley.newRequestQueue(allhaoyouyj.this);

        JsonObjectRequest jsonObject = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                mlist1 = new ArrayList<>();

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String title = jsonObject1.getString("title");
                        String name = jsonObject1.getString("nickName");
                        String img1 = jsonObject1.getString("logo");
                        String img2 = jsonObject1.getString("headIcon");
                        String id = jsonObject1.getString("travelId");
                        mlist1.add(new jingcaiyouji_list(img1, img2, title, name, id));

                    }
                    jingcaiyouji_adapter adapter = new jingcaiyouji_adapter(allhaoyouyj.this, mlist1);

                    mGridView.setAdapter(adapter);
                    setGridViewHeightBasedOnChildren(mGridView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            //    myyj_text2.setText("暂无好友游记");

            }
        });

        mRequestQueue.add(jsonObject);
    }

*/
    /**
     * @param gridView
     */
    private void setGridViewHeightBasedOnChildren(GridView gridView) {

        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = (totalHeight
                + (gridView.getHeight() * (listAdapter.getCount() - 1))) / 2 + 55;
        gridView.setLayoutParams(params);
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
        initdata(1, memberId, true);
    }

    @Override
    public void onLoad() {
        pa++;
        initdata(pa, memberId, false);
    }



}
