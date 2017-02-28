package com.example.administrator.tour_menology.my_fragment_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.tour_menology.R;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class myhuodong extends AppCompatActivity {

    private ImageButton toolbar_back;
    private Button bianji;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhuodong);
        init();
    }

    public void init(){
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("导入活动");
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        bianji = (Button) findViewById(R.id.hd_bianji);

        toolbar_back.setOnClickListener(mListener);
        bianji.setOnClickListener(mListener);

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
