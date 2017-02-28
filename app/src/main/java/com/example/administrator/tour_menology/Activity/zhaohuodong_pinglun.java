package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.tour_menology.Adapter.pinglunAdapter;
import com.example.administrator.tour_menology.Bean.pinglun_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.PositiveDialong;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class zhaohuodong_pinglun extends AppCompatActivity {

    private EditText mText;
    private Button fabiao;
    private ListView mListView;
    private List<pinglun_list> mList;
    private RequestQueue mRequestQueue;
    private TextView mTextView;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private String id ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhaohuodong_pinglun);
        initView();
    }

    private void initView() {
        mText = (EditText) findViewById(R.id.pinglun_zhaohuodong_edit);
        fabiao = (Button) findViewById(R.id.zhaohuodong_pinglun_fabu);

        utils = new PreferenceUtils(zhaohuodong_pinglun.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        if (map == null || map.size() < 1) {
            Toast.makeText(zhaohuodong_pinglun.this, "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(zhaohuodong_pinglun.this,
                    denglu.class);
            startActivity(intent);

        }else

            memberId = map.get("memberId").toString();


        mListView = (ListView) findViewById(R.id.zhaohuodong_pinglun_list);
        mTextView = (TextView) findViewById(R.id.zhaohuodong_pinglun_text);




        fabiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mText.getText().toString()==null){
                    Toast.makeText(zhaohuodong_pinglun.this,"请输入评论内容",Toast.LENGTH_SHORT).show();
                }


                String url ="http://bobtrip.com/tripcal/api/comment/create.action";

                Map<String, String> params = new HashMap<>();
                /**
                 activityId	是	活动id
                 currentId	是	评论人id
                 content	是	评论内容
                 */
                id =getIntent().getExtras().getString("id");
                params.put("activityId",id);
                params.put("currentId",memberId);
                params.put("content",mText.getText().toString());

                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(zhaohuodong_pinglun.this,"评论失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(zhaohuodong_pinglun.this,"评论成功",Toast.LENGTH_SHORT).show();
                        mText.getText().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("commentId")) {
                                final String recordId = jsonObject.getString("commentId");


                                PositiveDialong dialog = new PositiveDialong(zhaohuodong_pinglun.this, "",
                                        new PositiveDialong.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                switch (v.getId()) {
                                                    case R.id.dialog_btn_lq:
                                                        String url = "http://bobtrip.com/tripcal/api/getRewords.action?recordId=" + recordId;
                                                        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                                                                                                       @Override
                                                                                                       public void onError(Call call, Exception e) {

                                                                                                       }

                                                                                                       @Override
                                                                                                       public void onResponse(String response) {
                                                                                                           try {
                                                                                                               JSONObject jsonObject1 = new JSONObject(response);
                                                                                                               String fen = jsonObject1.getString("point");
                                                                                                               Toast.makeText(zhaohuodong_pinglun.this, "成功领取" + fen + "分",
                                                                                                                       Toast.LENGTH_SHORT).show();
                                                                                                           } catch (JSONException e) {
                                                                                                               e.printStackTrace();
                                                                                                           }

                                                                                                       }
                                                                                                   }
                                                        );


                                                        break;
                                                    case R.id.dialog_btn:
                                                        break;
                                                }

                                            }
                                        });
                                dialog.show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        initVolley();

                    }
                });

            }
        });

        initVolley();

    }

    private void initVolley() {
        mRequestQueue = Volley.newRequestQueue(zhaohuodong_pinglun.this);

        String url2 =getIntent().getExtras().getString("id");
        String Url = "http://bobtrip.com/tripcal/api/comment/list.action?activityId="+url2+"&currentPage=1";

        JsonObjectRequest jsonObject = new JsonObjectRequest(Url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mList = new ArrayList<>();
                try {
                    JSONArray data = jsonObject.getJSONArray("list");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        String content = jsonObject1.getString("content");
                        String img = jsonObject1.getString("headIcon");
                        String title = jsonObject1.getString("nickName");
                        String date = jsonObject1.getString("time");
                        mList.add(new pinglun_list(content, date, img, title));
                    }

                    pinglunAdapter adapter = new pinglunAdapter(zhaohuodong_pinglun.this, mList);
                    mListView.setAdapter(adapter);

                    mListView.setEmptyView(mTextView);
                    //     setListViewHeightBasedOnChildren(mListView);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mRequestQueue.add(jsonObject);
    }
}
