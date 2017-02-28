package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

public class bianjirenwu extends AppCompatActivity {

    private EditText startDateTime;
    private EditText endDateTime;
    private TextView toolbartext, add_title, add_address, add_beizhu;
    private ImageButton toolbar;
    private Button mButton;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private Spinner mSpinner;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
    private String initStartDateTime = formatter.format(curDate);
    private String initEndDateTime = formatter.format(curDate);
    private String mMId;
    private String mSpinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bianjirenwu);

        initView();

        initdata();

        //获取spinner 选中的值
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinner1 = (String) mSpinner.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initdata2();
            }
        });
    }

    private void initdata2() {

        String url ="http://bobtrip.com/tripcal/api/task/update.action";

        Map<String, String> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("taskId", mMId);
        params.put("title", add_title.getText().toString());
        params.put("address", add_address.getText().toString());
        params.put("priority", mSpinner1);
        params.put("remindTime", startDateTime.getText().toString());
        params.put("endTime", endDateTime.getText().toString());
        params.put("remark", add_beizhu.getText().toString());

        OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Toast.makeText(bianjirenwu.this,"修改成功",1).show();
                finish();
            }
        });

    }

    private void initdata() {

        mMId = getIntent().getExtras().getString("id");
        String url ="http://bobtrip.com/tripcal/api/task/detail.action?taskId="+ mMId;
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONObject jsonObject1 =jsonObject.getJSONObject("detail");
                    add_title.setText(jsonObject1.getString("title"));
                    add_address.setText(jsonObject1.getString("address"));
                    startDateTime.setText(jsonObject1.getString("endTime"));
                    endDateTime.setText(jsonObject1.getString("remindTime"));
                    add_beizhu.setText(jsonObject1.getString("remark"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        //获取登录用户信息
        utils = new PreferenceUtils(bianjirenwu.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        if (map == null || map.size() < 1) {
            Toast.makeText(bianjirenwu.this, "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(bianjirenwu.this,
                    denglu.class);
            startActivity(intent);

        }else
            memberId = map.get("memberId").toString();

        // 两个输入框        startDateTime = (EditText) findViewById(R.id.inputDate);

        startDateTime = (EditText) findViewById(R.id.inputDate);
        endDateTime = (EditText) findViewById(R.id.inputDate2);


        toolbartext = (TextView) findViewById(R.id.toolbar_title);
        toolbartext.setText("添加任务");

        mButton = (Button) findViewById(R.id.add_button);
        add_title = (TextView) findViewById(R.id.add_title);
        add_address = (TextView) findViewById(R.id.add_address);
        add_beizhu = (TextView) findViewById(R.id.add_beizhu);

        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSpinner = (Spinner) findViewById(R.id.add_spinner);



        // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.add_spinner);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        //绑定 Adapter到控件
        mSpinner.setAdapter(_Adapter);

        startDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        bianjirenwu.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);

            }
        });

        endDateTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        bianjirenwu.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(endDateTime);
            }
        });

    }
}
