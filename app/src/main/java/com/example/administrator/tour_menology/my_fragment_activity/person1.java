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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class person1 extends AppCompatActivity{

    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private Button save;
    private EditText nicname;
    private String memberId;
    private String httpPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person1);
        init();
    }

    public void init() {

        nicname = (EditText) findViewById(R.id.p1_name);
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        save = (Button) findViewById(R.id.p1_save);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("修改昵称");
        toolbar_back.setOnClickListener(mListener);
        save.setOnClickListener(mListener);

        //获取上个页面的传值
        Bundle extras = getIntent().getExtras();
        memberId = extras.getString("memberId");

    }

    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.toolbar_back:
                    Intent intent2 = new Intent(person1.this,
                            personalActivity.class);
                    startActivity(intent2);
                    finish();
                    break;

                case R.id.p1_save:

                    if(isnull()){
                        savedata();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void savedata(){
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        String nickName = nicname.getText().toString().trim();

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("memberId", memberId);
                        params.put("nickName",nickName);
                        Log.i("", "====memberId-->>" + memberId);
                        httpPost = HttpUtil.http(MyConfig.httpurl
                                + "/member/update.action", params);
                    } catch (Exception e) {
                        Toast.makeText(person1.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    handler.sendEmptyMessage(0x10);
                }
            }).start();
        }catch (Exception e){
            Toast.makeText(person1.this, "连接服务器失败,请检查您的网络并重试",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x10:
                    try {
                        JSONObject jsonObject = new JSONObject(httpPost);
                        if ("0".equals(jsonObject.getString("errcode"))) {
                            Toast.makeText(person1.this,
                                    "修改成功", Toast.LENGTH_SHORT)
                                    .show();
                            Intent intent = new Intent(person1.this,
                                    personalActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("", "====失败原因-->>" + jsonObject.getString("errmesg"));
                            // 登录失败
                            Toast.makeText(person1.this,
                                    "修改失败，原因"+jsonObject.getString("errmesg"), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(person1.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        };
    };



    //判断是否为空
    public boolean isnull(){
        if(nicname.getText().toString().trim().equals("")){
            Toast.makeText(this, "昵称不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]" ,
                Pattern . UNICODE_CASE | Pattern . CASE_INSENSITIVE );
        Matcher emojiMatcher = emoji.matcher(nicname.getText().toString().trim());
        if (emojiMatcher.find()) {
            Toast.makeText(this, "昵称不能有特殊符号",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }



}
