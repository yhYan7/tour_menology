package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.DateTimePickDialogUtil;
import com.example.administrator.tour_menology.date.PositiveDialong;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class xiesuiji extends AppCompatActivity {
    private TextView mTextView;
    private ImageButton mImageButton, duihao;
    private EditText startDateTime;
    private String initEndDateTime = "2016年8月6日 17:44"; // 初始化结束时间
    private EditText title, address;
    private PositiveDialong dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiesuiji);
        mTextView = (TextView) findViewById(R.id.toolbar_title);
        mTextView.setText("旅行随机");
        mImageButton = (ImageButton) findViewById(R.id.toolbar_back);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        startDateTime = (EditText) findViewById(R.id.inputDate2);

        //  时间选择器
        startDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        xiesuiji.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);

            }
        });

        duihao = (ImageButton) findViewById(R.id.duihao);
        title = (EditText) findViewById(R.id.xieyj_title);
        address = (EditText) findViewById(R.id.xieyj_address);


        duihao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(startDateTime.getText())) {
                    Toast.makeText(xiesuiji.this, "请选择日期", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(title.getText())) {
                    Toast.makeText(xiesuiji.this, "请填好游记内容", Toast.LENGTH_SHORT).show();
                } else {

                    String url = "http://bobtrip.com/tripcal/api/travel/createCont.action";
                    String travelId = getIntent().getExtras().getString("travelId");

                    Map<String, String> params = new HashMap<>();
                    params.put("travelId", travelId);
                    params.put("address", address.getText().toString());
                    params.put("startDate", startDateTime.getText().toString());
                    params.put("travelType", "0");
                    params.put("content", title.getText().toString());

                    OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {

                        }

                        @Override
                        public void onResponse(String response) {

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);

                                if (jsonObject.has("recordId")) {
                                    final String recordId = jsonObject.getString("recordId");
                                    dialog = new PositiveDialong(xiesuiji.this, "",
                                            new PositiveDialong.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    switch (v.getId()) {
                                                        case R.id.dialog_btn_lq:
                                                            String url = "http://bobtrip.com/tripcal/api/getRewords.action?recordId=" + recordId;
                                                            OkHttpUtils.get().url(url).build().execute(new StringCallback() {


                                                                                                           @Override
                                                                                                           public void onError(Call call, Exception e) {

                                                                                                           }

                                                                                                           @Override
                                                                                                           public void onResponse(String response) {
                                                                                                               try {
                                                                                                                   JSONObject jsonObject1 = new JSONObject(response);
                                                                                                                   String fen = jsonObject1.getString("point");
                                                                                                                   Toast.makeText(xiesuiji.this, "成功领取" + fen + "分",
                                                                                                                           Toast.LENGTH_SHORT).show();
                                                                                                               } catch (JSONException e) {
                                                                                                                   e.printStackTrace();
                                                                                                               }

                                                                                                           }
                                                                                                       }
                                                            );


                                                            break;
                                                        case R.id.dialog_btn:
                                                            break;
                                                    }

                                                }
                                            });
                                    dialog.show();

                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });

                    Intent intent = new Intent(xiesuiji.this, bianjiyj.class);
                    intent.putExtra("travelId", travelId);
                    startActivity(intent);


                }
            }
        });


    }
}
