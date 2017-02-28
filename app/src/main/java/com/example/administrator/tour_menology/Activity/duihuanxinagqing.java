package com.example.administrator.tour_menology.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import okhttp3.Call;

public class duihuanxinagqing extends AppCompatActivity {

    private ImageButton toolbar;
    private TextView toolbartext,title,jifen,shuliang,duihuanma,date,date2,zhuangtai;
    private ImageView img;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duihuanxinagqing);

        initview();        //获取登录用户信息
        utils = new PreferenceUtils(duihuanxinagqing.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();


        initdate();

    }

    private void initdate() {

        String id =getIntent().getExtras().getString("Id");
        String url ="http://bobtrip.com/tripcal/api/viewRecord.action?recordId="+id+"&memberId="+memberId;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONObject jsonObject1 =jsonObject.getJSONObject("singleExchange");
                    date2.setText(jsonObject1.getString("effectiveDate"));
                    duihuanma.setText(jsonObject1.getString("exchangeCode"));
                    shuliang.setText(jsonObject1.getString("exchangeCount"));
                    JSONObject jsonObject2 =jsonObject1.getJSONObject("exchangeDate");
                    long date1 =jsonObject2.getLong("time");

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date2 = new Date(date1);
                    String timeStr = simpleDateFormat.format(date2);

                    date.setText(timeStr);


                    jifen.setText(jsonObject1.getString("exchangeIntegral"));
                    title.setText(jsonObject1.getString("name"));
                    zhuangtai.setText(jsonObject1.getString("state"));


                    Glide.with(duihuanxinagqing.this).load("http://bobtrip.com/tripcal" + jsonObject1.getString("img")).diskCacheStrategy(DiskCacheStrategy.ALL).into(img);


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
        toolbartext = (TextView) findViewById(R.id.toolbar_title);
        toolbartext.setText("记录详情");
        title = (TextView) findViewById(R.id.dhxq_title);
        jifen = (TextView) findViewById(R.id.dhxq_jifen);
        shuliang = (TextView) findViewById(R.id.dhxq_duihuansl);
        duihuanma = (TextView) findViewById(R.id.dhxq_duihuanma);
        date = (TextView) findViewById(R.id.dhxq_date);
        date2 = (TextView) findViewById(R.id.dhxq_date2);
        zhuangtai = (TextView) findViewById(R.id.dhxq_zhuangtai);
        img = (ImageView) findViewById(R.id.dhxq_img);

    }

}
