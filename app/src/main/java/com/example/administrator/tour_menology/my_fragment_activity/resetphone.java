package com.example.administrator.tour_menology.my_fragment_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.HttpUtil;
import com.example.administrator.tour_menology.utils.MyConfig;
import com.example.administrator.tour_menology.utils.StringUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class resetphone extends AppCompatActivity {

    private ImageButton toolbar_back;
    private EditText phone;
    private Button nextstep;
    private TextView toolbar_title;

    private String phoneNumber,memberId;
    private String httpPostId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetphone);
        init();
    }

    public void init(){

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("重置密码");
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        phone = (EditText) findViewById(R.id.rep_phone);
        nextstep = (Button) findViewById(R.id.nextstep);

        toolbar_back.setOnClickListener(mListener);
        nextstep.setOnClickListener(mListener);

    }

    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.nextstep:
                    if(isvalidate()){
                        getMemberId();
                    }
                    break;
                default:
                    break;

            }

        }
    };


    //点击发送验证码时请求memberId
    public void getMemberId(){
        try{
            phoneNumber = phone.getText().toString().trim();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("phone", phoneNumber);
                        Log.i("", "====phoneNumber-->>" + phoneNumber);
                        httpPostId = HttpUtil.http(MyConfig.httpurl
                                + "/member/search.action", params);
                    } catch (Exception e) {
                        Toast.makeText(resetphone.this, "网络出问题了哦,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    handlerId.sendEmptyMessage(0x10);
                }
            }).start();
        }catch (Exception e){
            Toast.makeText(resetphone.this, "网络出问题了哦,请检查您的网络并重试",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Handler handlerId = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x10:
                    try {
                        JSONObject jsonObject = new JSONObject(httpPostId);
                        if ("0".equals(jsonObject.getString("errcode"))) {
                            // 查找成功
                            JSONObject  jsonObject1 =  (JSONObject) jsonObject.get("detail");
                            Log.i("", "====返回用户id-->>" + jsonObject1.getString("memberId"));
                            memberId = jsonObject1.getString("memberId");
                            //跳转下一页面
                            Intent intent = new Intent(resetphone.this,
                                    resetpwd.class);
                            intent.putExtra("memberId", memberId);
                            intent.putExtra("phoneNumber", phoneNumber);
                            startActivity(intent);
                            finish();

                        } else {
                            Log.i("", "====失败原因-->>" + jsonObject.getString("errmesg"));
                            // 查找失败 改变状态并提示
                            Toast.makeText(resetphone.this,
                                    "该手机号并未注册，请查正！", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(resetphone.this, "网络出问题了哦,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();

                    }
                    break;
            }
        };
    };

    //判断手机号是否正确

    private boolean isvalidate() {
        // TODO Auto-generated method stub
        // 获取控件输入的值
        phoneNumber = phone.getText().toString().trim();
        if (phoneNumber.equals("")) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!StringUtils.isPhoneNumberValid(phoneNumber)) {
            Toast.makeText(this, "手机号有误", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



}
