package com.example.administrator.tour_menology.my_fragment_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.tour_menology.R;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class myfenxiang extends AppCompatActivity{

    private ImageButton toolbar_back;
    private TextView toolbar_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfx);
        init();
    }


    public void init() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("我的分享");
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_back.setOnClickListener(mListener);

    }

    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.toolbar_back:
                    finish();
                    break;

                default:
                    break;

            }
        }
    };


}
