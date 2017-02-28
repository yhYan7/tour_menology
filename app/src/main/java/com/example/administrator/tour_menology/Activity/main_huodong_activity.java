package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.my_fragment_activity.setupActivity;
import com.example.administrator.tour_menology.utils.PreferenceUtils;

import java.util.Map;

public class main_huodong_activity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton toolbarback;
    private TextView toolbartitle;
    private RelativeLayout m1, m2, m3, m4, m5, m6, m7, m8,m9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_huodong);

        initView();
    }

    private void initView() {
        toolbarback = (ImageButton) findViewById(R.id.toolbar_back);
        toolbarback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbartitle = (TextView) findViewById(R.id.toolbar_title);
        toolbartitle.setText("活动分类");

        m1 = (RelativeLayout) findViewById(R.id.main_huodong_1);
        m2 = (RelativeLayout) findViewById(R.id.main_huodong_2);
        m3 = (RelativeLayout) findViewById(R.id.main_huodong_3);
        m4 = (RelativeLayout) findViewById(R.id.main_huodong_4);
        m5 = (RelativeLayout) findViewById(R.id.main_huodong_5);
        m6 = (RelativeLayout) findViewById(R.id.main_huodong_6);
        m7 = (RelativeLayout) findViewById(R.id.main_huodong_7);
        m8 = (RelativeLayout) findViewById(R.id.main_huodong_8);
        m9 = (RelativeLayout) findViewById(R.id.main_huodong_9);
        m1.setOnClickListener(this);
        m2.setOnClickListener(this);
        m3.setOnClickListener(this);
        m4.setOnClickListener(this);
        m5.setOnClickListener(this);
        m6.setOnClickListener(this);
        m7.setOnClickListener(this);
        m8.setOnClickListener(this);
        m9.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_huodong_1:

                Intent intent1 = new Intent(main_huodong_activity.this, main_zhaohuodong_Activity.class);
                startActivity(intent1);
                break;
            case R.id.main_huodong_2:

                Intent intent = new Intent(main_huodong_activity.this, main_zhaohuodong_Activity.class);
                startActivity(intent);
                break;
            case R.id.main_huodong_3:

                Intent intent2 = new Intent(main_huodong_activity.this, main_zhaohuodong_Activity.class);
                startActivity(intent2);
                break;
            case R.id.main_huodong_4:

                Intent intent3 = new Intent(main_huodong_activity.this, main_zhaohuodong_Activity.class);
                startActivity(intent3);
                break;
            case R.id.main_huodong_5:

                Intent intent4 = new Intent(main_huodong_activity.this, main_zhaohuodong_Activity.class);
                startActivity(intent4);
                break;
            case R.id.main_huodong_6:

                Intent intent5 = new Intent(main_huodong_activity.this, main_zhaohuodong_Activity.class);
                startActivity(intent5);
                break;
            case R.id.main_huodong_7:

                Intent intent6 = new Intent(main_huodong_activity.this, main_zhaohuodong_Activity.class);
                startActivity(intent6);
                break;
            case R.id.main_huodong_8:

                Intent intent7 = new Intent(main_huodong_activity.this, main_zhaohuodong_Activity.class);
                startActivity(intent7);
                break;

            case R.id.main_huodong_9:

                Intent intent8 = new Intent(main_huodong_activity.this, main_zhaohuodong_Activity.class);
                startActivity(intent8);
                break;
        }
    }
}
