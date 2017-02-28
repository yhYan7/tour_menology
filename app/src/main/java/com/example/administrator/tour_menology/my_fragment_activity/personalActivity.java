package com.example.administrator.tour_menology.my_fragment_activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.HttpUtil;
import com.example.administrator.tour_menology.utils.MyConfig;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.example.administrator.tour_menology.utils.Util;
import com.example.administrator.tour_menology.widget.CircularImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class personalActivity extends AppCompatActivity {

    private RelativeLayout relayout1;
    private RelativeLayout relayout2;
    private RelativeLayout relayout3;
    private RelativeLayout relayout4;
    private RelativeLayout relayout5;
    private RelativeLayout relayout6;
    private RelativeLayout relayout7;
    private RelativeLayout relayout8;

    private TextView toolbar_title;
    private ImageButton toolbar_back;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private String httpPost;
    private TextView pc_t2,pc_t3,pc_t4,pc_t5,pc_t6,pc_t7;
    private CircularImage pc_t1;
    private JSONObject  jsonObject1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        init();
    }

    public void init(){

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("个人资料");
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        relayout1 = (RelativeLayout) findViewById(R.id.pc_rlayout1);
        relayout2 = (RelativeLayout) findViewById(R.id.pc_rlayout2);
        relayout3 = (RelativeLayout) findViewById(R.id.pc_rlayout3);
        relayout4 = (RelativeLayout) findViewById(R.id.pc_rlayout4);
        relayout5 = (RelativeLayout) findViewById(R.id.pc_rlayout5);
        relayout6 = (RelativeLayout) findViewById(R.id.pc_rlayout6);
        relayout7 = (RelativeLayout) findViewById(R.id.pc_rlayout7);
        relayout8 = (RelativeLayout) findViewById(R.id.pc_rlayout8);

        pc_t2 = (TextView) findViewById(R.id.pc_t2);//昵称
        pc_t3 = (TextView) findViewById(R.id.pc_t3);//信条
        pc_t4 = (TextView) findViewById(R.id.pc_t4);//真实姓名
        pc_t5 = (TextView) findViewById(R.id.pc_t5);//性别
        pc_t6 = (TextView) findViewById(R.id.pc_t6);//所在地区
        pc_t7 = (TextView) findViewById(R.id.pc_t7);//手机号
        pc_t1 = (CircularImage) findViewById(R.id.pc_t1);//头像


        toolbar_back.setOnClickListener(mListener);
        relayout1.setOnClickListener(mListener);
        relayout2.setOnClickListener(mListener);
        relayout3.setOnClickListener(mListener);
        relayout4.setOnClickListener(mListener);
        relayout5.setOnClickListener(mListener);
        relayout6.setOnClickListener(mListener);
        relayout7.setOnClickListener(mListener);
        relayout8.setOnClickListener(mListener);

        //数据加载
        data();

    }

    public void data(){

        //获取登录用户信息
        utils = new PreferenceUtils(personalActivity.this);
        Map<String, Object> map = (Map<String, Object>) utils
                .getPreference("login");
        if(map==null || map.size()<1) {
            Toast.makeText(personalActivity.this, "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(personalActivity.this,
                    denglu.class);
            startActivity(intent);
        } else {
            memberId = map.get("memberId").toString();
            getData();
        }

    }

    public void getData(){
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("memberId", memberId);
                        Log.i("", "====memberId-->>" + memberId);
                        httpPost = HttpUtil.http(MyConfig.httpurl
                                + "/member/detail.action", params);
                    } catch (Exception e) {
                        Toast.makeText(personalActivity.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    handler.sendEmptyMessage(0x10);
                }
            }).start();
        }catch (Exception e){
            Toast.makeText(personalActivity.this, "连接服务器失败,请检查您的网络并重试",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x10:
                    try {
                        JSONObject jsonObject = new JSONObject(httpPost);
                        if ("0".equals(jsonObject.getString("errcode"))) {
                            jsonObject1 =  (JSONObject) jsonObject.get("detail");
                            String nickName = jsonObject1.getString("nickName");
                            String sex = jsonObject1.getString("sex");
                            String realName = jsonObject1.getString("realName");
                            String areaname = jsonObject1.getString("areaname");
                            String creed = jsonObject1.getString("creed");
                            String phone = jsonObject1.getString("phone");

                            new Thread() {
                                @Override
                                public void run() {
                                    if (jsonObject1.has("headIcon")) {
                                        Bitmap bitmap = null;
                                        Log.i("", "====走没走看看3-->>");
                                        try {
                                            Log.i("", "====解析的图片-->>" + jsonObject1.getString("headIcon"));
                                            String url = jsonObject1.getString("headIcon");
                                            bitmap = Util.getbitmap(url);
                                        } catch (JSONException e) {
                                            Log.i("", "====图片解析错误-->>" + e);
                                        }
                                        Message msg = new Message();
                                        msg.obj = bitmap;
                                        msg.what = 1;
                                        mHandler.sendMessage(msg);
                                    }

                                }
                            }.start();

                            pc_t2.setText(nickName);
                            pc_t3.setText(creed);
                            pc_t4.setText(realName);
                            pc_t5.setText(sex);
                            pc_t6.setText(areaname);
                            pc_t7.setText(phone);

                        } else {
                            Log.i("", "====失败原因-->>" + jsonObject.getString("errmesg"));
                            // 登录失败
                            Toast.makeText(personalActivity.this,
                                    "获取信息失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(personalActivity.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        };
    };


    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Bitmap bitmap = (Bitmap) msg.obj;
                pc_t1.setImageBitmap(bitmap);
                pc_t1.setVisibility(View.VISIBLE);
            }
        }

    };



    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.pc_rlayout1:
                    //跳转修改头像页面

                    break;
                case R.id.pc_rlayout2:
                    //跳转修改昵称页面
                    Intent intent = new Intent(personalActivity.this,
                            person1.class);
                    intent.putExtra("memberId", memberId);
                    //intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.pc_rlayout3:
                    //信条
                    Intent intent2 = new Intent(personalActivity.this,
                            person2.class);
                    intent2.putExtra("memberId", memberId);
                    //intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.pc_rlayout4:
                    //姓名
                    Intent intent3 = new Intent(personalActivity.this,
                            person3.class);
                    intent3.putExtra("memberId", memberId);
                    //intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.pc_rlayout5:
                    //弹出选择性别对话框
                    showSexChooseDialog();
                    break;
                case R.id.pc_rlayout6:
                    //地区
                    Intent intent4 = new Intent(personalActivity.this,
                            person4.class);
                     intent4.putExtra("memberId", memberId);
                    //intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent4);
                    finish();
                    break;
                case R.id.pc_rlayout7:
                    //手机号
                    Intent intent5 = new Intent(personalActivity.this,
                            person5.class);
                     intent5.putExtra("memberId", memberId);
                    //intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent5);
                    finish();
                    break;
                case R.id.pc_rlayout8:
                    //修改密码
                    Intent intent6 = new Intent(personalActivity.this,
                            person6.class);
                     intent6.putExtra("memberId", memberId);
                    //intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent6);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

/********************************************性别选择框***********************************************************/
    private String[] sexArry = new String[] { "女", "男" };// 性别选择
    private String sex;

    /* 性别选择框 */
    private void showSexChooseDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
        builder.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                pc_t5.setText(sexArry[which]);
                if("女".equals(sexArry[which])){
                    sex = "1";
                }else {
                    sex = "0";
                }
                //将修改信息发送至后台
                sendSex();
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder.show();// 让弹出框显示
    }

    private String httpPostsex;
    public void sendSex(){
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("memberId", memberId);
                        params.put("sex", sex);
                        Log.i("", "====memberId-->>" + memberId);
                        Log.i("", "====sex-->>" + sex);
                        httpPostsex = HttpUtil.http(MyConfig.httpurl
                                + "/member/update.action", params);
                    } catch (Exception e) {
                        Toast.makeText(personalActivity.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    handlersex.sendEmptyMessage(0x10);
                }
            }).start();
        }catch (Exception e){
            Toast.makeText(personalActivity.this, "连接服务器失败,请检查您的网络并重试",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private Handler handlersex = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x10:
                    try {
                        JSONObject jsonObject = new JSONObject(httpPostsex);
                        if ("0".equals(jsonObject.getString("errcode"))) {
                            Log.i("", "====性别修改成功-->>" + jsonObject.getString("errmesg"));
                        } else {
                            Log.i("", "====失败原因-->>" + jsonObject.getString("errmesg"));

                        }
                    } catch (Exception e) {
                        Toast.makeText(personalActivity.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        };
    };

/***********************************************性别选择结束***************************************************/

}
