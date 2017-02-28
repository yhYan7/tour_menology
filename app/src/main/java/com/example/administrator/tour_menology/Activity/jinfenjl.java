package com.example.administrator.tour_menology.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.tour_menology.Adapter.jifenjl_adapter;
import com.example.administrator.tour_menology.Bean.jinfenjl_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class jinfenjl extends AppCompatActivity {
    private TextView mTextView;
    private ImageButton mImageButton;
    private ListView mListView;
    private TextView shengyu,huoqu,shiyong;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private List<jinfenjl_list>mList=new ArrayList<>();
    private Button mButton,mButton2,mButton3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jinfenjl);

        mTextView = (TextView) findViewById(R.id.toolbar_title);

        mTextView.setText("积分记录");
        mImageButton = (ImageButton) findViewById(R.id.toolbar_back);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mListView = (ListView) findViewById(R.id.jifenjl_list);

        initview();

        initdata();

        initdata2("");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton.setBackgroundColor(Color.parseColor("#6034bcec"));
                mButton2.setBackgroundColor(Color.parseColor("#00000000"));
                mButton3.setBackgroundColor(Color.parseColor("#00000000"));
                mList.clear();
                initdata2("");
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton2.setBackgroundColor(Color.parseColor("#6034bcec"));
                mButton.setBackgroundColor(Color.parseColor("#00000000"));
                mButton3.setBackgroundColor(Color.parseColor("#00000000"));
                mList.clear();
                initdata2("获取");
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton3.setBackgroundColor(Color.parseColor("#6034bcec"));
                mButton2.setBackgroundColor(Color.parseColor("#00000000"));
                mButton.setBackgroundColor(Color.parseColor("#00000000"));
                mList.clear();
                initdata2("兑换");
            }
        });

    }

    private void initdata2(String s) {
        String url ="http://bobtrip.com/tripcal/api/listPointRecord.action?memberId="+memberId+"&searchType="+s;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String data =jsonObject1.getString("createDateStr");
                        String jifen =jsonObject1.getString("point");
                        String title =jsonObject1.getString("moduleName");
                        mList.add(new jinfenjl_list(jifen,title,data));

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jifenjl_adapter madapter =new jifenjl_adapter(mList,jinfenjl.this);
                mListView.setAdapter(madapter);

            }
        });





    }

    private void initdata() {

        String url ="http://bobtrip.com/tripcal/api/getPoint.action?memberId="+memberId;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    shengyu.setText(jsonObject.getString("balence"));
                    huoqu.setText(jsonObject.getString("getPoint"));
                    shiyong.setText(jsonObject.getString("usePoint"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initview() {
        shengyu = (TextView) findViewById(R.id.jifenjl_shengyu);
        huoqu = (TextView) findViewById(R.id.jifenjl_huoqu);
        shiyong = (TextView) findViewById(R.id.jifenjl_shiyong);
        //获取登录用户信息
        utils = new PreferenceUtils(jinfenjl.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();

    mButton = (Button) findViewById(R.id.jifen_1);


    mButton2 = (Button) findViewById(R.id.jifen_2);

    mButton3= (Button) findViewById(R.id.jifen_3);


    }
}
