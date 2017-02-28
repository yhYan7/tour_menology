package com.example.administrator.tour_menology.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.tour_menology.R;

public class Viewxiangqing extends AppCompatActivity {
    private WebView mWebView;
    private ImageButton toolback;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewxiangqing);
        mWebView = (WebView) findViewById(R.id.viewweb);
        mTextView = (TextView) findViewById(R.id.toolbar_title);
        mTextView.setText("活动详情");
        toolback = (ImageButton) findViewById(R.id.toolbar_back);
        toolback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String url =getIntent().getExtras().getString("id");
        mWebView.loadUrl(url);
        WebSettings settings = mWebView.getSettings();
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
//        WebSettings webSettings =mWebView.getSettings();


        //          WebSettings webSettings = view.getSettings();
//        webSettings.setJavaScriptEnabled(true);
    }
}
