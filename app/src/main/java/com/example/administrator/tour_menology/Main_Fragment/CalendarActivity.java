package com.example.administrator.tour_menology.Main_Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.tour_menology.Activity.add_shijian;
import com.example.administrator.tour_menology.Activity.add_task;
import com.example.administrator.tour_menology.Activity.ain_list_xiangqing;
import com.example.administrator.tour_menology.Activity.renwuxq;
import com.example.administrator.tour_menology.Activity.shijianxq;
import com.example.administrator.tour_menology.Adapter.tour_adapter2;
import com.example.administrator.tour_menology.Adapter.tour_list_adapter;
import com.example.administrator.tour_menology.Bean.apapter;
import com.example.administrator.tour_menology.Bean.list;
import com.example.administrator.tour_menology.Bean.tour_List2;
import com.example.administrator.tour_menology.Bean.tour_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.Sour_Fragment.CalendarAdapter;
import com.example.administrator.tour_menology.date.ArcMenu;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static android.R.attr.fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarActivity extends Fragment implements View.OnClickListener {

    private TextView toolbar_text;
    private CalendarAdapter calV = null;
    private GestureDetector gestureDetector = null;

    private ViewFlipper flipper = null;
    private GridView gridView = null;
    private static int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
    private static int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private String currentDate = "";
    /**
     * 每次添加gridview到viewflipper中时给的标记
     */
    private int gvFlag = 0;
    /**
     * 当前的年月，现在日历顶端
     */
    private TextView currentMonth;
    /**
     * 上个月
     */
    private ImageView prevMonth;
    /**
     * 下个月
     */
    private ImageView nextMonth;
    //aecmenu
    private ArcMenu mArcMenu;
    //联网请求的工具
    private HttpUtils mHttpUtils;

    private tour_list mbean;
    private List<tour_list.listAct> mListAct = new ArrayList<>();
    private ListView mListView;
    private TextView mTextView;
    private tour_list_adapter mAdapter;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;

    private List<list> mList;


    //点击的日期
    private String clickdate = null;
    private ViewGroup mContainer;
    private boolean mAttachToRoot;

    public CalendarActivity() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date); // 当期日期
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContainer = container;
        mAttachToRoot = false;
        final View view = inflater.inflate(R.layout.fragment_tour, mContainer, mAttachToRoot);
        init(view);
        mTextView = (TextView) view.findViewById(R.id.text);
        initHttpUtils();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);


        initdata(str);
        return view;
    }

    private void initHttpUtils() {

        mHttpUtils = new HttpUtils(5000);//5000-->联网超时时间-->可以无参

        //设置缓存大小
        int size = (int) (Runtime.getRuntime().totalMemory() / 8);
        mHttpUtils.configHttpCacheSize(size);

        //设置编码格式
        mHttpUtils.configResponseTextCharset("UTF-8");

        //设置重连次数,如果链接服务失败,再次尝试
        mHttpUtils.configRequestRetryCount(3);

        //设置多线程迸发(线程池)
        mHttpUtils.configRequestThreadPoolSize(4);

        //设置缓存数据失效的时间(设置30分钟后缓存数据失效)
        mHttpUtils.configDefaultHttpCacheExpiry(1000 * 60 * 30);

    }


    private void initdata(final String date) {
        String url = "http://bobtrip.com/tripcal/api/selfcal/list.action";

        final Map<String, String> params = new HashMap<String, String>();
        params.put("memberId", memberId);
        params.put("date", date);
//        OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e) {
//
//                Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(String response) {
////                if (mbean.getListAct()!=null){
//
//                mListAct.clear();
//                mbean = new Gson().fromJson(response, tour_list.class);
//
//                Iterator<tour_list.listAct> iterAct = mbean.getListAct().iterator();
//                while (iterAct.hasNext())
//                    mListAct.add(iterAct.next());
//
//                mAdapter.notifyDataSetChanged();
//
//                mListView.setSelection(0);
//            }
//            //        else {
//            //            mListView.setEmptyView(mTextView);
//            //        }
//
//        });

        OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {

                try {
                    mList = new ArrayList<list>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("listAct");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String date = jsonObject1.getString("startTime");
                        String date2 = jsonObject1.getString("endTime");
                        String img = jsonObject1.getString("poster");
                        String title = jsonObject1.getString("actName");
                        String id = jsonObject1.getString("activityId");
                        String type = jsonObject1.getString("type");

                        mList.add(new list(date, date2, title, img, id, type));

                    }
                    JSONArray jsonArray2 = jsonObject.getJSONArray("listEvent");
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject jsonObject1 = jsonArray2.getJSONObject(i);
                        String date = jsonObject1.getString("startTime");
                        String date2 = jsonObject1.getString("endTime");
                        String img = jsonObject1.getString("tagIcon");
                        String title = jsonObject1.getString("title");
                        String id = jsonObject1.getString("eventId");
                        String type = jsonObject1.getString("type");

                        mList.add(new list(date, date2, title, img, id, type));
                    }
                    JSONArray jsonArray3 = jsonObject.getJSONArray("listTask");
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        JSONObject jsonObject1 = jsonArray3.getJSONObject(i);
                        String date = jsonObject1.getString("remindTime");
//                        String date2 =jsonObject1.getString("endTime");
                        // String img =jsonObject1.getString("tagIcon");
                        String title = jsonObject1.getString("title");
                        String id = jsonObject1.getString("taskId");
                        String type = jsonObject1.getString("type");

                        mList.add(new list(date, "", title, "", id, type));
                    }

                    apapter madapter = new apapter(getContext(), mList);

                    mListView.setAdapter(madapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList.get(position).getType().toString().equals("0")) {
                    Intent mIntent = new Intent(getContext(), ain_list_xiangqing.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("id", mList.get(position).getId());
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
                } else if (mList.get(position).getType().toString().equals("1")) {
                    Intent mIntent = new Intent(getContext(), shijianxq.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("id", mList.get(position).getId());
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
                } else if (mList.get(position).getType().toString().equals("2")) {
                    Intent mIntent = new Intent(getContext(), renwuxq.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("id", mList.get(position).getId());
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
                }


            }
        });


