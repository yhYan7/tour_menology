package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.tour_menology.Adapter.friend_adapter;
import com.example.administrator.tour_menology.Bean.friend_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class myfriend extends AppCompatActivity {
    private ImageButton mImageButton,add_friend;
    private TextView mTextView;
    private EditText mEditText;
    private ImageButton search;
    private ListView mListView;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private List<friend_list>mList=new ArrayList<>();
    public friend_adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfriend);

        initview();

        initdata("");


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(myfriend.this, zhuye.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getInviterId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });




    }

    private void initdata(String key) {
        String url ="http://bobtrip.com/tripcal/api/addressbook/list.action?memberId="+memberId+"&keyword="+key;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 =jsonArray.getJSONObject(i);
                        String addStatus =jsonObject1.getString("addStatus");
                        String headIcon =jsonObject1.getString("headIcon");
                        String nickName =jsonObject1.getString("nickName");
                        String inviterId =jsonObject1.getString("inviterId");
                        String addressBookId =jsonObject1.getString("addressBookId");

                        mList.add(new friend_list(addStatus,headIcon,nickName,inviterId,addressBookId));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter = new friend_adapter(mList,myfriend.this);
                mListView.setAdapter(mAdapter);



            }
        });

    }

    private void initview() {
        mImageButton = (ImageButton) findViewById(R.id.toolbar_back);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTextView = (TextView) findViewById(R.id.toolbar_title);
        mTextView.setText("通讯录");

        //获取登录用户信息
        utils = new PreferenceUtils(myfriend.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();

        mListView = (ListView) findViewById(R.id.haoyou_list);
        mEditText = (EditText) findViewById(R.id.friend_edt);
        add_friend = (ImageButton) findViewById(R.id.friend_add);

        search = (ImageButton) findViewById(R.id.friend_seatch);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.clear();
                initdata(mEditText.getText().toString());
            }
        });

        add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(myfriend.this,add_friend.class);
                startActivity(intent);
            }
        });


    }
}
