package com.example.administrator.tour_menology.my_fragment_activity;


import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.MainActivity;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.AllApk;
import com.example.administrator.tour_menology.utils.HttpUtil;
import com.example.administrator.tour_menology.utils.MyConfig;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.example.administrator.tour_menology.utils.Util;
import com.mob.tools.utils.UIHandler;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;


public class denglu extends AppCompatActivity implements PlatformActionListener,Handler.Callback{

    private EditText lg_phone;
    private EditText lg_pwd;
    private Button denglu_button;
    private Button repassword;
    private Button lg_zhuce;
    private Button lg_qq;
    private Button lg_wechat;
    private Button lg_weibo;
    private ImageButton toolbar_back;
    private TextView toolbar_title;

    private ProgressDialog progressDialog;
    private String httpPost;
    private String httpPostqq;

    private String zhanghao,mima,memberId;

    private String touxiang,nickname,openid;

    //微博登录上传数据
    private String wbtouxiang,wbnickname,wbopenid;


    private PreferenceUtils utils;

    //---------------------------微信第三方相关
    public static IWXAPI api;


    //qq登录
    private static final String TAG = TestQQ.class.getName();
    public static String mAppid;
    public static QQAuth mQQAuth;
    private UserInfo mInfo;
    private Tencent mTencent;
    private final String APP_ID = "1105591451";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        ShareSDK.initSDK(this);
        regToWx();
        init();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "-->onStart");
        final Context context = denglu.this;
        final Context ctxContext = context.getApplicationContext();
        mAppid = APP_ID;
        mQQAuth = QQAuth.createInstance(mAppid, ctxContext);
        mTencent = Tencent.createInstance(mAppid, denglu.this);
        super.onStart();
    }


    private void init(){

       //SMSSDK.initSDK(this, "16d18b7327892", "4d61059b3307adc86b86aeed0d246e17");

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("登录");
        utils = new PreferenceUtils(denglu.this);
        lg_phone = (EditText) findViewById(R.id.lg_phone);
        lg_pwd = (EditText) findViewById(R.id.lg_pwd);
        denglu_button = (Button) findViewById(R.id.denglu_button);
        repassword = (Button) findViewById(R.id.repassword);
        lg_zhuce = (Button) findViewById(R.id.lg_zhuce);
        lg_qq = (Button) findViewById(R.id.lg_qq);
        lg_wechat = (Button) findViewById(R.id.lg_wechat);
        lg_weibo = (Button) findViewById(R.id.lg_weibo);
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);

        toolbar_back.setOnClickListener(mListener);
        denglu_button.setOnClickListener(mListener);
        repassword.setOnClickListener(mListener);
        lg_zhuce.setOnClickListener(mListener);
        lg_qq.setOnClickListener(mListener);
        lg_wechat.setOnClickListener(mListener);
        lg_weibo.setOnClickListener(mListener);

    }

    View.OnClickListener mListener = new View.OnClickListener() {

        public void onClick(View v) {

            switch (v.getId()) {
                //登录
                case R.id.denglu_button:
                    //登录
                    if(isUserNameAndPwdValid()){
                        login();
                    }
                    break;
                //忘记密码
                case R.id.repassword:
                    //跳转重置密码
                    Intent intent = new Intent(denglu.this,
                            resetphone.class);
                    startActivity(intent);
                    break;
                //注册按钮
                case R.id.lg_zhuce:
                    Intent intent1 = new Intent(denglu.this,
                            zhuce.class);
                    startActivity(intent1);
                    break;
                case R.id.toolbar_back:
                    finish();
                    break;
                //qq登录
                case R.id.lg_qq:
                    onClickLogin();
                    break;
                //微信登录
                case R.id.lg_wechat:

                    if(!api.isWXAppInstalled()){
                        Toast.makeText(denglu.this, "请先安装微信",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    getCode();


                    break;
                //微博登录
                case R.id.lg_weibo:
                    thirdSinaLogin();
                    break;
                default:
                    break;
            }

        }
    };



    //----------------------新浪微博授权获取用户信息相关------------------------
    private static final int MSG_TOAST = 1;
    private static final int MSG_ACTION_CCALLBACK = 2;
    private static final int MSG_CANCEL_NOTIFY = 3;
    private Platform mPf;
    //-----------------------------------------------------新浪微博授权登录   开始-----------------------
    /** 新浪微博授权、获取用户信息页面 */
    private void thirdSinaLogin() {
        //初始化新浪平台
        Platform pf = ShareSDK.getPlatform(denglu.this, SinaWeibo.NAME);
    //    Platform pf = ShareSDK.getPlatform(SinaWeibo.NAME);
        pf.SSOSetting(true);
        //设置监听
        pf.setPlatformActionListener(denglu.this);
        //获取登陆用户的信息，如果没有授权，会先授权，然后获取用户信息
        pf.authorize();
    }
    /** 新浪微博授权成功回调页面 */
    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        /** res是返回的数据，例如showUser(null),返回用户信息，对其解析就行
         *   http://sharesdk.cn/androidDoc/cn/sharesdk/framework/PlatformActionListener.html
         *   1、不懂如何解析hashMap的，可以上网搜索一下
         *   2、可以参考官网例子中的GetInforPage这个类解析用户信息
         *   3、相关的key-value,可以看看对应的开放平台的api
         *     如新浪的：http://open.weibo.com/wiki/2/users/show
         *     腾讯微博：http://wiki.open.t.qq.com/index.php/API%E6%96%87%E6%A1%A3/%E5%B8%90%E6%88%B7%E6%8E%A5%E5%8F%A3/%E8%8E%B7%E5%8F%96%E5%BD%93%E5%89%8D%E7%99%BB%E5%BD%95%E7%94%A8%E6%88%B7%E7%9A%84%E4%B8%AA%E4%BA%BA%E8%B5%84%E6%96%99
         *
         */
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }
    /** 取消授权 */
    @Override
    public void onCancel(Platform platform, int action) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }
    /** 授权失败 */
    @Override
    public void onError(Platform platform, int action, Throwable t) {
        t.printStackTrace();
        t.getMessage();
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch(msg.what) {
            case MSG_TOAST: {
                String text = String.valueOf(msg.obj);
                Toast.makeText(denglu.this, text, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_ACTION_CCALLBACK: {
                switch (msg.arg1) {
                    case 1: {
                        // 成功, successful notification
                        //授权成功后,获取用户信息，要自己解析，看看oncomplete里面的注释
                        //ShareSDK只保存以下这几个通用值
                        Platform pf = ShareSDK.getPlatform(denglu.this, SinaWeibo.NAME);
                        Log.e("sharesdk use_id", pf.getDb().getUserId()); //获取用户id
                        Log.e("sharesdk use_name", pf.getDb().getUserName());//获取用户名称
                        Log.e("sharesdk use_icon", pf.getDb().getUserIcon());//获取用户头像

                        Log.i("", "====用户id--->>" + pf.getDb().getUserId());
                        Log.i("", "====用户名称--->>" + pf.getDb().getUserName());
                        wbnickname = pf.getDb().getUserName();
                        wbtouxiang = pf.getDb().getUserIcon();
                        wbopenid = pf.getDb().getUserId();
                        Toast.makeText(denglu.this, "授权成功，正在登录！",
                                Toast.LENGTH_SHORT).show();
                        //启动微博登录
                        wbdenglu();

                   //     mThirdLoginResult.setText("授权成功"+"\n"+"用户id:" + pf.getDb().getUserId() + "\n" + "获取用户名称" + pf.getDb().getUserName() + "\n" + "获取用户头像" + pf.getDb().getUserIcon());
                        //mPf.author()这个方法每一次都会调用授权，出现授权界面
                        //如果要删除授权信息，重新授权
                        //mPf.getDb().removeAccount();
                        //调用后，用户就得重新授权，否则下一次就不用授权
                    }
                    break;
                    case 2: {
                    //    mThirdLoginResult.setText("登录失败");
                        Toast.makeText(denglu.this, "登录失败",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case 3: {
                        // 取消, cancel notification
                       // mThirdLoginResult.setText("取消授权");
                        Toast.makeText(denglu.this, "取消授权",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
            break;
            case MSG_CANCEL_NOTIFY: {
                NotificationManager nm = (NotificationManager) msg.obj;
                if (nm != null) {
                    nm.cancel(msg.arg1);
                }
            }
            break;
        }
        return false;
    }

    //微博登录方法

    private String httpPostwb;

    public void wbdenglu(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("type", "3");
                    params.put("openId", wbopenid);
                    params.put("nickName", wbnickname);
                    params.put("headIcon", wbtouxiang);
                    Log.i("", "====openId-->>" + wbopenid);
                    Log.i("", "====nickName--->>" + wbnickname);
                    httpPostwb = HttpUtil.http(MyConfig.httpurl
                            + "/member/thirdlogin.action", params);
                } catch (Exception e) {
                    Toast.makeText(denglu.this, "连接服务器失败,请检查您的网络并重试",
                            Toast.LENGTH_SHORT).show();
                }
                handlerwb.sendEmptyMessage(0x10);
            }
        }).start();
    }

    private Handler handlerwb = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x10:
                    System.out.println("######测试weibo登录专用#######");
                    try {
                        JSONObject jsonObject = new JSONObject(httpPostwb);
                        if ("0".equals(jsonObject.getString("errcode"))) {
                            // 注册成功
                            Log.i("", "====返回字段用户id-->>" + jsonObject.getString("memberId"));
                            Toast.makeText(denglu.this, "恭喜，登录成功！",
                                    Toast.LENGTH_SHORT).show();
                            memberId = jsonObject.getString("memberId");

                            //将登陆数据保存起来 以作后用
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("memberId", memberId);
                            utils.savePreference("login", map);

                            Intent intent = new Intent(denglu.this,
                                    MainActivity.class);
                            intent.putExtra("memberId",memberId);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("", "====失败原因-->>" + jsonObject.getString("errmesg"));
                            // 登录失败
                            Toast.makeText(denglu.this,
                                    "登录失败，"+jsonObject.getString("errmesg"), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(denglu.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        };
    };


  /*  // 在状态栏提示分享操作,the notification on the status bar
    private void showNotification(long cancelTime, String text) {
        try {
            Context app = getApplicationContext();
            NotificationManager nm = (NotificationManager) app.getSystemService(Context.NOTIFICATION_SERVICE);
            final int id = Integer.MAX_VALUE / 13 + 1;
            nm.cancel(id);
            long when = System.currentTimeMillis();
            Notification notification = new Notification(R.mipmap.ic_launcher, text, when);
            PendingIntent pi = PendingIntent.getActivity(app, 0, new Intent(), 0);
            notification.setLatestEventInfo(app, "sharesdk test", text, pi);
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            nm.notify(id, notification);
            if (cancelTime > 0) {
                Message msg = new Message();
                msg.what = MSG_CANCEL_NOTIFY;
                msg.obj = nm;
                msg.arg1 = id;
                UIHandler.sendMessageDelayed(msg, cancelTime, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

*/

/**************************************微博第三方登录  结束*************************************************/





    /** -------------------------微信第三方登录---------------------- */
    /**
     *
     * 微信平台应用授权登录接入代码示例
     *
     * */
    private void regToWx(){
        // 通过WXAPIFactory工厂,获得IWXAPI的实例
        api = WXAPIFactory.createWXAPI(denglu.this, AllApk.WEIXIN_APP_ID, true);
        // 将应用的appid注册到微信
        api.registerApp(AllApk.WEIXIN_APP_ID);
    }
    //获取微信访问getCode
    private void getCode(){

        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "carjob_wx_login";
        api.sendReq(req);
        System.out.print("开始走不走");
    }
    /** -------------------------微信第三方登录结束-------------------- */





//   =========================     QQ登录的全部方法  ================================
    //QQ第三方登录
    private void onClickLogin() {
        if (!mQQAuth.isSessionValid()) {
            IUiListener listener = new BaseUiListener() {
                @Override
                protected void doComplete(JSONObject values) {
                    updateUserInfo();
               //     updateLoginButton();
                }
            };
            mQQAuth.login(this, "all", listener);
            // mTencent.loginWithOEM(this, "all",
            // listener,"10000144","10000144","xxxx");
            mTencent.login(this, "all", listener);
        } else {
            mQQAuth.logout(this);
            updateUserInfo();
        //    updateLoginButton();
        }
    }


    private void updateUserInfo() {
        if (mQQAuth != null && mQQAuth.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onComplete(final Object response) {


                    JSONObject json = (JSONObject) response;
                    if (json.has("figureurl")) {
                        try {
                            nickname = json.getString("nickname");
                            touxiang = json.getString("figureurl_qq_2");
                            Log.e("昵称", nickname);
                            Log.e("头像", json.getString("figureurl_qq_2"));
                        } catch (JSONException e) {

                        }
                    }
                  /*  Toast.makeText(denglu.this, "授权成功，正在登录！",
                            Toast.LENGTH_SHORT).show();*/
                    qqdenglu();
                }

                @Override
                public void onCancel() {
                }
            };
            mInfo = new UserInfo(this, mQQAuth.getQQToken());
            mInfo.getUserInfo(listener);

        } else {
            /*mUserInfo.setText("");
            mUserInfo.setVisibility(View.GONE);
            mUserLogo.setVisibility(View.GONE);*/
        }
    }

    public void qqdenglu(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("type", "1");
                    params.put("openId", openid);
                    params.put("nickName", nickname);
                    params.put("headIcon", touxiang);
                    Log.i("", "====openId-->>" + openid);
                    Log.i("", "====nickName--->>" + nickname);
                    httpPostqq = HttpUtil.http(MyConfig.httpurl
                            + "/member/thirdlogin.action", params);
                } catch (Exception e) {
                    Toast.makeText(denglu.this, "连接服务器失败,请检查您的网络并重试",
                            Toast.LENGTH_SHORT).show();
                }
                handlerqq.sendEmptyMessage(0x10);
            }
        }).start();
    }

    private Handler handlerqq = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x10:
                    System.out.println("######测试qq登录专用#######");
                    try {
                        JSONObject jsonObject = new JSONObject(httpPostqq);
                        if ("0".equals(jsonObject.getString("errcode"))) {
                            // 注册成功
                            Log.i("", "====返回字段用户id-->>" + jsonObject.getString("memberId"));
                            Toast.makeText(denglu.this, "恭喜，登录成功！",
                                    Toast.LENGTH_SHORT).show();
                            memberId = jsonObject.getString("memberId");

                            //将登陆数据保存起来 以作后用
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("memberId", memberId);
                            utils.savePreference("login", map);

                            Intent intent = new Intent(denglu.this,
                                    MainActivity.class);
                            intent.putExtra("memberId",memberId);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("", "====失败原因-->>" + jsonObject.getString("errmesg"));
                            // 登录失败
                            Toast.makeText(denglu.this,
                                    "登录失败，"+jsonObject.getString("errmesg"), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(denglu.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        };
    };

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
           /* Util.showResultDialog(denglu.this, response.toString(),
                    "登录成功");*/
            JSONObject json1 = (JSONObject) response;

            try {
                openid = json1.getString("openid");
                Log.i("", "====openid-->>" + json1.getString("openid"));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Toast.makeText(denglu.this,"获取授权成功，正在登录",
                    Toast.LENGTH_SHORT).show();
            Log.i("", "====xinxi-->>" + response.toString());


            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            Util.toastMessage(denglu.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(denglu.this, "onCancel: ");
            Util.dismissDialog();
        }
    }

//==============================================QQ登录方法结束=========================================================



/*******************************************正常登录方法***************************************/

    //登录方法
    public void login(){
        try{
            progressDialog = new ProgressDialog(denglu.this);
            progressDialog.setMessage("正在登录...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            zhanghao = lg_phone.getText().toString().trim();
            mima = lg_pwd.getText().toString().trim();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("phone", zhanghao);
                        params.put("paswd", mima);
                        Log.i("", "====phone-->>" + zhanghao);
                        Log.i("", "====paswd--->>" + mima);
                        httpPost = HttpUtil.http(MyConfig.httpurl
                                + "/member/login.action", params);
                    } catch (Exception e) {
                        Toast.makeText(denglu.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    handler.sendEmptyMessage(0x10);
                }
            }).start();
        }catch (Exception e){
            Toast.makeText(denglu.this, "连接服务器失败,请检查您的网络并重试",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Handler handler = new Handler() {
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
                            Toast.makeText(denglu.this, "恭喜，登录成功！",
                                    Toast.LENGTH_SHORT).show();
                            memberId = jsonObject.getString("memberId");

                            //将登陆数据保存起来 以作后用
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("memberId", memberId);
                            utils.savePreference("login", map);

                            Intent intent = new Intent(denglu.this,
                                    MainActivity.class);
                            intent.putExtra("memberId",memberId);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("", "====失败原因-->>" + jsonObject.getString("errmesg"));
                            // 登录失败
                            Toast.makeText(denglu.this,
                                    "登录失败，"+jsonObject.getString("errmesg"), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(denglu.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        };
    };

    //判断账号或密码是否为空
    public boolean isUserNameAndPwdValid() {
        if(lg_phone.getText().toString().trim().equals("")){
            Toast.makeText(this, "手机号不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(lg_pwd.getText().toString().trim().equals("")){
            Toast.makeText(this, "密码不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }


}
