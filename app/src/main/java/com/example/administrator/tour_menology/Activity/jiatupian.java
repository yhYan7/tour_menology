package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.DateTimePickDialogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class jiatupian extends AppCompatActivity {

    private ImageButton Toolbar;
    private ImageView img, duihao;
    private TextView mTextView;
    private EditText startDateTime;
    private String initEndDateTime = "2016年8月6日 17:44"; // 初始化结束时间
    private EditText title, address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiatupian);

        Toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        Toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTextView = (TextView) findViewById(R.id.toolbar_title);
        mTextView.setText("旅行美图");
        startDateTime = (EditText) findViewById(R.id.inputDate2);

        //  事件选择器
        startDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        jiatupian.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);

            }
        });

        duihao = (ImageView) findViewById(R.id.duihao2);

        title = (EditText) findViewById(R.id.tupian_edxt);

        address = (EditText) findViewById(R.id.jtp_address);

        img = (ImageView) findViewById(R.id.chuantp_img);

        Glide.with(jiatupian.this).load(getIntent().getExtras().getString("url")).into(img);



        duihao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(startDateTime.getText())){
                    Toast.makeText(jiatupian.this,"请选择日期",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(title.getText())){
                    Toast.makeText(jiatupian.this,"请填好游记内容",Toast.LENGTH_SHORT).show();
                } else {


                    String url ="http://bobtrip.com/tripcal/api/travel/createCont.action";
                    String travelId =getIntent().getExtras().getString("travelId");

                    Map<String, String> params = new HashMap<>();
                    params.put("travelId",travelId);
                    params.put("address",address.getText().toString());
                    params.put("startDate",startDateTime.getText().toString());
                    params.put("travelType","1");
                    params.put("content",title.getText().toString());
                    params.put("logo",getIntent().getExtras().getString("path"));

                    OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {

                        }

                        @Override
                        public void onResponse(String response) {

                        }
                    });

                    Intent intent =new Intent(jiatupian.this,bianjiyj.class);
                    intent.putExtra("travelId",travelId);
                    startActivity(intent);
                    finish();


                }

            }
        });



    }
}
