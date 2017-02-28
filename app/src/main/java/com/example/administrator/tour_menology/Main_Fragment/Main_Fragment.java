package com.example.administrator.tour_menology.Main_Fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Activity.Viewxiangqing;
import com.example.administrator.tour_menology.Activity.ain_list_xiangqing;
import com.example.administrator.tour_menology.Activity.all_gonglue;
import com.example.administrator.tour_menology.Activity.all_youji;
import com.example.administrator.tour_menology.Activity.gonglue_xiangqing;
import com.example.administrator.tour_menology.Activity.main_fabufang;
import com.example.administrator.tour_menology.Activity.main_huodong_activity;
import com.example.administrator.tour_menology.Activity.main_list2_xiangqing;
import com.example.administrator.tour_menology.Activity.main_zhaohuodong_Activity;
import com.example.administrator.tour_menology.Activity.sousuo_huodong;
import com.example.administrator.tour_menology.Adapter.Main_list2_adapter;
import com.example.administrator.tour_menology.Adapter.gonglue_adapter;
import com.example.administrator.tour_menology.Adapter.jingcaiyouji_adapter;
import com.example.administrator.tour_menology.Adapter.main_list1_adapter;
import com.example.administrator.tour_menology.Bean.gonglue_list;
import com.example.administrator.tour_menology.Bean.jingcaiyouji_list;
import com.example.administrator.tour_menology.Bean.main_list1_list;
import com.example.administrator.tour_menology.Bean.main_list2_List;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.HeiListView;
import com.example.administrator.tour_menology.date.henglistview;
import com.example.administrator.tour_menology.utils.TimeTaskScroll;
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
import java.util.List;
import java.util.Timer;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class Main_Fragment extends Fragment implements OnClickListener {
    private static final String PATH = "http://bobtrip.com/tripcal/api/advert/list.action";
    private NewViewPager mViewPager;
    private lunboAdapter mAdapter;
    public boolean isRun = false;
    public boolean isDown = false;
    private static final int SLEEPTIME = 3000;
    private LinearLayout mBottomLayout;
    private ImageView imgCur;
    private imgBean mImgBean;
    private Handler mHandler;
    private List<imgBean.imgs> mList = new ArrayList<>();
    private TextView toolbar_text;
    private ListView main_list_1, main_list_2;
    private RequestQueue mRequestQueue, mRequestQueue2;
    private List<main_list2_List> mList2;
    private List<main_list1_list> mList1;
    private String URL = "http://bobtrip.com/tripcal/api/news/list.action";
    private String URL_list1 = "http://bobtrip.com/tripcal/api/activity/indexlist.action";
    private RelativeLayout r1, r2, r3, r6, r7, re_tuijian;
    private RelativeLayout hei;


    private List<jingcaiyouji_list> mlist1;
    private GridView mGridView;
    private ListView mListView;
    private List<gonglue_list> mList3;

    private List<String> list;
    private ImageButton sousuo;
    private ScrollView mSl;

    public Main_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        init(view);
        initdata();
        initdata2();
        initVolley();
        initVolley2();
        initClick();
        initClick2();


        sousuo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(), sousuo_huodong.class);
                startActivity(intent1);
            }
        });

        hei = (RelativeLayout) view.findViewById(R.id.hei);
        DisplayMetrics dm2 = getResources().getDisplayMetrics();

        System.out.println("heigth2 : " + dm2.heightPixels);

        System.out.println("width2 : " + dm2.widthPixels);

        Log.d("hei", String.valueOf(dm2.heightPixels));

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) hei.getLayoutParams(); // 取控件mGrid当前的布局参数
        linearParams.height = dm2.heightPixels / 3;// 当控件的高强制设成75象素
        hei.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2


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
                setViewPager(view);
            }
        });
        return view;
    }

    private void initClick2() {
        main_list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getContext(), ain_list_xiangqing.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", mList1.get(position).getActivityId());
                //      bundle.putBoolean("guanzhu", mList1.get(position).getCollect());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });
    }

    private void initVolley2() {
        mRequestQueue2 = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObject = new JsonObjectRequest(URL_list1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mList1 = new ArrayList<>();
                try {
                    JSONArray list2 = jsonObject.getJSONArray("list");
                    for (int i = 0; i < list2.length(); i++) {
                        JSONObject jsonObject11 = list2.getJSONObject(i);
                        String id = jsonObject11.getString("activityId");
                        String address = jsonObject11.getString("address");
                        String title = jsonObject11.getString("actName");
                        String date = jsonObject11.getString("actDateStr");
                        String img = jsonObject11.getString("poster");
                        String guanzhu = jsonObject11.getString("collect_count");
                        boolean shifou = jsonObject11.getBoolean("collect");
                        mList1.add(new main_list1_list(id, date, address, img, guanzhu, title, shifou));
                    }
                    main_list1_adapter adapter = new main_list1_adapter(getContext(), mList1);
                    main_list_1.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(main_list_1);

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
    }

    private void initClick() {
        main_list_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getContext(), main_list2_xiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mList2.get(position).getNewsId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });
    }

    private Handler handler;

    private void initVolley() {
        mRequestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObject = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                mList2 = new ArrayList<>();
                try {
                    JSONArray list = jsonObject.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject1 = list.getJSONObject(i);
                        String id = jsonObject1.getString("newsId");
                        String address = jsonObject1.getString("source");
                        String title = jsonObject1.getString("title");
                        String date = jsonObject1.getString("pubTime");
                        mList2.add(new main_list2_List(id, title, date, address));
                    }
                    Main_list2_adapter adapter = new Main_list2_adapter(getContext(), mList2);
                    main_list_2.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(main_list_2);
                    new Timer().schedule(new TimeTaskScroll(getContext(), main_list_2, mList2), 50, 100);

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

    private void init(View view) {

        mGridView = (GridView) view.findViewById(R.id.gridView);
        mListView = (ListView) view.findViewById(R.id.youji_list);

        toolbar_text = (TextView) view.findViewById(R.id.toolbar_title);
        toolbar_text.setText("旅行日历");
        main_list_2 = (ListView) view.findViewById(R.id.main_list_zx);
        mSl = (ScrollView) view.findViewById(R.id.sl);
        //解决scrollview和listview的滚动问题
        sousuo = (ImageButton) view.findViewById(R.id.chaxun_img);
        main_list_2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mSl.requestDisallowInterceptTouchEvent(true);

                            mSl.requestDisallowInterceptTouchEvent(true);
                            break;
                        case MotionEvent.ACTION_UP:
                            mSl.requestDisallowInterceptTouchEvent(false);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        main_list_1 = (ListView) view.findViewById(R.id.main_list_1);
        r1 = (RelativeLayout) view.findViewById(R.id.main_r1);
        r2 = (RelativeLayout) view.findViewById(R.id.main_r2);
        r3 = (RelativeLayout) view.findViewById(R.id.main_r3);
        r1.setOnClickListener(this);
        r2.setOnClickListener(this);
        r3.setOnClickListener(this);

        re_tuijian = (RelativeLayout) view.findViewById(R.id.re_tuijian);
        re_tuijian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), main_zhaohuodong_Activity.class);
                startActivity(intent);
            }
        });
        r6 = (RelativeLayout) view.findViewById(R.id.youji_R1);
        r6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), all_youji.class);
                startActivity(intent);
            }
        });
        r7 = (RelativeLayout) view.findViewById(R.id.youji_R2);
        r7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), all_gonglue.class);
                startActivity(intent);
            }
        });

    }

    private void setViewPager(View view) {
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

        mViewPager = (NewViewPager) view.findViewById(R.id.vp);
        mBottomLayout = (LinearLayout) view.findViewById(R.id.ll_points);
        mAdapter = new lunboAdapter(new MyLoopViewPagerAdatper());
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mViewPager.setInfinateAdapter(this, mHandler, mAdapter);
        setFaceCurPage(0);


    }


    //推荐游记
    private void initdata() {
        String url = "http://bobtrip.com/tripcal/api/travel/recommend.action?currentPage=1";
        mRequestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObject = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                mlist1 = new ArrayList<>();

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    //       for (int i = 0; i < jsonArray.length(); i++) {
                    for (int i = 0; i < 2; i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String title = jsonObject1.getString("title");
                        String name = jsonObject1.getString("nickName");
                        String img1 = jsonObject1.getString("logo");
                        String img2 = jsonObject1.getString("headIcon");
                        String id = jsonObject1.getString("travelId");
                        String m = jsonObject1.getString("memberId");
                        mlist1.add(new jingcaiyouji_list(img1, img2, title, name, id, m));

                    }
                    jingcaiyouji_adapter adapter = new jingcaiyouji_adapter(getActivity(), mlist1);

                    mGridView.setAdapter(adapter);
                    //  setGridViewHeightBasedOnChildren(mGridView);


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


    //原创攻略
    private void initdata2() {
        String url = "http://bobtrip.com/tripcal/api/strategy/list.action";

        mRequestQueue2 = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObject = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                mList3 = new ArrayList<>();

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
                        mList3.add(new gonglue_list(img2, title, name, date2, date, id));

                    }
                    gonglue_adapter adapter = new gonglue_adapter(getActivity(), mList3);

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
                Intent mIntent = new Intent(getActivity(), gonglue_xiangqing.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", mList3.get(position).getStrategyId());

                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_r1:
                Intent intent = new Intent(getContext(), main_huodong_activity.class);
                startActivity(intent);
                break;

            case R.id.main_r2:
                Intent intent1 = new Intent(getContext(), main_zhaohuodong_Activity.class);
                startActivity(intent1);
                break;

            case R.id.main_r3:
                Intent intent3 = new Intent(getContext(), main_fabufang.class);
                startActivity(intent3);
                break;
        }
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
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String url = mList.get(position).getAdImg();
            if (!TextUtils.isEmpty(url)) {
                Glide.with(Main_Fragment.this).load(url).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.lunbo).crossFade().into(imageView);
            }
            container.addView(imageView);

            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(getContext(), Viewxiangqing.class);
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
            imgCur = new ImageView(getContext());
            imgCur.setBackgroundResource(R.mipmap.dain);
            if (page != i) {
                imgCur.setBackgroundResource(R.mipmap.dian2);
            }
//            Looper looper = Looper.getMainLooper();
//            looper.loop();
            mBottomLayout.addView(imgCur);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imgCur.getLayoutParams());
            lp.setMargins(10, 5, 5, 10);
            lp.width = 20;
            lp.height = 20;
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
}
