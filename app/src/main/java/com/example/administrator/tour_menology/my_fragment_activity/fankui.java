package com.example.administrator.tour_menology.my_fragment_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.R;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class fankui extends AppCompatActivity {
    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private EditText t1,t2;
    private Button confrim;
    private String yijian,lianxi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fankui);
        init();
    }


    public void init() {

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("疑问与反馈");
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        t1 = (EditText) findViewById(R.id.fankui_t1);
        t2 = (EditText) findViewById(R.id.fankui_t2);
        confrim = (Button) findViewById(R.id.fankui_confrim);


        confrim.setOnClickListener(mListener);
        toolbar_back.setOnClickListener(mListener);

    }

    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.fankui_confrim:
                    if(isnull()){
                        Toast.makeText(fankui.this, "感谢您的反馈！",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;

                default:
                    break;

            }
        }
    };

    public boolean isnull(){
        yijian = t1.getText().toString().trim();
        lianxi = t2.getText().toString().trim();
        if("".equals(yijian)){
            Toast.makeText(this, "请填写反馈信息",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if("".equals(lianxi)){
            Toast.makeText(this, "请填写邮箱或手机号",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



}
