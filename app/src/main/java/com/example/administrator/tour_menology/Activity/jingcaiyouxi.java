package com.example.administrator.tour_menology.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Adapter.gonglue_adapter;
import com.example.administrator.tour_menology.Adapter.jingcaiyouji_adapter;
import com.example.administrator.tour_menology.Bean.gonglue_list;
import com.example.administrator.tour_menology.Bean.jingcaiyouji_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.my_fragment_activity.myyouji;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.example.administrator.tour_menology.viewpager.NewViewPager;
import com.example.administrator.tour_menology.viewpager.imgBean;
import com.example.administrator.tour_menology.viewpager.lunboAdapter;
import com.google.gson.Gson;
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

public class jingcaiyouxi extends AppCompatActivity {
    private ImageButton toolback;
    private TextView toolbar_text;
    private NewViewPager mViewPager;
    private lunboAdapter mAdapter;
    private LinearLayout mBottomLayout;
    private Handler mHandler;
    public boolean isRun = false;
    public boolean isDown = false;
    private imgBean mImgBean;
    private List<imgBean.imgs> mList = new ArrayList<>();
    private ImageView imgCur;
    private RelativeLayout hei;
    private static final int SLEEPTIME = 3000;
    private static final String PATH = "http://bobtrip.com/tripcal/api/travel/wonderful.action";

    private GridView mGridView;
    private ListView mListView;

    private List<jingcaiyouji_list> mlist1;
    private RequestQueue mRequestQueue, mRequestQueue2;

    private List<gonglue_list> mList2;
    private RelativeLayout r1, r2;
    private ImageButton add_youji;
    private Button jicai_myyouji;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jingcaiyouxi);


        //获取登录用户信息
        utils = new PreferenceUtils(this);
        Map<String, Object> map = (Map<String, Object>) utils
                .getPreference("login");
        if(map==null || map.size()<1) {
            Toast.makeText(this, "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,
                    denglu.class);
            startActivity(intent);
        } else {
            memberId = map.get("memberId").toString();
        }


        jicai_myyouji=(Button) findViewById(R.id.jicai_myyouji);
        jicai_myyouji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(jingcaiyouxi.this,
                        myyouji.class);
                intent2.putExtra("memberId",memberId);
                startActivity(intent2);
            }
        });
        toolback = (ImageButton) findViewById(R.id.toolbar_back);
        toolback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_text = (TextView) findViewById(R.id.toolbar_title);
        toolbar_text.setText("精彩游记");


