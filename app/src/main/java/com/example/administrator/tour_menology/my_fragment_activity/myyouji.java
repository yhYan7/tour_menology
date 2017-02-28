package com.example.administrator.tour_menology.my_fragment_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.tour_menology.Activity.bianjiyj;
import com.example.administrator.tour_menology.Activity.jingcaiyouxi;
import com.example.administrator.tour_menology.Activity.youjixiangqing;
import com.example.administrator.tour_menology.Adapter.jingcaiyouji_adapter;
import com.example.administrator.tour_menology.Adapter.myyouji_adapter;
import com.example.administrator.tour_menology.Bean.jingcaiyouji_list;
import com.example.administrator.tour_menology.Bean.myyouji_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class myyouji extends AppCompatActivity {

    private ImageButton toolbar_back;
    private TextView toolbar_title,myyj_text1,myyj_text2;
    private RelativeLayout myyj_re1,myyj_re2;
    private int couent = 1;
    private HttpUtils mHttpUtils;
    private ListView mListView;
    private myyouji_adapter mAdapter;
    private myyouji_list mbean;
    private List<myyouji_list.LIstdate> mListDataTemp = new ArrayList<>();
    private List<myyouji_list.LIstdate> mListData = new ArrayList<>();

    private GridView mGridView;
    private RequestQueue mRequestQueue, mRequestQueue2;
    private List<jingcaiyouji_list> mlist1;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myyj);
        //获取上个页面的传值
//        Bundle extras = getIntent().getExtras();
//        memberId = extras.getString("memberId");

        //获取登录用户信息
        utils = new PreferenceUtils(myyouji.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();


        init();
    }


    public void init() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("我的游记");
        myyj_re1 = (RelativeLayout) findViewById(R.id.myyj_re1);
        myyj_re2 = (RelativeLayout) findViewById(R.id.myyj_re2);
        myyj_text1 =(TextView) findViewById(R.id.myyj_text1);
        myyj_text2 = (TextView) findViewById(R.id.myyj_text2);
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_back.setOnClickListener(mListener);
        myyj_re1.setOnClickListener(mListener);
        myyj_re2.setOnClickListener(mListener);

        mListView = (ListView) findViewById(R.id.myyj_mylist);
        mAdapter = new myyouji_adapter(mListData, myyouji.this);
        mListView.setAdapter(mAdapter);

        mGridView = (GridView) findViewById(R.id.myyouji_gridView);

        initData2(1, memberId, "","" ,"", true);

        initdata();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(myyouji.this, bianjiyj.class);
                Bundle bundle = new Bundle();
                bundle.putString("travelId", mListData.get(position).getTravelId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });
    }



    private void initdata() {
        String url = "http://bobtrip.com/tripcal/api/travel/friend.action?currentPage=1&memberId="+memberId;
        mRequestQueue = Volley.newRequestQueue(myyouji.this);
        JsonObjectRequest jsonObject = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                mlist1 = new ArrayList<>();

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //   for (int i = 0; i < 3; i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String title = jsonObject1.getString("title");
                        String name = jsonObject1.getString("nickName");
                        String img1 = jsonObject1.getString("logo");
                        String img2 = jsonObject1.getString("headIcon");
                        String id = jsonObject1.getString("travelId");
                        String m = jsonObject1.getString("memberId");
                        mlist1.add(new jingcaiyouji_list(img1, img2, title, name, id,m));

                    }
                    jingcaiyouji_adapter adapter = new jingcaiyouji_adapter(myyouji.this, mlist1);

                    mGridView.setAdapter(adapter);
                    setGridViewHeightBasedOnChildren(mGridView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                myyj_text2.setText("暂无好友游记");

            }
        });

        mRequestQueue.add(jsonObject);
    }

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





    //获取我的游记方法
    private void initData2(int current, String memberId, String time,String themeId, String keyword, final boolean isRefresh) {
        String url = "http://bobtrip.com/tripcal/api/travel/my.action?currentPage=" +
                current + "&memberId=" + memberId;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
              /*  mPullLayout.finishPull();
                Toast.makeText(myshoucang.this, "暂无数据", Toast.LENGTH_SHORT).show();*/
                myyj_text1.setText("暂无我的精彩游记");

            }

            @Override
            public void onResponse(String response) {
                mbean = new Gson().fromJson(response, myyouji_list.class);
                mListDataTemp = mbean.getList();
                int length = mListDataTemp.size();

                if (length > 0) {
                    if (isRefresh) {
                        mListData.clear();
                        Iterator<myyouji_list.LIstdate> iter = mListDataTemp.iterator();
                        Log.i("", "====我的游记走不走3-->>"+iter);
                        while (iter.hasNext())
                            mListData.add(iter.next());
                        mAdapter.notifyDataSetChanged();
                        mListView.setSelection(0);
                    } else {

                        //   mListData.addAll(mListDataTemp);
                        //   mAdapter.notifyDataSetChanged();
                        int size = mListView.getFirstVisiblePosition();
                        View view = mListView.getChildAt(0);
                        int top = view == null ? 0 : view.getTop();
                        mListData.addAll(mListDataTemp);
                        mAdapter.notifyDataSetChanged();
                        mListView.setSelectionFromTop(size, top - 1);
                    }
                } else {
                    // Toast.makeText(myyouji.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    myyj_text1.setText("暂无我的精彩游记");
                }
            }
        });

    }







    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.toolbar_back:
                    finish();
                    break;

                //我的游记
                case R.id.myyj_re1:

                    Intent intent1 = new Intent(myyouji.this,
                            myallyouji.class);
                    intent1.putExtra("memberId",memberId);
                    startActivity(intent1);
                    break;

                //好友游记
                case R.id.myyj_re2:
                    Intent intent2 = new Intent(myyouji.this,
                            allhaoyouyj.class);
                    intent2.putExtra("memberId",memberId);
                    startActivity(intent2);
                    break;
                default:
                    break;

            }
        }
    };

}
