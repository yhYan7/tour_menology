package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;

public class shangchengxq extends AppCompatActivity {
    private ImageButton toolbar;
    private TextView tool;
    private ImageView img;
    private TextView shi, zhong, title, jifen, zhangshu, jieshao, guize;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private Button youqian, meiqian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangchengxq);
        tool = (TextView) findViewById(R.id.toolbar_title);
        tool.setText("产品详情");
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
        //获取登录用户信息
        utils = new PreferenceUtils(shangchengxq.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();

        initdata();
    }

    private void initdata() {

        String id = getIntent().getExtras().getString("id");

        String url = "http://bobtrip.com/tripcal/api/showProInfo.action?memberId=" + memberId + "&remoteProId=" + id;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    jieshao.setText(jsonObject.getString("siteCaption"));

                    if (String.valueOf(jsonObject.getString("startDate")) != "") {
                        shi.setText(jsonObject.getString("startDate"));
                        zhong.setText(jsonObject.getString("endDate"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void initView() {
        img = (ImageView) findViewById(R.id.chanpinxp_img);
        shi = (TextView) findViewById(R.id.chanpinxp_shi);
        zhong = (TextView) findViewById(R.id.chanpinxp_mo);
        title = (TextView) findViewById(R.id.chanpinxp_title);
        jifen = (TextView) findViewById(R.id.chanpinxp_jifen);
        zhangshu = (TextView) findViewById(R.id.chanpinxp_zhangshu);
        jieshao = (TextView) findViewById(R.id.chanpinxp_jieshao);
        guize = (TextView) findViewById(R.id.chanpinxp_guize);
        youqian = (Button) findViewById(R.id.button_youqian);
        meiqian = (Button) findViewById(R.id.button_meiqian);


        jifen.setText(getIntent().getExtras().getString("jifen"));
        zhangshu.setText(getIntent().getExtras().getString("zs"));
        guize.setText(getIntent().getExtras().getString("guize"));
        title.setText(getIntent().getExtras().getString("title"));
        Glide.with(shangchengxq.this).load("http://bobtrip.com/tripcal" + getIntent().getExtras().getString("img")).diskCacheStrategy(DiskCacheStrategy.ALL).into(img);

        int wode = Integer.parseInt(getIntent().getExtras().getString("wodejifen"));
        int jifen = Integer.parseInt(getIntent().getExtras().getString("jifen"));

        int zhi = wode - jifen;

        if (zhi > 0) {
            youqian.setVisibility(View.VISIBLE);
            meiqian.setVisibility(View.GONE);
        } else {
            meiqian.setVisibility(View.VISIBLE);
            youqian.setVisibility(View.GONE);
        }

        youqian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

       //         intent.getExtras().putString("productId", getIntent().getExtras().getString("productId"));

                Intent mIntent = new Intent(shangchengxq.this, duihuan.class);
                Bundle bundle = new Bundle();
                bundle.putString("img","http://bobtrip.com/tripcal" + getIntent().getExtras().getString("img"));
                bundle.putString("title",getIntent().getExtras().getString("title"));
                bundle.putString("productId",getIntent().getExtras().getString("productId"));
                bundle.putString("zs",getIntent().getExtras().getString("zs"));
                bundle.putString("jifen",getIntent().getExtras().getString("jifen"));
                mIntent.putExtras(bundle);
                startActivity(mIntent);


            }
        });


    }
}
