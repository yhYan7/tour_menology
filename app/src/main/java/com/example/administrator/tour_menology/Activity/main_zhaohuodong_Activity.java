package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.Adapter.main_zhaohuodong_Adapter;
import com.example.administrator.tour_menology.Bean.bean;
import com.example.administrator.tour_menology.Bean.fabu_huodong_list;
import com.example.administrator.tour_menology.Bean.zhaohuodong_list;
import com.example.administrator.tour_menology.Calendar.AllCalendar;
import com.example.administrator.tour_menology.MainActivity;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.MyAsyncTask;
import com.example.administrator.tour_menology.date.MyDownUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xiaosu.pulllayout.PullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;


public class main_zhaohuodong_Activity extends AppCompatActivity{

    private ImageButton toolbarback;
    private TextView toolbartext;
    private ImageButton calendar_img;
    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_zhaohuodong);
        toolbarback = (ImageButton) findViewById(R.id.toolbar_back);
        toolbarback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbartext = (TextView) findViewById(R.id.toolbar_title);
        toolbartext.setText("找活动");

        calendar_img = (ImageButton) findViewById(R.id.calendar_img);
        calendar_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_zhaohuodong_Activity.this, AllCalendar.class);
                startActivity(intent);
            }
        });

        mWebView = (WebView) findViewById(R.id.webview_zhd);
        mWebView.loadUrl("http://bobtrip.com/tripcal/search/act.action");
//启用支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

    }


}
