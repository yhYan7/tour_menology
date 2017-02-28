package com.example.administrator.tour_menology.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.tour_menology.R;

public class map extends AppCompatActivity {

    private ImageButton toolbar;
    private TextView mTextView;
    private WebView mWebView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTextView = (TextView) findViewById(R.id.toolbar_title);
        mTextView.setText("周边详情");

        mWebView= (WebView) findViewById(R.id.map_web);

    //    mWebView.loadUrl("http://bobtrip.com/tripcal/webapp/common/nearby.jsp");


//        WebView mobView = new WebView(this);
        //WebView加载web资源
        mWebView.loadUrl("http://bobtrip.com/tripcal/webapp/common/nearby.jsp");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return false;
            }
        });

    }
}
