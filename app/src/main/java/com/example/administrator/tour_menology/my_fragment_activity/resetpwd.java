package com.example.administrator.tour_menology.my_fragment_activity;

import android.app.ProgressDialog;
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

import com.example.administrator.tour_menology.MainActivity;
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
 * Created by Administrator on 2016/8/5 0005.
 */
public class resetpwd extends AppCompatActivity {

    private EditText yanzheng,password,againpwd;
    private Button send,confrim;
    private ImageButton toolbar_back;

    private String re_phone,re_yanzheng,re_password,re_againpwd,phoneNumber,memberId;

    private ProgressDialog progressDialog;
    private String httpPost;
    private boolean tag = true;
    private int i = 60;
    Thread thread = null;
    public boolean isChange = false;
    private boolean type = false;
    private boolean istouch = false;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpwd);

        //获取上个页面的传值
        Bundle extras = getIntent().getExtras();
        phoneNumber = extras.getString("phoneNumber");
        memberId = extras.getString("memberId");

        init();
    }

    public void init(){

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("重置密码");
        yanzheng = (EditText) findViewById(R.id.re_yanzheng);//验证码
        password = (EditText) findViewById(R.id.re_password);
        againpwd = (EditText) findViewById(R.id.re_pwdagain);
        send = (Button) findViewById(R.id.re_send);//发送验证码按钮
        confrim = (Button) findViewById(R.id.re_confrim);//确定
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);

        send.setOnClickListener(mListener);
        confrim.setOnClickListener(mListener);
        toolbar_back.setOnClickListener(mListener);

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

                //发送验证码
                case R.id.re_send:
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
                    break;

                //确定修改
                case R.id.re_confrim:
                    //判断是否为空
                    if(istouch) {
                        if (isnull()) {
                            type = false;
                            SMSSDK.submitVerificationCode("86", phoneNumber, yanzheng
                                    .getText().toString());
                        }
                    }else {
                        Toast.makeText(resetpwd.this, "请先点击获取验证码！",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;

                //返回
                case R.id.toolbar_back:
                    finish();
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
                        reset();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(resetpwd.this, "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }else{
                    if(type){
                        Toast.makeText(resetpwd.this, "该手机号发送数量超限",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(resetpwd.this, "验证码不正确",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    //修改密码方法
    public void reset(){
        try{
            progressDialog = new ProgressDialog(resetpwd.this);
            progressDialog.setMessage("正在重置密码...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            re_password = password.getText().toString().trim();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("memberId", memberId);
                        params.put("paswd", re_password);
                        Log.i("", "====memberId-->>" + memberId);
                        Log.i("", "====userpass--->>" + re_password);
                        httpPost = HttpUtil.http(MyConfig.httpurl
                                + "/member/update.action", params);
                    } catch (Exception e) {
                        Toast.makeText(resetpwd.this, "网络出问题了哦,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    handler1.sendEmptyMessage(0x10);
                }
            }).start();
        }catch (Exception e){
            Toast.makeText(resetpwd.this, "网络出问题了哦,请检查您的网络并重试",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x10:
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(httpPost);
                        if ("0".equals(jsonObject.getString("errcode"))) {
                            // 重置密码成功
                            Toast.makeText(resetpwd.this, "恭喜，重置密码成功！",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(resetpwd.this,
                                    denglu.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("", "====失败原因-->>" + jsonObject.getString("errmesg"));
                            // 登录失败
                            Toast.makeText(resetpwd.this,
                                    "重置密码失败，"+jsonObject.getString("errmesg"), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(resetpwd.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        };
    };


    //判断是否为空
    public boolean isnull(){

        if(yanzheng.getText().toString().trim().equals("")){
            Toast.makeText(this, "验证码不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.getText().toString().trim().equals("")){
            Toast.makeText(this, "密码不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(againpwd.getText().toString().trim().equals("")){
            Toast.makeText(this, "确认密码不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.getText().toString().trim().equals(againpwd.getText().toString().trim())){
            Toast.makeText(this, "确认密码与密码不一致",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }


    //发送验证码的提示秒数
    private void changeBtnGetCode() {
        thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        if (resetpwd.this == null) {
                            break;
                        }
                        resetpwd.this
                                .runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        send.setText("获取验证码("
                                                + i + ")");
                                        send.setClickable(false);
                                    }
                                });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;
                if (resetpwd.this != null) {
                    resetpwd.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            send.setText("获取验证码");
                            send.setClickable(true);
                        }
                    });
                }
            };
        };
        thread.start();
    }


}
