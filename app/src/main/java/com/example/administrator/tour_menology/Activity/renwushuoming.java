package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.tour_menology.R;

public class renwushuoming extends AppCompatActivity {
    private TextView toolbar;
    private ImageButton mImageButton;
    private WebView mWebView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renwushuoming);
        toolbar = (TextView) findViewById(R.id.toolbar_title);
        toolbar.setText("任务说明");

        mImageButton = (ImageButton) findViewById(R.id.toolbar_back);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWebView = (WebView) findViewById(R.id.renwu_web);
        mWebView.loadUrl("http://bobtrip.com/tripcal/webapp/personCenter/taskspec.jsp");

        mButton = (Button) findViewById(R.id.jifen_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(renwushuoming.this,jinfenjl.class);
                startActivity(intent);
            }
        });

    }
}
