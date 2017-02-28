package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.Adapter.main_fabufang_adapter;
import com.example.administrator.tour_menology.Bean.main_fabu_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.xiaosu.pulllayout.PullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class main_fabufang extends AppCompatActivity implements View.OnClickListener, BasePullLayout.OnPullCallBackListener {
    private ImageButton toolbar;
    private TextView toolbartext;
    private PopupWindow mPopupWindow;
    private TextView main_fabu_menu;
    private PullLayout mPullLayout;
    private ListView mListView3;
    private main_fabu_list mbean;
    private int pa = 1;
    private main_fabufang_adapter mAdapter;
    private List<main_fabu_list.LIstdate> mlistdateTemp = new ArrayList<>();
    private List<main_fabu_list.LIstdate> mlistdate = new ArrayList<>();
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private RelativeLayout r1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fabufang);
        initView();

        initDate(pa, "", "", "", false);

        mListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(main_fabufang.this, main_fabu_xiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mlistdate.get(position).getUnitId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });

    }


    private void initDate(int pager, String unitType, String keyword, String memberId, final boolean isRefresh) {
        String URL = "http://bobtrip.com/tripcal/api/unit/search.action?currentPage=" + pager + "&unitType=" + unitType +
                "&keyword=" + keyword + "&memberId=" + memberId;

        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(main_fabufang.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mbean = gson.fromJson(response, main_fabu_list.class);
                mlistdateTemp = mbean.getList();
                int length = mlistdateTemp.size();
                if (length > 0) {
                    if (isRefresh) {
                        mlistdate.clear();
                        Iterator<main_fabu_list.LIstdate> iter = mlistdateTemp.iterator();
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
                    Toast.makeText(main_fabufang.this, "暂无数据", Toast.LENGTH_SHORT).show();

                mPullLayout.finishPull();
            }

        });
    }

    private void initView() {
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbartext = (TextView) findViewById(R.id.toolbar_title);
        toolbartext.setText("找社区");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        main_fabu_menu = (TextView) findViewById(R.id.main_fabu_menu);
        main_fabu_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shouPopupwindow();
            }
        });
        mPullLayout = (PullLayout) findViewById(R.id.main_fabu_palllayout);
        mPullLayout.setOnPullListener(this);
        mListView3 = (ListView) findViewById(R.id.main_fabu_listview);
        mAdapter = new main_fabufang_adapter(mlistdate, this);
        mListView3.setAdapter(mAdapter);

        r1= (RelativeLayout) findViewById(R.id.ry1);



        //获取登录用户信息
        utils = new PreferenceUtils(main_fabufang.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        if (map == null || map.size() < 1) {
            Toast.makeText(main_fabufang.this, "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(main_fabufang.this,
                    denglu.class);
            startActivity(intent);

        }else
            memberId = map.get("memberId").toString();


    }

    private void shouPopupwindow() {
        View contentView = LayoutInflater.from(main_fabufang.this).inflate(R.layout.main_popopwindow_fabu, null);
        mPopupWindow = new PopupWindow(contentView);
        mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tv1 = (TextView) contentView.findViewById(R.id.main_fabu_m1);
        TextView tv2 = (TextView) contentView.findViewById(R.id.main_fabu_m2);
        TextView tv3 = (TextView) contentView.findViewById(R.id.main_fabu_m3);
        TextView tv4 = (TextView) contentView.findViewById(R.id.main_fabu_m4);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);

        mPopupWindow.showAsDropDown(main_fabu_menu);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_fabu_m1:
                mlistdate.clear();
                initDate(1, "旅游局", "", "", false);
                if (mPopupWindow != null && mPopupWindow.isShowing())
                    mPopupWindow.dismiss();
                break;

            case R.id.main_fabu_m2:

                mlistdate.clear();
                initDate(1, "旅游企业", "", "", false);
                if (mPopupWindow != null && mPopupWindow.isShowing())
                    mPopupWindow.dismiss();
                break;
            case R.id.main_fabu_m3:
                mlistdate.clear();
                initDate(1, "团队", "", "", false);
                if (mPopupWindow != null && mPopupWindow.isShowing())
                    mPopupWindow.dismiss();
                break;
            case R.id.main_fabu_m4:
                mlistdate.clear();
                initDate(1, "", "", "", false);
                if (mPopupWindow != null && mPopupWindow.isShowing())
                    mPopupWindow.dismiss();
                break;
        }
    }

    @Override
    public void onRefresh() {
        initDate(++pa, "", "", "", false);
    }

    @Override
    public void onLoad() {
        initDate(++pa, "", "", "", false);
    }
}
