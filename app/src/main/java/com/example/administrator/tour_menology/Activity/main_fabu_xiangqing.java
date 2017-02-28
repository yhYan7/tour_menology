package com.example.administrator.tour_menology.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.administrator.tour_menology.Adapter.MainAdapter;
import com.example.administrator.tour_menology.Adapter.fabu_huodong_adapter;
import com.example.administrator.tour_menology.Adapter.pinglunAdapter;
import com.example.administrator.tour_menology.Bean.fabu_huodong_list;
import com.example.administrator.tour_menology.Bean.pinglun_list;
import com.example.administrator.tour_menology.Fragment.fabu_huodong_Fragment;
import com.example.administrator.tour_menology.Fragment.fabu_pinglun_Fragment;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.PositiveDialong;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.example.administrator.tour_menology.utils.StringUtils;
import com.google.gson.Gson;
import com.xiaosu.pulllayout.PullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class main_fabu_xiangqing extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener {
    private ImageView img_logo;
    private Button mButton1, mButton2;
    private ImageButton toolbar;
    private TextView title, count, jianjie, toolbartext;
    private ViewPager mPager;
    private MainAdapter mAdapter;
    private String fabuid = null;
    private List<Fragment> mList;
    private RadioButton huodong, pinglun;

    private fabu_huodong_Fragment mFabu_huodong_fragment;
    private fabu_pinglun_Fragment mFbau_pinglun_fragment;
    private RadioGroup mRadioGroup;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private ListView mListView;
    private fabu_huodong_adapter mAdapter_huodong;
    private PullLayout mPullLayout;
    private List<fabu_huodong_list.list> mListDataTemp = new ArrayList<>();
    private List<fabu_huodong_list.list> mListData = new ArrayList<>();
    private String id;
    private fabu_huodong_list mbean;
    private int ca = 1;
    private LinearLayout mLinearLayout;
    private EditText mText;

    private List<pinglun_list> mList3;
    private Button fabiao;
    private static final String ID = "id";


    private ListView mListView3;
    private List<pinglun_list> mList2;
    private RequestQueue mRequestQueue;
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fabu_xiangqing);

        init();
        initdata();

        initdata2(1, true);

        initdata3();

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://bobtrip.com/tripcal/api/unit/canclefocus.action";


                Map<String, String> params = new HashMap<>();
                params.put("memberId", memberId);
                params.put("unitIds", fabuid);
                Log.d("shoucang", fabuid);
                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(main_fabu_xiangqing.this, "取消失败", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(main_fabu_xiangqing.this, "取消关注", Toast.LENGTH_SHORT).show();

                        initdata();
                    }
                });
            }


        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://bobtrip.com/tripcal/api/unit/focus.action";

                Map<String, String> params = new HashMap<>();
                params.put("memberId", memberId);
                params.put("unitIds", fabuid);
                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(main_fabu_xiangqing.this, "关注失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(main_fabu_xiangqing.this, "关注成功", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            strOrig == null || strOrig.equals(null
                            if (jsonObject.getString("recordId") == null
                                    || jsonObject.getString("recordId").equals(null)) {
                                final String recordId = jsonObject.getString("recordId");


                                PositiveDialong dialog = new PositiveDialong(main_fabu_xiangqing.this, "",
                                        new PositiveDialong.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                switch (v.getId()) {
                                                    case R.id.dialog_btn_lq:
                                                        String url = "http://bobtrip.com/tripcal/api/getRewords.action?recordId=" + recordId;
                                                        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                                                                                                       @Override
                                                                                                       public void onError(Call call, Exception e) {

                                                                                                       }

                                                                                                       @Override
                                                                                                       public void onResponse(String response) {
                                                                                                           try {
                                                                                                               JSONObject jsonObject1 = new JSONObject(response);
                                                                                                               String fen = jsonObject1.getString("point");
                                                                                                               Toast.makeText(main_fabu_xiangqing.this, "成功领取" + fen + "分",
                                                                                                                       Toast.LENGTH_SHORT).show();
                                                                                                           } catch (JSONException e) {
                                                                                                               e.printStackTrace();
                                                                                                           }

                                                                                                       }
                                                                                                   }
                                                        );


                                                        break;
                                                    case R.id.dialog_btn:
                                                        break;
                                                }

                                            }
                                        });
                                dialog.show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        initdata();
                    }
                });
            }

        });


        fabu_huodong_Fragment fragment = new fabu_huodong_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("d", "adsgsdfgsdfg");
        fragment.setArguments(bundle);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(main_fabu_xiangqing.this, ain_list_xiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mListData.get(position).getActivityId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });

    }

    private void initdata3() {

        mRequestQueue = Volley.newRequestQueue(main_fabu_xiangqing.this);
        String Url = "http://bobtrip.com/tripcal/api/discuss/list.action?unitId=" + getIntent().getExtras().getString("id") + "&currentPage=1";

        JsonObjectRequest jsonObject = new JsonObjectRequest(Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mList2 = new ArrayList<>();
                try {
                    JSONArray data = jsonObject.getJSONArray("list");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        String content = jsonObject1.getString("content");
                        String img = jsonObject1.getString("headIcon");
                        String title = jsonObject1.getString("nickName");
                        String date = jsonObject1.getString("time");
                        mList2.add(new pinglun_list(content, date, img, title));
                    }

                    pinglunAdapter adapter = new pinglunAdapter(main_fabu_xiangqing.this, mList2);
                    mListView3.setAdapter(adapter);

                    setListViewHeightBasedOnChildren(mListView3);

                    mListView3.setEmptyView(mTextView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mRequestQueue.add(jsonObject);

    }

    private void initdata2(int pager, final boolean isRefresh) {
        String URL = "http://bobtrip.com/tripcal/api/activity/unitlist.action?/memberId=" +
                memberId + "&unitId=" + getIntent().getExtras().getString("id") + "&currentPage=" + pager;

        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(main_fabu_xiangqing.this, "暂无数据", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(String response) {
                mbean = new Gson().fromJson(response, fabu_huodong_list.class);
                mListDataTemp = mbean.getList();
                int length = mListDataTemp.size();

                if (length > 0) {
                    if (isRefresh) {
                        mListData.clear();
                        Iterator<fabu_huodong_list.list> iter = mListDataTemp.iterator();
                        while (iter.hasNext())
                            mListData.add(iter.next());
                        mAdapter_huodong.notifyDataSetChanged();
                        mListView.setSelection(0);
                    } else {
                        //   mListData.addAll(mListDataTemp);
                        //     mAdapter.notifyDataSetChanged();

                        int size = mListView.getFirstVisiblePosition();
                        View view = mListView.getChildAt(0);
                        int top = view == null ? 0 : view.getTop();
                        mListData.addAll(mListDataTemp);
                        mAdapter.notifyDataSetChanged();
                        mListView.setSelectionFromTop(size, top - 1);
                    }
                    mPullLayout.finishPull();
                }
            }
        });
    }

    private void initdata() {
        String url = getIntent().getExtras().getString("id");

        String URL = "http://bobtrip.com/tripcal/api/unit/detail.action?unitId=" + url + "&memberId=" + memberId;

        Log.d("url", URL);
        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject detail = jsonObject.getJSONObject("detail");
                    title.setText(detail.getString("unitName"));
                    count.setText(detail.getString("focus_count"));
                    jianjie.setText(detail.getString("profile"));
                    if (detail.getString("profile") == null)
                        jianjie.setText("暂无简介");
                    fabuid = detail.getString("unitId");

                    boolean guanzhu = detail.getBoolean("focus");
                    if (guanzhu == true) {
                        mButton2.setVisibility(View.GONE);
                        mButton1.setVisibility(View.VISIBLE);
                    } else if (guanzhu == false) {
                        mButton2.setVisibility(View.VISIBLE);
                        mButton1.setVisibility(View.GONE);

                    }

                    Glide.with(getApplicationContext()).load(detail.getString("logo")).into(img_logo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void init() {
        img_logo = (ImageView) findViewById(R.id.main_fabu_xq_img);
        mButton1 = (Button) findViewById(R.id.guanzhu);
        mButton2 = (Button) findViewById(R.id.jguanzhu);
        title = (TextView) findViewById(R.id.main_fabu_xq_title);
        count = (TextView) findViewById(R.id.main_fabu_xq_guanzhu);
        jianjie = (TextView) findViewById(R.id.main_fabu_xq_jianjie);
        toolbartext = (TextView) findViewById(R.id.toolbar_title);
        toolbartext.setText("发布方详情");
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        huodong = (RadioButton) findViewById(R.id.huodong);
        pinglun = (RadioButton) findViewById(R.id.pinglun);
        mLinearLayout = (LinearLayout) findViewById(R.id.pinlun_ll);
//        mPager = (ViewPager) findViewById(R.id.fabu_Viewpager);
        mList = getdate();
//        mAdapter = new MainAdapter(getSupportFragmentManager(), mList);
//        mPager.setAdapter(mAdapter);

//        mPager.addOnPageChangeListener(new OnPageChangeListener());


        mText = (EditText) findViewById(R.id.pinglun_edit);
        fabiao = (Button) findViewById(R.id.pinglun_fab);
        mListView3 = (ListView) findViewById(R.id.pinglun_list);
        mTextView = (TextView) findViewById(R.id.pinglun_text);


        mRadioGroup = (RadioGroup) findViewById(R.id.fabu_radio);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.huodong:
//                        mPager.setCurrentItem(0);
                        mPullLayout.setVisibility(View.VISIBLE);
                        mLinearLayout.setVisibility(View.GONE);
                        break;

                    case R.id.pinglun:
//                        mPager.setCurrentItem(1);
                        mPullLayout.setVisibility(View.GONE);
                        mLinearLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        fabiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://bobtrip.com/tripcal/api/discuss/create.action";

                Map<String, String> params = new HashMap<>();
                params.put("unitId", getIntent().getExtras().getString("id"));
                params.put("currentId", memberId);
                params.put("content", mText.getText().toString());
                Log.d("ping", "content" + mText.getText().toString());
                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(main_fabu_xiangqing.this, "评论失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(main_fabu_xiangqing.this, "评论成功", Toast.LENGTH_SHORT).show();
                        initdata3();
                    }
                });
            }
        });


        mPullLayout = (PullLayout) findViewById(R.id.huodong_pulllayout);
        mPullLayout.setOnPullListener(this);
        mListView = (ListView) findViewById(R.id.huodong_list);
        mAdapter_huodong = new fabu_huodong_adapter(mListData, main_fabu_xiangqing.this);
        mListView.setAdapter(mAdapter_huodong);

        //获取登录用户信息
        utils = new PreferenceUtils(main_fabu_xiangqing.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        if (map == null || map.size() < 1) {
            Toast.makeText(main_fabu_xiangqing.this, "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(main_fabu_xiangqing.this,
                    denglu.class);
            startActivity(intent);

        } else
            memberId = map.get("memberId").toString();

    }

    public List<Fragment> getdate() {
        mList = new ArrayList<Fragment>();

        mFabu_huodong_fragment = fabu_huodong_Fragment.newInstance(getIntent().getExtras().getString("id"));
        mList.add(mFabu_huodong_fragment);

        mFbau_pinglun_fragment = fabu_pinglun_Fragment.newInstance(getIntent().getExtras().getString("id"));
        mList.add(mFbau_pinglun_fragment);


        return mList;
    }

    private class OnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    huodong.setChecked(true);
                    break;
                case 1:
                    pinglun.setChecked(true);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public class ListScrollView extends ScrollView {
        private List list = new ArrayList();
        private int scrollPaddingTop; // scrollview的顶部内边距
        private int scrollPaddingLeft;// scrollview的左侧内边距
        private int[] scrollLoaction = new int[2]; // scrollview在窗口中的位置
        private final static int UPGLIDE = 0;
        private final static int DOWNGLIDE = 1;
        private int glideState;

        public ListScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        private int downY = 0;
        private int moveY = 0;

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downY = (int) ev.getY();
                    //System.out.println("actiondown" + ev.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    moveY = (int) ev.getY();
                    //System.out.println("move" + moveY + "down" + downY);
                    if ((moveY - downY) >= 0) {
                        //System.out.println("'''''''''DOWNGLIDE'''''''''''");
                        glideState = DOWNGLIDE;
                    } else {
                        //System.out.println("'''''''''UPGLIDE'''''''''''");
                        glideState = UPGLIDE;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                default:
                    break;
            }
            return super.dispatchTouchEvent(ev);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            // 该事件的xy是以scrollview的左上角为00点而不是以窗口为00点
            int x = (int) ev.getX() + scrollLoaction[0];
            int y = (int) ev.getY() + scrollLoaction[1];
            for (int i = 0; i < list.size(); i++) {
                ListView listView = (ListView) list.get(i);
                int[] location = new int[2];
                listView.getLocationInWindow(location);
                int width = listView.getWidth();
                int height = listView.getHeight();
                // 在listview的位置之内则可以滑动
                if (x >= location[0] + scrollPaddingLeft
                        && x <= location[0] + scrollPaddingLeft + width
                        && y >= location[1] + scrollPaddingTop
                        && y <= location[1] + scrollPaddingTop + height) {
                    //System.out.println(glideState);
                    if (((listView.getLastVisiblePosition() == (listView.getCount() - 1)) && (glideState == UPGLIDE))) {
                        //System.out.println("up");
                        break;
                    }
                    if (((listView.getFirstVisiblePosition() == 0) && (glideState == DOWNGLIDE))) {
                        //System.out.println("down");
                        break;
                    }
                    return false; //让子控件直接处理
                }
            }
            return super.onInterceptTouchEvent(ev);
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            return super.onTouchEvent(ev);
        }


        private void findAllListView(View view) {
            if (view instanceof ViewGroup) {
                int count = ((ViewGroup) view).getChildCount();
                for (int i = 0; i < count; i++) {
                    if (!(view instanceof ListView)) {
                        findAllListView(((ViewGroup) view).getChildAt(i));
                    }
                }
                if (view instanceof ListView) {
                    list.add((ListView) view);
                }
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            scrollPaddingTop = getTop();
            scrollPaddingLeft = getLeft();
            getLocationInWindow(scrollLoaction);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            if (this.getChildCount() != 1) {

            }
            list.clear();
            findAllListView(this.getChildAt(0));
        }
    }

    /**
     * @param listView
     */
    private void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onRefresh() {
        initdata2(ca++, true);
    }

    @Override
    public void onLoad() {
        initdata2(ca++, true);
    }


}