//轮播的高度切成比例适配
        hei = (RelativeLayout) findViewById(R.id.hei2);
        DisplayMetrics dm2 = getResources().getDisplayMetrics();

        System.out.println("heigth2 : " + dm2.heightPixels);

        System.out.println("width2 : " + dm2.widthPixels);

        Log.d("hei", String.valueOf(dm2.heightPixels));

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) hei.getLayoutParams(); // 取控件mGrid当前的布局参数
        linearParams.height = dm2.heightPixels / 4;// 当控件的高强制设成高度/4
        hei.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2

        initView();

        initdata();

        initdata2();


        //轮播解析
        OkHttpUtils.get().url(PATH).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                if (!TextUtils.isEmpty(response)) {
                    Gson gson = new Gson();
                    mImgBean = gson.fromJson(response, imgBean.class);
                    mList = mImgBean.getlist();
                }
                setViewPager();
            }
        });

    }

    private void initdata2() {
        String url = "http://bobtrip.com/tripcal/api/strategy/list.action";

        mRequestQueue2 = Volley.newRequestQueue(jingcaiyouxi.this);
        JsonObjectRequest jsonObject = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                mList2 = new ArrayList<>();

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String title = jsonObject1.getString("title");
                        String name = jsonObject1.getString("areaName");
                        String date = jsonObject1.getString("publishDate");
                        String date2 = jsonObject1.getString("days");
                        String img2 = jsonObject1.getString("logo");
                        String id = jsonObject1.getString("strategyId");
                        mList2.add(new gonglue_list(img2, title, name, date2, date, id));

                    }
                    gonglue_adapter adapter = new gonglue_adapter(jingcaiyouxi.this, mList2);

                    mListView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


            }
        });

        mRequestQueue2.add(jsonObject);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(jingcaiyouxi.this, gonglue_xiangqing.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", mList2.get(position).getStrategyId());

                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });


    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.gridView);
        mListView = (ListView) findViewById(R.id.youji_list);
        r1 = (RelativeLayout) findViewById(R.id.youji_R1);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(jingcaiyouxi.this, all_youji.class);
                startActivity(intent);
            }
        });
        r2 = (RelativeLayout) findViewById(R.id.youji_R2);
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(jingcaiyouxi.this, all_gonglue.class);
                startActivity(intent);
            }
        });

        add_youji = (ImageButton) findViewById(R.id.add_youji);
        add_youji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(jingcaiyouxi.this,add_yj.class);
                startActivity(intent);
            }
        });

    }

    private void initdata() {
        String url = "http://bobtrip.com/tripcal/api/travel/recommend.action?currentPage=1";
        mRequestQueue = Volley.newRequestQueue(jingcaiyouxi.this);
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
                        String m = jsonObject1.getString("memberId");
                        mlist1.add(new jingcaiyouji_list(img1, img2, title, name, id,m));

                    }
                    jingcaiyouji_adapter adapter = new jingcaiyouji_adapter(jingcaiyouxi.this, mlist1);

                    mGridView.setAdapter(adapter);
                    setGridViewHeightBasedOnChildren(mGridView);


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


    private void setViewPager() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case 0:
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                        if (isRun && !isDown) {
                            this.sendEmptyMessageDelayed(0, SLEEPTIME);
                        }
                        break;

                    case 1:
                        if (isRun && !isDown) {
                            this.sendEmptyMessageDelayed(0, SLEEPTIME);
                        }
                }
            }
        };
        isRun = true;
        mHandler.sendEmptyMessageDelayed(0, SLEEPTIME);

        mViewPager = (NewViewPager) findViewById(R.id.vp2);
        mBottomLayout = (LinearLayout) findViewById(R.id.ll_points2);
        mAdapter = new lunboAdapter(new MyLoopViewPagerAdatper());
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mViewPager.setInfinateAdapter2(this, mHandler, mAdapter);
        setFaceCurPage(0);

    }


    private class MyLoopViewPagerAdatper extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (View) object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @SuppressWarnings("unused")
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(jingcaiyouxi.this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String url = mList.get(position).getAdImg();
            if (!TextUtils.isEmpty(url)) {
                Glide.with(jingcaiyouxi.this).load(url).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.lunbo).crossFade().into(imageView);
            }
            container.addView(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(jingcaiyouxi.this, Viewxiangqing.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mList.get(position).getLinkUrl());
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
                }
            });
            return imageView;
        }
    }

    public void setFaceCurPage(int page) {
        mBottomLayout.removeAllViews();
        page = page % mList.size();
        for (int i = 0, m = mList.size(); i < m; i++) {
            imgCur = new ImageView(jingcaiyouxi.this);
            imgCur.setBackgroundResource(R.mipmap.dain);
            if (page != i) {
                imgCur.setBackgroundResource(R.mipmap.dian2);
            }
//            Looper looper = Looper.getMainLooper();
//            looper.loop();
            mBottomLayout.addView(imgCur);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imgCur.getLayoutParams());
            lp.setMargins(10, 5, 5, 10);
            lp.height = 10;
            lp.width = 10;
            imgCur.setLayoutParams(lp);
        }
    }


    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * Indicates that the pager is in an idle, settled state. The current
         * page is fully in view and no animation is in progress.
         */
        public static final int SCROLL_STATE_IDLE = 0;

        /**
         * Indicates that the pager is currently being dragged by the user.
         */

        public static final int SCROLL_STATE_DRAGGING = 1;

        /**
         * Indicates that the pager is in the process of settling to a final
         * position.
         */
        public static final int SCROLL_STATE_SETTLING = 2;

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case SCROLL_STATE_IDLE:
                    break;
                case SCROLL_STATE_DRAGGING:
                    break;
                case SCROLL_STATE_SETTLING:
                    break;
            }

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            Log.e("", "" + position);
            setFaceCurPage(position);
        }
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
                + (gridView.getHeight() * (listAdapter.getCount() - 1))) / 2 + 25;
        gridView.setLayoutParams(params);
    }


}
