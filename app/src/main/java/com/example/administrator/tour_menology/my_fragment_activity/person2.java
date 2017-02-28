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
public class person2 extends AppCompatActivity {

    private ImageButton toolbar_back;
    private Button save;
    private EditText xintiao;
    private String memberId,creed;
    private String httpPost;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person2);
        init();
    }

    public void init() {

        xintiao = (EditText) findViewById(R.id.p2_name);
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        save = (Button) findViewById(R.id.p2_save);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("旅行信条");

        //获取上个页面的传值
        Bundle extras = getIntent().getExtras();
        memberId = extras.getString("memberId");

        toolbar_back.setOnClickListener(mListener);
        save.setOnClickListener(mListener);

    }

    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.toolbar_back:
                    Intent intent2 = new Intent(person2.this,
                            personalActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.p2_save:
                    if(isRight()) {
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

                     //   String creed = xintiao.getText().toString().trim();

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("memberId", memberId);
                        params.put("creed",creed);
                        Log.i("", "====memberId-->>" + memberId);
                        httpPost = HttpUtil.http(MyConfig.httpurl
                                + "/member/update.action", params);
                    } catch (Exception e) {
                        Toast.makeText(person2.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    handler.sendEmptyMessage(0x10);
                }
            }).start();
        }catch (Exception e){
            Toast.makeText(person2.this, "连接服务器失败,请检查您的网络并重试",
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
                            Toast.makeText(person2.this,
                                    "修改成功", Toast.LENGTH_SHORT)
                                    .show();
                            Intent intent = new Intent(person2.this,
                                    personalActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("", "====失败原因-->>" + jsonObject.getString("errmesg"));
                            // 登录失败
                            Toast.makeText(person2.this,
                                    "修改失败，原因"+jsonObject.getString("errmesg"), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(person2.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        };
    };


    public boolean isRight(){
        creed = xintiao.getText().toString().trim();
        Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]" ,
                Pattern . UNICODE_CASE | Pattern . CASE_INSENSITIVE );
        Matcher emojiMatcher = emoji.matcher(creed);
        if (emojiMatcher.find()) {
            Toast.makeText(this, "不能有特殊符号",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
