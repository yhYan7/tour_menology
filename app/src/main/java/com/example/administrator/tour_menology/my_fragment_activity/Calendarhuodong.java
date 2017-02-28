package com.example.administrator.tour_menology.my_fragment_activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.administrator.tour_menology.Activity.add_task;
import com.example.administrator.tour_menology.Activity.ain_list_xiangqing;
import com.example.administrator.tour_menology.Adapter.tour_list_adapter;
import com.example.administrator.tour_menology.Bean.tour_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.Sour_Fragment.CalendarAdapter;
import com.example.administrator.tour_menology.date.ArcMenu;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class Calendarhuodong extends AppCompatActivity implements View.OnClickListener {

    private ImageButton toolbar_back;
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


    //点击的日期
    private String clickdate = null;

    public Calendarhuodong() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date); // 当期日期
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendarhuodong);
        mTextView = (TextView) findViewById(R.id.text);
        init();
        initHttpUtils();
    }

 /*   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.calendarhuodong, container, false);
        init(view);
        mTextView = (TextView) view.findViewById(R.id.text);
        initHttpUtils();
//        initdata(clickdate);
        return view;
    }*/

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
        OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

                Toast.makeText(Calendarhuodong.this, "没有数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
//                if (mbean.getListAct()!=null){

                mListAct.clear();
                mbean = new Gson().fromJson(response, tour_list.class);

                Iterator<tour_list.listAct> iterAct = mbean.getListAct().iterator();
                while (iterAct.hasNext())
                    mListAct.add(iterAct.next());

                mAdapter.notifyDataSetChanged();

                mListView.setSelection(0);
            }
            //        else {
            //            mListView.setEmptyView(mTextView);
            //        }

        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(Calendarhuodong.this, ain_list_xiangqing.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", mListAct.get(position).getActivityId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });

    }


    private void init() {
        //获取登录用户信息
        utils = new PreferenceUtils(Calendarhuodong.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        if (map == null || map.size() < 1) {
            Toast.makeText(Calendarhuodong.this, "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Calendarhuodong.this,
                    denglu.class);
            startActivity(intent);

        } else
            memberId = map.get("memberId").toString();

        toolbar_back = (ImageButton)findViewById(R.id.toolbar_back);
        toolbar_text = (TextView) findViewById(R.id.toolbar_title);
        toolbar_text.setText("日历");
        currentMonth = (TextView) findViewById(R.id.currentMonth);
        prevMonth = (ImageView) findViewById(R.id.prevMonth);
        nextMonth = (ImageView) findViewById(R.id.nextMonth);
        setListener();

        gestureDetector = new GestureDetector(Calendarhuodong.this, new MyGestureListener());
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.removeAllViews();
        calV = new CalendarAdapter(Calendarhuodong.this, getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        addGridView();
        gridView.setAdapter(calV);
        flipper.addView(gridView, 0);
        addTextToTopTextView(currentMonth);


        mListView = (ListView) findViewById(R.id.tour_list);
        //   mTextView = (TextView) view.findViewById(R.id.tour_text);
        mAdapter = new tour_list_adapter(mListAct, Calendarhuodong.this);

        mListView.setAdapter(mAdapter);

        mListView.setEmptyView(mTextView);

//        mButton1 = (ImageButton) view.findViewById(R.id.button_yx_1);
//        mButton2 = (ImageButton) view.findViewById(R.id.button_yx_2);

        mArcMenu = (ArcMenu) findViewById(R.id.arc_menu);

        mArcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
//                        Toast.makeText(getContext(),
//                                pos + ":" + view.getTag(), Toast.LENGTH_SHORT)
//                                .show();
                switch (view.getId()) {
                    case R.id.button_yx_1:
                        Intent intent = new Intent(Calendarhuodong.this, add_task.class);
                        startActivity(intent);
                        break;
                }


                switch (view.getId()) {
                    case R.id.button_yx_2:
                        Intent intent = new Intent(Calendarhuodong.this, add_task.class);
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

        calV = new CalendarAdapter(Calendarhuodong.this, this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(calV);
        addTextToTopTextView(currentMonth); // 移动到下一月后，将当月显示在头标题中
        gvFlag++;
        flipper.addView(gridView, gvFlag);
        flipper.setInAnimation(AnimationUtils.loadAnimation(Calendarhuodong.this, R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(Calendarhuodong.this, R.anim.push_left_out));
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

        calV = new CalendarAdapter(Calendarhuodong.this, this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(calV);
        gvFlag++;
        addTextToTopTextView(currentMonth); // 移动到上一月后，将当月显示在头标题中
        flipper.addView(gridView, gvFlag);

        flipper.setInAnimation(AnimationUtils.loadAnimation(Calendarhuodong.this, R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(Calendarhuodong.this, R.anim.push_right_out));
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

        gridView = new GridView(Calendarhuodong.this);
        gridView.setNumColumns(7);
        gridView.setColumnWidth(40);
        // gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        if (Width == 720 && Height == 1280) {
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
                return Calendarhuodong.this.gestureDetector.onTouchEvent(event);
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
        toolbar_back.setOnClickListener(this);
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
            case R.id.toolbar_back:
                finish();
                break;
            default:
                break;

        }
    }


}
