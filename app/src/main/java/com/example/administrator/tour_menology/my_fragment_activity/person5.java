package com.example.administrator.tour_menology.my_fragment_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class person5 extends AppCompatActivity{

    private ImageButton toolbar_back;
    private Button save,send;
    private EditText phoneNum,yanzheng;
    private String phoneNumber;
    private String memberId;
    private String httpPost;

    private boolean type = false;
    private boolean istouch = false;

    private TextView toolbar_title;
    private boolean tag = true;
    private int i = 60;
    Thread thread = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person5);
        init();
    }

    public void init(){

        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        save = (Button) findViewById(R.id.p5_confrim);
        send  = (Button) findViewById(R.id.p5_send);
        phoneNum = (EditText) findViewById(R.id.p5_phone);
        yanzheng = (EditText) findViewById(R.id.p5_yanzheng);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("修改手机号");

        //获取上个页面的传值
        Bundle extras = getIntent().getExtras();
        memberId = extras.getString("memberId");

        toolbar_back.setOnClickListener(mListener);
        save.setOnClickListener(mListener);
        send.setOnClickListener(mListener);

        // 启动短信验证sdk
        SMSSDK.initSDK(this, "1657dcf73a12b", "97bf1baa091f412f33f41ad8b9ef7d8f");
        EventHandler eventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);

    }

    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.toolbar_back:
                    Intent intent = new Intent(person5.this,
                            personalActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.p5_send:
                    if(isvalidate()){
                       // changeBtnGetCode();

                        // 通过sdk发送短信验证
                        type = true;
                        istouch = true;
                        SMSSDK.getVerificationCode("86", phoneNumber);
                        send.setClickable(true);
                        send.setText("重新发送(" + i + ")");
                        send.setClickable(false);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (; i > 0; i--) {
                                    handler.sendEmptyMessage(-9);
                                    if (i <= 0) {
                                        break;
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                handler.sendEmptyMessage(-8);
                            }
                        }).start();


                    }
                    break;
                case R.id.p5_confrim:
                    if(istouch) {
                        if (isnull()) {
                            type = false;
                            SMSSDK.submitVerificationCode("86", phoneNumber, yanzheng
                                    .getText().toString());
                        }
                    }else {
                        Toast.makeText(person5.this, "请先点击获取验证码！",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;

            }
        }
    };


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.e("result", "看看走不走1");
            if (msg.what == -9) {
                send.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                send.setText("获取验证码");
                send.setClickable(true);
                i = 60;
            } else {
                Log.e("result", "看看走不走2");
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                Log.e("result", "result=" + result);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        /*Toast.makeText(zhuce.this, "提交验证码成功",
                                Toast.LENGTH_SHORT).show();*/
                        //执行修改密码
                        savedata();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(person5.this, "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }else{
                    if(type){
                        Toast.makeText(person5.this, "该手机号发送数量超限",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(person5.this, "验证码不正确",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };


    public void savedata(){
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                 //       String phone = phoneNumber;
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("memberId", memberId);
                        params.put("phone",phoneNumber);
                        Log.i("", "====memberId-->>" + memberId);
                        httpPost = HttpUtil.http(MyConfig.httpurl
                                + "/member/update.action", params);
                    } catch (Exception e) {
                        Toast.makeText(person5.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    handler1.sendEmptyMessage(0x10);
                }
            }).start();
        }catch (Exception e){
            Toast.makeText(person5.this, "连接服务器失败,请检查您的网络并重试",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x10:
                    try {
                        JSONObject jsonObject = new JSONObject(httpPost);
                        if ("0".equals(jsonObject.getString("errcode"))) {
                            Toast.makeText(person5.this,
                                    "修改成功", Toast.LENGTH_SHORT)
                                    .show();
                            Intent intent = new Intent(person5.this,
                                    personalActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("", "====失败原因-->>" + jsonObject.getString("errmesg"));
                            // 登录失败
                            Toast.makeText(person5.this,
                                    "修改失败，原因"+jsonObject.getString("errmesg"), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(person5.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        };
    };



    //判断验证码
    public boolean isnull(){
        if(yanzheng.getText().toString().trim().equals("")){
            Toast.makeText(this, "验证码不能为空！",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    //判断手机号是否正确

    private boolean isvalidate() {
        // TODO Auto-generated method stub
        // 获取控件输入的值
        phoneNumber = phoneNum.getText().toString().trim();
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
