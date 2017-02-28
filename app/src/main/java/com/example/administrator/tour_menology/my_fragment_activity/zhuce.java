package com.example.administrator.tour_menology.my_fragment_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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


public class zhuce extends AppCompatActivity {

    private EditText phone;
    private Button send;
    private EditText yanzheng;
    private EditText password;
    private EditText pwdagain;
    private Button zhuce;
    private Button qqlogin;
    private Button wechatlogin;
    private Button weibologin;
    private Button alreadyAcc;
    private ImageButton toolbar_back;
    private TextView toolbar_title;

    private String phoneNo,phoneNumber;
    private String mima;
    private String yanzhengNo;
    private String memberId;

    private ProgressDialog progressDialog;
    private String httpPost;
    private boolean tag = true;
    private boolean type = false;
    private int i = 60;
    Thread thread = null;
    public boolean isChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        init();
    }

    private void init(){
        phone = (EditText) findViewById(R.id.phoneNo);
        send = (Button) findViewById(R.id.send);
        yanzheng = (EditText) findViewById(R.id.yanzheng);
        password = (EditText) findViewById(R.id.password);
        pwdagain = (EditText) findViewById(R.id.pwdagain);
        zhuce = (Button) findViewById(R.id.zhuce);
        qqlogin = (Button) findViewById(R.id.qqlogin);
        wechatlogin = (Button) findViewById(R.id.wechatlogin);
        weibologin = (Button)findViewById(R.id.wechatlogin);
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        alreadyAcc = (Button) findViewById(R.id.alreadyAcc);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("注册");


        alreadyAcc.setOnClickListener(mListener);
        toolbar_back.setOnClickListener(mListener);
        send.setOnClickListener(mListener);
        zhuce.setOnClickListener(mListener);
        qqlogin.setOnClickListener(mListener);
        wechatlogin.setOnClickListener(mListener);
        weibologin.setOnClickListener(mListener);


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
                case R.id.send:
                    //如果手机号输入正确
                    if(isvalidate()){
                        //发送验证码
                      /*  send.setClickable(true);
                        isChange = true;
                        changeBtnGetCode();*/

                        type = true;

                        // 通过sdk发送短信验证
                  //      phoneNumber = phone.getText().toString().trim();
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
                //注册按钮
                case R.id.zhuce:
                    //如果各种不为空
                    type = false;
                    if(isnull()){
                        SMSSDK.submitVerificationCode("86", phoneNumber, yanzheng
                                .getText().toString());
                    //    createProgressBar();
                    //   login();
                    }
                    break;
                //已有账号
                case R.id.alreadyAcc:
                    /*Intent intent = new Intent(zhuce.this,
                            denglu.class);
                    startActivity(intent);*/
                    finish();
                    break;
                //qq登录
                case R.id.qqlogin:

                    break;
                //微信登录
                case R.id.wechatlogin:

                    break;
                //微博登录
                case R.id.weibologin:

                    break;
                case R.id.toolbar_back:
                    finish();
                    break;
                default:
                    break;
            }

        }
    };


    /**
     *
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                send.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                send.setText("获取验证码");
                send.setClickable(true);
                i = 60;
            } else {
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
                        //执行登录
                        login();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(zhuce.this, "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }else{
                    if(type){
                        Toast.makeText(zhuce.this, "该手机号发送数量超限",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(zhuce.this, "验证码不正确",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };



    //登录方法
    public void login(){
        try{
            progressDialog = new ProgressDialog(zhuce.this);
            progressDialog.setMessage("正在注册...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            mima = password.getText().toString().trim();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("phone", phoneNumber);
                        params.put("paswd", mima);
                        Log.i("", "====username-->>" + phoneNumber);
                        Log.i("", "====userpass--->>" + mima);
                        httpPost = HttpUtil.http(MyConfig.httpurl
                                + "/member/register.action", params);
                    } catch (Exception e) {
                        Toast.makeText(zhuce.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    handler1.sendEmptyMessage(0x10);
                }
            }).start();
        }catch (Exception e){
            Toast.makeText(zhuce.this, "连接服务器失败,请检查您的网络并重试",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x10:

                    progressDialog.dismiss();

                    System.out.println("######测试专用#######");
                    try {
                        JSONObject jsonObject = new JSONObject(httpPost);
                        if ("0".equals(jsonObject.getString("errcode"))) {
                            // 注册成功
                            Log.i("", "====返回字段用户id-->>" + jsonObject.getString("memberId"));

                            Toast.makeText(zhuce.this, "恭喜，注册成功！请登录",
                                    Toast.LENGTH_SHORT).show();
                            memberId = jsonObject.getString("memberId");
                            Intent intent = new Intent(zhuce.this,
                                    denglu.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //   System.out.println("####"+jsonObject.getString("errorMsg"));
                                Log.i("", "====失败原因-->>" + jsonObject.getString("errmesg"));
                            // 登录失败
                            Toast.makeText(zhuce.this,
                                    "注册失败，"+jsonObject.getString("errmesg"), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(zhuce.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();

                    }
                    break;
            }
        };
    };



    /**
     * progressbar
     */
    private void createProgressBar() {
        FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ProgressBar mProBar = new ProgressBar(this);
        mProBar.setLayoutParams(layoutParams);
        mProBar.setVisibility(View.VISIBLE);
        layout.addView(mProBar);
    }



    private boolean isvalidate() {
        // TODO Auto-generated method stub
        // 获取控件输入的值
        phoneNumber = phone.getText().toString().trim();
        Log.i("", "====phoneNumber-->>手机号为：" + phoneNumber);
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



    //判断是否为空
    public boolean isnull(){

        if(phone.getText().toString().trim().equals("")){
            Toast.makeText(this, "手机号不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
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
        if(pwdagain.getText().toString().trim().equals("")){
            Toast.makeText(this, "确认密码不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.getText().toString().trim().equals(pwdagain.getText().toString().trim())){
            Toast.makeText(this, "确认密码与密码不一致",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
     /*   if(!phoneNumber.equals(phone.getText().toString().trim())){
            Toast.makeText(this, "该手机号与发送验证码手机号不一致",
                    Toast.LENGTH_SHORT).show();
            return false;
        }*/

        return true;
    }

    /**
     * 判断一个字符串的位数
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }



}
