package com.example.administrator.tour_menology.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class duihuan extends AppCompatActivity {
    private TextView toolbartext;
    private ImageButton toolbar;
    private Button jia,jian,tijiao;
    private TextView shuliang;
    private int shu=1;
    private ImageView img;
    private TextView title,shengyu,jifen_dan,jifen_zong,jifen_zong2,name,phone,shenfenz;
    private String mJifen;

    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duihuan);

        initview();
        //获取登录用户信息
        utils = new PreferenceUtils(duihuan.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();
        initdata();


    }

    private void initdata() {


        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (TextUtils.isEmpty(name.getText())&&TextUtils.isEmpty(shenfenz.getText())&&TextUtils.isEmpty(phone.getText())){
                Toast.makeText(duihuan.this,"请完善好信息再兑换",Toast.LENGTH_SHORT).show();
            }else {


                new AlertDialog.Builder(duihuan.this)
                        .setTitle("确认")
                        .setMessage("您是否完善了信息")
                        .setPositiveButton("否", null)
                        .setNegativeButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Map<String, String> params = new HashMap<>();
                                params.put("memberId",memberId);
                                params.put("productId",getIntent().getExtras().getString("productId"));
                                params.put("phone",phone.getText().toString());
                                params.put("exchangeCount",shuliang.getText().toString());
                                params.put("realName",name.getText().toString());
                                params.put("IDNumber",phone.getText().toString());
                                String url ="http://bobtrip.com/tripcal/api/saveOrder.action";
                            OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e) {
                                    Toast.makeText(duihuan.this,"兑换失败",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onResponse(String response) {
                                Toast.makeText(duihuan.this,"兑换成功",Toast.LENGTH_LONG).show();
                                }
                            });


                            }
                        })
                        .show();

            }


            }
        });

    }

    private void initview() {
        toolbartext = (TextView) findViewById(R.id.toolbar_title);
        toolbartext.setText("兑换详情");
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        jia = (Button) findViewById(R.id.duihuan_btn_jia);
        jian = (Button) findViewById(R.id.duihuan_btn_jian);
        shuliang = (TextView) findViewById(R.id.duihuan_shuliang);


        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               shuliang.setText(shu+++"");
                mJifen = getIntent().getExtras().getString("jifen");
                jifen_zong.setText(Integer.parseInt(shuliang.getText().toString())*Integer.parseInt(mJifen)+"");
                jifen_zong2.setText(Integer.parseInt(shuliang.getText().toString())*Integer.parseInt(mJifen)+"");
            }
        });


        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              shuliang.setText(shu--+"");
                jifen_zong.setText(Integer.parseInt(shuliang.getText().toString())*Integer.parseInt(mJifen)+"");
                jifen_zong2.setText(Integer.parseInt(shuliang.getText().toString())*Integer.parseInt(mJifen)+"");
            }
        });

        img = (ImageView) findViewById(R.id.duihuan_img);
        title = (TextView) findViewById(R.id.duihuan_title);
        jifen_dan = (TextView) findViewById(R.id.duihuan_jifen_dan);
        jifen_zong = (TextView) findViewById(R.id.duihuan_jifen_he);
        jifen_zong2 = (TextView) findViewById(R.id.duihuan_jifen_he2);
        name = (TextView) findViewById(R.id.duihuan_name);
        phone = (TextView) findViewById(R.id.duihuan_phone);
        shenfenz = (TextView) findViewById(R.id.duihuan_shenfenz);
        shengyu = (TextView) findViewById(R.id.duihuan_shengyu);
        tijiao = (Button) findViewById(R.id.duihuan_btn_tijiao);


        jifen_dan.setText(getIntent().getExtras().getString("jifen"));
        jifen_zong.setText(getIntent().getExtras().getString("jifen"));
        jifen_zong2.setText(getIntent().getExtras().getString("jifen"));
        title.setText(getIntent().getExtras().getString("title"));
        shengyu.setText(getIntent().getExtras().getString("zs"));
        Glide.with(duihuan.this).load(getIntent().getExtras().getString("img")).diskCacheStrategy(DiskCacheStrategy.ALL).into(img);


    }
}
