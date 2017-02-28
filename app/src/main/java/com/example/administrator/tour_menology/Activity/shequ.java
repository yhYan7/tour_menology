package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.Adapter.main_fabufang_adapter;
import com.example.administrator.tour_menology.Adapter.shequ_adapter;
import com.example.administrator.tour_menology.Bean.main_fabu_list;
import com.example.administrator.tour_menology.Bean.shequ_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.xiaosu.pulllayout.PullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class shequ extends AppCompatActivity {

    private List<main_fabu_list.LIstdate> mlistdateTemp = new ArrayList<>();
    private List<main_fabu_list.LIstdate> mlistdate = new ArrayList<>();
    private ImageButton toolbar;
    private TextView toolbartext;
    private PullLayout mPullLayout;
    private ListView mListView3;
    private main_fabu_list mbean;
    private int pa = 1;
    private shequ_adapter mAdapter;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private List<shequ_list>mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shequ);

        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbartext = (TextView) findViewById(R.id.toolbar_title);
        toolbartext.setText("TA订阅的社区");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mListView3 = (ListView) findViewById(R.id.main_fabu_listview1);

        initDate();


        mListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(shequ.this, main_fabu_xiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getUnitId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });



    }

        private void initDate() {

            String id =getIntent().getExtras().getString("id");

            String URL = "http://bobtrip.com/tripcal/api/member/listTaFocus.action?memberId="+id ;

            OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    mPullLayout.finishPull();
                    Toast.makeText(shequ.this, "暂无数据", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject =new JSONObject(response);
                        JSONArray jsonArray =jsonObject.getJSONArray("list");
                        mList=new ArrayList<shequ_list>();
                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 =jsonArray.getJSONObject(i);
                            String countPublish =jsonObject1.getString("countPublish");
                            String logo =jsonObject1.getString("logo");
                            String countFocus =jsonObject1.getString("countFocus");
                            String unitName =jsonObject1.getString("unitName");
                            String unitId =jsonObject1.getString("unitId");

                            mList.add(new shequ_list(countPublish,"http://bobtrip.com/tripcal"+logo,countFocus,unitName,unitId));

                        }

                        shequ_adapter m =new shequ_adapter(shequ.this,mList);
                        mListView3.setAdapter(m);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
        }



}
