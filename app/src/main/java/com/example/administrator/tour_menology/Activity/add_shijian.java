package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
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

public class add_shijian extends AppCompatActivity {
    private TextView title, address, beizhu, toolbartext;
    private Switch mSwitch;
    private Spinner tixing, chongfu;
    private EditText startDateTime;
    private EditText endDateTime;
    private Button mButton;
    String tixing1 = null;
    String chongfu1 = null;

    private ImageButton toolbar;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
    private String initStartDateTime = formatter.format(curDate);
    private String ttt = formatter2.format(curDate);

    private String initEndDateTime = formatter.format(curDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shijian);

        initview();

        initdata();
        //获取spinner 选中的值
        chongfu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chongfu1 = (String) chongfu.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //获取spinner 选中的值
        tixing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tixing1 = (String) tixing.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initdata() {

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://bobtrip.com/tripcal/api/event/add.action";
                Map<String, String> params = new HashMap<>();
                params.put("memberId", memberId);
                params.put("title", title.getText().toString());
                params.put("address", address.getText().toString());

                if (mSwitch.isChecked())
                    params.put("fullDay", "1");
                else
                    params.put("fullDay", "0");
                params.put("startTime", startDateTime.getText().toString());
                params.put("endTime", endDateTime.getText().toString());
                params.put("remind", tixing1);
                params.put("repeats", chongfu1);
                params.put("remark", beizhu.getText().toString());

                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(add_shijian.this, "添加失败了亲,请选择好时间啊", 1).show();
                    }

                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(add_shijian.this, "添加成功", Toast.LENGTH_SHORT).show();
                        finish();


                    }
                });

            }
        });

    }

    private void initview() {
        //获取登录用户信息
        utils = new PreferenceUtils(add_shijian.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        if (map == null || map.size() < 1) {
            Toast.makeText(add_shijian.this, "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(add_shijian.this,
                    denglu.class);
            startActivity(intent);

        } else
            memberId = map.get("memberId").toString();

        startDateTime = (EditText) findViewById(R.id.inputDate);
        endDateTime = (EditText) findViewById(R.id.inputDate2);
        startDateTime.setText(ttt);
        endDateTime.setText(ttt);
        toolbartext = (TextView) findViewById(R.id.toolbar_title);
        toolbartext.setText("添加事件");
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        add_shijian.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);

            }
        });

        endDateTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        add_shijian.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(endDateTime);
            }
        });

        tixing = (Spinner) findViewById(R.id.add_spinner);
        chongfu = (Spinner) findViewById(R.id.add_spinner2);

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


        title = (TextView) findViewById(R.id.addsj_title);
        address = (TextView) findViewById(R.id.addsj_address);
        beizhu = (TextView) findViewById(R.id.addsj_beizhu);
        mButton = (Button) findViewById(R.id.addsj_button);
        mSwitch = (Switch) findViewById(R.id.add_switch);


    }
}
