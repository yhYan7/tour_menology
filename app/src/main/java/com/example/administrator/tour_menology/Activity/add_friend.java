package com.example.administrator.tour_menology.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.Adapter.add_friend_adapter;
import com.example.administrator.tour_menology.Bean.add_friend_list;
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

public class add_friend extends AppCompatActivity {

    private ImageButton mImageButton;
    private TextView mTextView;
    private EditText mEditText;
    private ImageButton search;
    private ListView mListView;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private List<add_friend_list> mlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        mImageButton = (ImageButton) findViewById(R.id.toolbar_back);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTextView = (TextView) findViewById(R.id.toolbar_title);
        mTextView.setText("查找联系人");

        //获取登录用户信息
        utils = new PreferenceUtils(add_friend.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();

        mEditText = (EditText) findViewById(R.id.addfriend_edt);
        search = (ImageButton) findViewById(R.id.addfriend_seatch);
        mListView = (ListView) findViewById(R.id.addfriend_listview);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlist.clear();
                initdata();
            }
        });


    }

    private void initdata() {

        String url = "http://bobtrip.com/tripcal/api/member/search.action?Phone=" + mEditText.getText().toString();

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject js = jsonObject.getJSONObject("detail");
                    String img = js.getString("headIcon");
                    String sex = js.getString("sex");
                    String name = js.getString("nickName");
                    String memberId = js.getString("memberId");
                    mlist.add(new add_friend_list(img, sex, name, memberId));

                    if (TextUtils.isEmpty(img) && TextUtils.isEmpty(name)) {
//为空
                        Toast.makeText(add_friend.this, "您要查找的人不存在", Toast.LENGTH_SHORT).show();
                    } else {

                        add_friend_adapter madapter = new add_friend_adapter(mlist, add_friend.this);
                        mListView.setAdapter(madapter);
                    }
                } catch (JSONException e) {

                    Toast.makeText(add_friend.this, "您要查找的人不存在", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }
        });


    }
}
