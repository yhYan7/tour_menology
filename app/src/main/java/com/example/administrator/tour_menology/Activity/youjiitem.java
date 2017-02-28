package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.DateTimePickDialogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class youjiitem extends AppCompatActivity {

    private TextView tool;
    private ImageButton toolbarback, duihao;
    private EditText title, address, date;
    private ImageView img;
    private RelativeLayout mRelativeLayout;
    private String initEndDateTime = "2016年8月6日 17:44"; // 初始化结束时间
    private String mTravelContId;
    private String mTravelId;
    private boolean mEquals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youjiitem);

        initvie();

        initdate();

        initdate2();

    }

    private void initdate2() {
        duihao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String url ="http://bobtrip.com/tripcal/api/travel/updateCont.action";

                Map<String, String> params = new HashMap<>();
                params.put("travelContId",mTravelContId);
                params.put("address",address.getText().toString());
                params.put("startDate",date.getText().toString());
                if (mEquals)
                    params.put("travelType","1");
                else
                    params.put("travelType","0");
                params.put("content",title.getText().toString());

                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(youjiitem.this,"修改成功",Toast.LENGTH_SHORT).show();

                        Intent intent =new Intent(youjiitem.this,bianjiyj.class);


                        intent.putExtra("travelId",mTravelId);
                        startActivity(intent);

                        finish();
                    }
                });

            }
        });


    }

    private void initdate() {
        mTravelId = getIntent().getExtras().getString("travelId");
        mTravelContId = getIntent().getExtras().getString("travelContId");

        String url = "http://bobtrip.com/tripcal/api/travel/detailCont.action?travelId=" + mTravelId + "&travelContId=" + mTravelContId;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("detail");

                    mEquals = jsonObject1.getString("travelType").toString().equals("图片");
                    if (mEquals) {
                        Glide.with(youjiitem.this).load("http://bobtrip.com/tripcal" + jsonObject1.getString("content")).into(img);
                        title.setText(jsonObject1.getString("imgDesc"));
                    } else {
                        title.setText(jsonObject1.getString("content"));
                    }
                    address.setText(jsonObject1.getString("address"));
                    date.setText(jsonObject1.getString("travelDate"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void initvie() {

        tool = (TextView) findViewById(R.id.toolbar_title);
        tool.setText("旅行随记");
        toolbarback = (ImageButton) findViewById(R.id.toolbar_back);
        toolbarback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        duihao = (ImageButton) findViewById(R.id.yjitem_duihao);
        title = (EditText) findViewById(R.id.yjitem_title);
        address = (EditText) findViewById(R.id.yjitem_address);
        date = (EditText) findViewById(R.id.yjitem_date);
        img = (ImageView) findViewById(R.id.yjitem_img);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.yjitem_shan);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  事件选择器


                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        youjiitem.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(date);


            }

        });



        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url ="http://bobtrip.com/tripcal/api/travel/delCont.action";

                OkHttpUtils.post().url(url).addParams("travelContId",mTravelContId).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(youjiitem.this,"删除成功",Toast.LENGTH_SHORT).show();

                        Intent intent =new Intent(youjiitem.this,bianjiyj.class);


                        intent.putExtra("travelId",mTravelId);
                        startActivity(intent);

                        finish();

                    }
                });
            }
        });


    }
}