//        final JSONObject object = new JSONObject(params);
//        mRequestQueue = Volley.newRequestQueue(getContext());
//
//
//        final String map2 = "memberId=297ebc2d56074b5501560b148d23000d&date=2016-6-1";
//
//        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                try {
//                    JSONArray jsonArray = jsonObject.getJSONArray("listAct");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            public byte[] getBody() {
//                try {
//                    return params.toString().getBytes("UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        };
//        mRequestQueue.add(request);


    }


    private void init(View view) {
        //获取登录用户信息
        utils = new PreferenceUtils(getContext());

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        if (map == null || map.size() < 1) {
            Toast.makeText(getContext(), "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(),
                    denglu.class);
            startActivity(intent);

        } else
            memberId = map.get("memberId").toString();


        toolbar_text = (TextView) view.findViewById(R.id.toolbar_title);
        toolbar_text.setText("日历");
        currentMonth = (TextView) view.findViewById(R.id.currentMonth);
        prevMonth = (ImageView) view.findViewById(R.id.prevMonth);
        nextMonth = (ImageView) view.findViewById(R.id.nextMonth);
        setListener();

        gestureDetector = new GestureDetector(getContext(), new MyGestureListener());
        flipper = (ViewFlipper) view.findViewById(R.id.flipper);
        flipper.removeAllViews();
        calV = new CalendarAdapter(getContext(), getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        addGridView();
        gridView.setAdapter(calV);

        flipper.addView(gridView, 0);
        addTextToTopTextView(currentMonth);


        mListView = (ListView) view.findViewById(R.id.tour_list);
        //   mTextView = (TextView) view.findViewById(R.id.tour_text);

//        LinearLayout Listviewhead2 = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.rili_head,mContainer,false);
//
//                mListView.addHeaderView(Listviewhead2);

        mAdapter = new tour_list_adapter(mListAct, getContext());

        mListView.setAdapter(mAdapter);

        mListView.setEmptyView(mTextView);

//        mButton1 = (ImageButton) view.findViewById(R.id.button_yx_1);
//        mButton2 = (ImageButton) view.findViewById(R.id.button_yx_2);

        mArcMenu = (ArcMenu) view.findViewById(R.id.arc_menu);

        mArcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
//                        Toast.makeText(getContext(),
//                                pos + ":" + view.getTag(), Toast.LENGTH_SHORT)
//                                .show();
                switch (view.getId()) {
                    case R.id.button_yx_1:
                        Intent intent = new Intent(getContext(), add_task.class);
                        startActivity(intent);
                        break;
                }


                switch (view.getId()) {
                    case R.id.button_yx_2:
                        Intent intent = new Intent(getContext(), add_shijian.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int gvFlag = 0; // 每次添加gridview到viewflipper中时给的标记
            if (e1.getX() - e2.getX() > 120) {
                // 像左滑动
                enterNextMonth(gvFlag);
                return true;
            } else if (e1.getX() - e2.getX() < -120) {
                // 向右滑动
                enterPrevMonth(gvFlag);
                return true;
            }
            return false;
        }
    }

    /**
     * 移动到下一个月
     *
     * @param gvFlag
     */
    private void enterNextMonth(int gvFlag) {
        addGridView(); // 添加一个gridView
        jumpMonth++; // 下一个月

        calV = new CalendarAdapter(getContext(), this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(calV);
        addTextToTopTextView(currentMonth); // 移动到下一月后，将当月显示在头标题中
        gvFlag++;
        flipper.addView(gridView, gvFlag);
        flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_left_out));
        flipper.showNext();
        flipper.removeViewAt(0);
    }

    /**
     * 移动到上一个月
     *
     * @param gvFlag
     */
    private void enterPrevMonth(int gvFlag) {
        addGridView(); // 添加一个gridView
        jumpMonth--; // 上一个月

        calV = new CalendarAdapter(getContext(), this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(calV);
        gvFlag++;
        addTextToTopTextView(currentMonth); // 移动到上一月后，将当月显示在头标题中
        flipper.addView(gridView, gvFlag);

        flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_right_out));
        flipper.showPrevious();
        flipper.removeViewAt(0);
    }

    /**
     * 添加头部的年份 闰哪月等信息
     *
     * @param view
     */
    public void addTextToTopTextView(TextView view) {
        StringBuffer textDate = new StringBuffer();
        // draw = getResources().getDrawable(R.drawable.top_day);
        // view.setBackgroundDrawable(draw);
        textDate.append(calV.getShowYear()).append("年").append(calV.getShowMonth()).append("月").append("\t");
        view.setText(textDate);
    }


    private void addGridView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        // 取得屏幕的宽度和高度
     /*   WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();*/
        int Width = 48;
        int Height = 48;

        gridView = new GridView(getContext());
        gridView.setNumColumns(7);
        gridView.setColumnWidth(1120);

        // gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        if (Width == 580 && Height == 1000) {
            gridView.setColumnWidth(40);

        }
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        // 去除gridView边框
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            // 将gridview中的触摸事件回传给gestureDetector

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return CalendarActivity.this.gestureDetector.onTouchEvent(event);
            }
        });

        final View[] oldView = new View[1];
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
                int startPosition = calV.getStartPositon();
                int endPosition = calV.getEndPosition();
                if (startPosition <= position + 7 && position <= endPosition - 7) {
                    String scheduleDay = calV.getDateByClickItem(position).split("\\.")[0]; // 这一天的阳历
                    // String scheduleLunarDay =
                    // calV.getDateByClickItem(position).split("\\.")[1];
                    // //这一天的阴历
                    String scheduleYear = calV.getShowYear();
                    String scheduleMonth = calV.getShowMonth();
                    //       Toast.makeText(getContext(), scheduleYear + "-" + scheduleMonth + "-" + scheduleDay, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(CalendarActivity.this, "点击了该条目",
                    clickdate = scheduleYear + "-" + scheduleMonth + "-" + scheduleDay;
                    if (oldView[0] != null)
                        oldView[0].setBackgroundColor(Color.WHITE);
                    arg1.setBackgroundColor(Color.YELLOW);
                    oldView[0] = arg1;
                    initdata(clickdate);
                    // Toast.LENGTH_SHORT).show();
                }
            }
        });
        gridView.setLayoutParams(params);
    }

    private void setListener() {
        prevMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.nextMonth: // 下一个月
                enterNextMonth(gvFlag);
                break;

            case R.id.prevMonth: // 上一个月
                enterPrevMonth(gvFlag);
                break;

            default:
                break;

        }
    }


}
