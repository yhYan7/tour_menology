package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.Bean.add_youji_bean;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.lidroid.xutils.http.RequestParams;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class add_yj extends AppCompatActivity {
    private TextView toolbart;
    private ImageButton toolbar;
    private EditText title, date;
    private AutoCompleteTextView address;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> mList;
    private Button mButton;
    private List<String> mList1;
    private List<add_youji_bean> mList2;
    private List<add_youji_bean> mList21;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private String mId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_yj);
        //获取登录用户信息
        utils = new PreferenceUtils(add_yj.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");
        memberId = map.get("memberId").toString();
        initview();
        initdata();

    }

    private void initdata() {
        mList2 = new ArrayList<>();
        String url = "http://bobtrip.com/tripcal/api/area/list.action?areaId=";

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                mList = new ArrayList<>();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("areaId");

                        String url = "http://bobtrip.com/tripcal/api/area/list.action?areaId=" + id;

                        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);


                                    JSONArray jsonArray = jsonObject.getJSONArray("list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String name = jsonObject1.getString("areaName");
                                        String id = jsonObject1.getString("areaId");
                                        // mList1 = mList;
                                        mList21 = mList2;
                                        mList21.add(new add_youji_bean(name, id));


                                        mList1 = mList;
                                        mList1.add(name);

                                    }

                                    arrayAdapter = new ArrayAdapter<>(add_yj.this, android.R.layout.simple_list_item_1, mList1);

                                    address.setAdapter(arrayAdapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initview() {
        toolbart = (TextView) findViewById(R.id.toolbar_title);
        toolbart.setText("创建游记");
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title = (EditText) findViewById(R.id.add_yj_title);
        date = (EditText) findViewById(R.id.add_yj_date);
        address = (AutoCompleteTextView) findViewById(R.id.add_yj_address);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        date.setText(str);


        mButton = (Button) findViewById(R.id.add_yj_btn);

        mButton.setOnClickListener(new View.OnClickListener() {

            private String mId;

            @Override
            public void onClick(View v) {
                String str = address.getText().toString();
                if (TextUtils.isEmpty(title.getText()))
                    Toast.makeText(add_yj.this, "请输入游记标题", Toast.LENGTH_SHORT).show();
                else if (mList1.indexOf(str) == -1) {
                    Toast.makeText(add_yj.this, "请输入正确的地址哦", Toast.LENGTH_SHORT).show();
                } else {
                    String url = "http://bobtrip.com/tripcal/api/area/list.action?areaId=";

                    OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            mList = new ArrayList<>();
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id = jsonObject1.getString("areaId");

                                    String url = "http://bobtrip.com/tripcal/api/area/list.action?areaId=" + id;

                                    OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e) {

                                        }

                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);


                                                JSONArray jsonArray = jsonObject.getJSONArray("list");

                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                                    String name = jsonObject2.getString("areaName");
                                                    if (name.equals(address.getText().toString())) {

                                                        mId = jsonObject2.getString("areaId");


                                                        String okurl = "http://bobtrip.com/tripcal/api/travel/create.action";

                                                        Map<String, String> params = new HashMap<>();
/**
 * memberId	是	会员id
 startDate	是	开始时间
 title	是	标题
 areaId	是	地区id
 */
                                                        params.put("memberId", memberId);
                                                        params.put("startDate", date.getText().toString());
                                                        params.put("title", title.getText().toString());
                                                        params.put("areaId", mId);
                                                        OkHttpUtils.post().url(okurl).params(params).build().execute(new StringCallback() {
                                                            @Override
                                                            public void onError(Call call, Exception e) {
                                                                Toast.makeText(add_yj.this, "创建失败", Toast.LENGTH_SHORT).show();

                                                            }

                                                            @Override
                                                            public void onResponse(String response) {
                                                                try {
                                                                    JSONObject object = new JSONObject(response);
                                                                    String travelId = object.getString("travelId");
                                                                    Intent mIntent = new Intent(add_yj.this, bianjiyj.class);
                                                                    Bundle bundle = new Bundle();

                                                                    bundle.putString("travelId", travelId);

                                                                    mIntent.putExtras(bundle);
                                                                    startActivity(mIntent);
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }

                                                            }
                                                        });


                                                    }

                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });


                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                }
            }


        });


    }

}
