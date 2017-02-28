package com.example.administrator.tour_menology.Main_Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.Activity.jingcaiyouxi;
import com.example.administrator.tour_menology.Activity.main_fabufang;
import com.example.administrator.tour_menology.Activity.main_zhaohuodong_Activity;
import com.example.administrator.tour_menology.Activity.map;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.utils.PreferenceUtils;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class find_Fragment extends Fragment implements View.OnClickListener {
    private TextView toolbartext;
    private RelativeLayout r1, r2, r3, r4, r5;


    public find_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_find_, container, false);

        toolbartext = (TextView) view.findViewById(R.id.toolbar_title);
        toolbartext.setText("发现");
        r2 = (RelativeLayout) view.findViewById(R.id.faxian_btn2);
        r1 = (RelativeLayout) view.findViewById(R.id.faxian_btn1);
        r3 = (RelativeLayout) view.findViewById(R.id.faxian_btn3);
        r4 = (RelativeLayout) view.findViewById(R.id.faxian_btn4);
//        r5 = (RelativeLayout) view.findViewById(R.id.faxian_btn5);
//        r5.setOnClickListener(this);
        r4.setOnClickListener(this);
        r3.setOnClickListener(this);
        r2.setOnClickListener(this);
        r1.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.faxian_btn1:
                Intent intent1 = new Intent(getContext(), main_zhaohuodong_Activity.class);
                startActivity(intent1);

                break;
            case R.id.faxian_btn2:

                Intent intent = new Intent(getContext(), main_fabufang.class);
                startActivity(intent);

                break;
            case R.id.faxian_btn3:

                Intent intent2 = new Intent(getContext(), jingcaiyouxi.class);
                startActivity(intent2);
                break;

            case R.id.faxian_btn4:
//                Intent intent3 = new Intent(getContext(), map.class);
//                startActivity(intent3);
////                Intent intent3= new Intent();
////                intent3.setAction("android.intent.action.VIEW");
////                Uri content_url = Uri.parse("");
////                intent3.setData(content_url);
////                intent3.setClassName("com.android.browser","com.android.browser.BrowserActivity");
////                startActivity(intent3);

                Intent intent3= new Intent();
                intent3.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://bobtrip.com/tripcal/webapp/common/nearby.jsp");
                intent3.setData(content_url);
                startActivity(intent3);


                break;
//            case R.id.faxian_btn5:
//                break;
        }
    }
}
