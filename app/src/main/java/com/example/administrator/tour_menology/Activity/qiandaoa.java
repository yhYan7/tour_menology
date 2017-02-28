package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.PositiveDialong;
import com.example.administrator.tour_menology.my_fragment_activity.myyouji;
import com.example.administrator.tour_menology.qiandao.DBManager;
import com.example.administrator.tour_menology.qiandao.SignCalendar;
import com.example.administrator.tour_menology.qiandao.sqlit;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class qiandaoa extends AppCompatActivity {

    private String date = null;// 设置默认选中的日期  格式为 “2014-04-05” 标准DATE格式
    private TextView popupwindow_calendar_month;
    private SignCalendar calendar;
    private Button btn_signIn;
    private List<String> list = new ArrayList<String>(); //设置标记列表
    DBManager dbManager;
    boolean isinput=false;
    private String date1 = null;//单天日期

    private ImageButton toolbar;
    private TextView tool;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private PositiveDialong dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiandao);// 初始化DBManager
        dbManager = new DBManager(this);


        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //获取登录用户信息
        utils = new PreferenceUtils(qiandaoa.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();




        tool = (TextView) findViewById(R.id.toolbar_title);
        tool.setText("签到");


        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        date1 =formatter.format(curDate);

        popupwindow_calendar_month = (TextView) findViewById(R.id.popupwindow_calendar_month);
        btn_signIn = (Button) findViewById(R.id.btn_signIn);
        calendar = (SignCalendar) findViewById(R.id.popupwindow_calendar);
        popupwindow_calendar_month.setText(calendar.getCalendarYear() + "年"
                + calendar.getCalendarMonth() + "月");
        if (null != date) {
            int years = Integer.parseInt(date.substring(0,
                    date.indexOf("-")));
            int month = Integer.parseInt(date.substring(
                    date.indexOf("-") + 1, date.lastIndexOf("-")));
            popupwindow_calendar_month.setText(years + "年" + month + "月");

            calendar.showCalendar(years, month);
            calendar.setCalendarDayBgColor(date,
                    R.drawable.calendar_date_focused);
        }

        add("2015-11-10");
        add("2015-11-02");
        add("2015-12-02");
        query();
        if(isinput){
            btn_signIn.setText("今日已签，明日继续");
            btn_signIn.setBackgroundResource(R.drawable.button_gray);
            btn_signIn.setEnabled(false);
        }
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date today= calendar.getThisday();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
           /* calendar.removeAllMarks();
           list.add(df.format(today));
           calendar.addMarks(list, 0);*/
                //将当前日期标示出来
                add(df.format(today));
                //calendar.addMark(today, 0);
                query();
                HashMap<String, Integer> bg = new HashMap<String, Integer>();

                calendar.setCalendarDayBgColor(today, R.drawable.bg_sign_today);
                btn_signIn.setText("今日已签，明日继续");
                btn_signIn.setBackgroundResource(R.drawable.button_gray);
                btn_signIn.setEnabled(false);


                String url = "http://bobtrip.com/tripcal/api/signin/save.action";
                OkHttpUtils.post().url(url).addParams("memberId",memberId).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            final int tian =jsonObject.getInt("result");

                            dialog = new PositiveDialong(qiandaoa.this, "",
                                    new PositiveDialong.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            switch (v.getId()) {
                                                case R.id.dialog_btn_lq:
                                                    if (tian<5){
                                                        Toast.makeText(qiandaoa.this, "成功领取" + 5 + "分",
                                                                Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        Toast.makeText(qiandaoa.this, "成功领取" + 10 + "分",
                                                                Toast.LENGTH_SHORT).show();
                                                    }




                                                    break;
                                                case R.id.dialog_btn:
                                                    break;
                                            }

                                        }
                                    });
                            dialog.show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });




            }
        });
        //监听所选中的日期
//		calendar.setOnCalendarClickListener(new OnCalendarClickListener() {
//
//			public void onCalendarClick(int row, int col, String dateFormat) {
//				int month = Integer.parseInt(dateFormat.substring(
//						dateFormat.indexOf("-") + 1,
//						dateFormat.lastIndexOf("-")));
//
//				if (calendar.getCalendarMonth() - month == 1//跨年跳转
//						|| calendar.getCalendarMonth() - month == -11) {
//					calendar.lastMonth();
//
//				} else if (month - calendar.getCalendarMonth() == 1 //跨年跳转
//						|| month - calendar.getCalendarMonth() == -11) {
//					calendar.nextMonth();
//
//				} else {
//					list.add(dateFormat);
//					calendar.addMarks(list, 0);
//					calendar.removeAllBgColor();
//					calendar.setCalendarDayBgColor(dateFormat,
//							R.drawable.calendar_date_focused);
//					date = dateFormat;//最后返回给全局 date
//				}
//			}
//		});

        //监听当前月份
        calendar.setOnCalendarDateChangedListener(new SignCalendar.OnCalendarDateChangedListener() {
            public void onCalendarDateChanged(int year, int month) {
                popupwindow_calendar_month
                        .setText(year + "年" + month + "月");
            }
        });
    }

    public void add(String date)
    {
        ArrayList<sqlit> persons = new ArrayList<sqlit>();

        sqlit person1 = new sqlit(date,"true");

        persons.add(person1);

        dbManager.add(persons);
    }

    public void query()
    {
        List<sqlit> persons = dbManager.query();
        for (sqlit person : persons)
        {
            list.add(person.getDate());
            if(date1.equals(person.getDate())){
                isinput=true;
            }
        }
        calendar.addMarks(list, 0);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dbManager.closeDB();// 释放数据库资源
    }
}
