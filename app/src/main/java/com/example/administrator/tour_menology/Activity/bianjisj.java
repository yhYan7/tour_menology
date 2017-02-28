package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.DateTimePickDialogUtil;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class bianjisj extends AppCompatActivity {
    private TextView toolbartext;
    private EditText title, address, beizhu;
    private Switch mSwitch;
    private Spinner tixing, chongfu;
    private EditText startDateTime;
    private EditText endDateTime;
    private ImageButton toolbar;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private Button mButton;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
    private String initStartDateTime = formatter.format(curDate);
    private String initEndDateTime = formatter.format(curDate);
    private String mChongfu1;
    private String mTixing;
    private String mStartdate;
    private String mEnddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bianjisj);
        toolbartext = (TextView) findViewById(R.id.toolbar_title);
        toolbartext.setText("编辑事件");
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initview();


        initdata();
        //获取spinner 选中的值
        chongfu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mChongfu1 = (String) chongfu.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //获取spinner 选中的值
        tixing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTixing = (String) tixing.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initdata2();
                finish();
            }
        });


    }

    private void initdata2() {
        String url ="http://bobtrip.com/tripcal/api/event/update.action";
        Map<String,String> map=new HashMap<>();
        map.put("memberId",memberId);
        map.put("eventId",getIntent().getExtras().getString("id"));
        map.put("title",title.getText().toString());
        map.put("address",address.getText().toString());
        if (mSwitch.isChecked())
            map.put("fullDay", "1");
        else
            map.put("fullDay", "0");
        map.put("startTime",startDateTime.getText().toString());
        map.put("endTime",startDateTime.getText().toString());
        map.put("remind",mTixing);
        map.put("repeats",mChongfu1);
        map.put("remark",beizhu.getText().toString());

        OkHttpUtils.post().url(url).params(map).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
        Toast.makeText(bianjisj.this,"修改成功",1).show();
            }
        });

    }

    private void initdata() {
        String id = getIntent().getExtras().getString("id");
        String url = "http://bobtrip.com/tripcal/api/event/detail.action?eventId=" + id;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("detail");
                    title.setText(jsonObject1.getString("title"));
                    address.setText(jsonObject1.getString("address"));
                    mStartdate = (jsonObject1.getString("startTime"));
                    mEnddate = (jsonObject1.getString("endTime"));
                    beizhu.setText(jsonObject1.getString("remind"));
                    startDateTime.setText(mStartdate);
                    endDateTime.setText(mEnddate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initview() {
        title = (EditText) findViewById(R.id.bianjisj_title);
        address = (EditText) findViewById(R.id.bianjisj_address);
        beizhu = (EditText) findViewById(R.id.bianjisj_beizhu);
        startDateTime = (EditText) findViewById(R.id.inputDate);
        endDateTime = (EditText) findViewById(R.id.inputDate2);
        mSwitch = (Switch) findViewById(R.id.bianji_switch);
        tixing = (Spinner) findViewById(R.id.bianji_spinner);
        chongfu = (Spinner) findViewById(R.id.bianji_spinner2);
        //获取登录用户信息
        utils = new PreferenceUtils(bianjisj.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        if (map == null || map.size() < 1) {
            Toast.makeText(bianjisj.this, "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(bianjisj.this,
                    denglu.class);
            startActivity(intent);

        } else
            memberId = map.get("memberId").toString();
        startDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        bianjisj.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);

            }
        });

        endDateTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        bianjisj.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(endDateTime);
            }
        });




        // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.tixing);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        //绑定 Adapter到控件
        tixing.setAdapter(_Adapter);
        // 建立数据源
        String[] mItems2 = getResources().getStringArray(R.array.chongfu);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> _Adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems2);
        //绑定 Adapter到控件

        chongfu.setAdapter(_Adapter2);
        mButton = (Button) findViewById(R.id.bianjisj_button);


    }
}
