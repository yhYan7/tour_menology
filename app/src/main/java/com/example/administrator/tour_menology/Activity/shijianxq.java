package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;

public class shijianxq extends AppCompatActivity {
    private ImageButton toolbar;
    private TextView toolbart;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private ImageButton shanchu,bianji;
    private TextView title,address,startdate,enddate,tixing,chongfu,beizhu;
    private String mId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shijianxq);


        initview();

        initdata();

        shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initdata2();
            }
        });

        bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(shijianxq.this,bianjisj.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", getIntent().getExtras().getString("id"));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });


    }

    private void initdata2() {
        String url ="http://bobtrip.com/tripcal/api/event/del.action?eventId="+mId;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Toast.makeText(shijianxq.this,"删除成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void initdata() {
        mId = getIntent().getExtras().getString("id");
        String url ="http://bobtrip.com/tripcal/api/event/detail.action?eventId="+ mId;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONObject jsonObject1 =jsonObject.getJSONObject("detail");
                    title.setText(jsonObject1.getString("title"));
                    address.setText(jsonObject1.getString("address"));
                    startdate.setText(jsonObject1.getString("startTime"));
                    enddate.setText(jsonObject1.getString("endTime"));
                    tixing.setText(jsonObject1.getString("remind"));
                    chongfu.setText(jsonObject1.getString("repeats"));
                    beizhu.setText(jsonObject1.getString("remark"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initview() {
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbart = (TextView) findViewById(R.id.toolbar_title);
        toolbart.setText("事件详情");
        //获取登录用户信息
        utils = new PreferenceUtils(shijianxq.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();

        title = (TextView) findViewById(R.id.shijian_title);
        address = (TextView) findViewById(R.id.shijian_address);
        startdate = (TextView) findViewById(R.id.shijian_start);
        enddate = (TextView) findViewById(R.id.shijian_end);
        tixing = (TextView) findViewById(R.id.shijian_tixing);
        chongfu = (TextView) findViewById(R.id.shijian_chongfu);
        beizhu = (TextView) findViewById(R.id.shijian_beizhu);

        shanchu = (ImageButton) findViewById(R.id.shanchu_sj);
        bianji = (ImageButton) findViewById(R.id.bianji_sj);

    }
}
